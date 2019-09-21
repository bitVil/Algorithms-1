/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Stack<Board> pathToGoal;
    private boolean solvable;
    private int numMoves;


    public Solver(Board initial1) {
        Board initial2 = initial1.twin();
        MinPQ<Node> frontier1 = new MinPQ<Node>();
        MinPQ<Node> frontier2 = new MinPQ<Node>();
        Node previous1 = null;
        Node previous2 = null;
        frontier1.insert(new Node(null, initial1));
        frontier2.insert(new Node(null, initial2));
        Node searchNode1 = frontier1.delMin();
        Node searchNode2 = frontier2.delMin();

        while (!searchNode1.getState().isGoal() && !searchNode2.getState().isGoal()) {
            for (Board neighbour : searchNode1.getState().neighbors()) {
                if (searchNode1.getParent() == null || !neighbour
                        .equals(searchNode1.getParent().getState())) {
                    frontier1.insert(new Node(searchNode1, neighbour));
                }
            }
            searchNode1 = frontier1.delMin();
            for (Board neighbour : searchNode2.getState().neighbors()) {
                if (searchNode2.getParent() == null || !neighbour
                        .equals(searchNode2.getParent().getState())) {
                    frontier2.insert(new Node(searchNode2, neighbour));
                }
            }
            searchNode2 = frontier2.delMin();
        }
        if (searchNode1.getState().isGoal()) {
            pathToGoal = searchNode1.pathToNode();
            solvable = true;
            numMoves = searchNode1.getNumMoves();
        }
        else {
            pathToGoal = null;
            solvable = false;
            numMoves = -1;
        }

    }


    /*
    public Solver(Board initial) {
        MinPQ<Node> frontier = new MinPQ<Node>();
        Node previous = null;
        frontier.insert(new Node(null, initial));
        Node searchNode = frontier.delMin();

        while (!searchNode.getState().isGoal()) {
            /*
            System.out.println("Previous Node");
            if (previous != null) System.out.println(previous.getState());
            else System.out.println("no previous");
            System.out.println("Search Node");
            System.out.println(searchNode.getState());
            System.out.println("New nodes");

            Iterable<Board> neighbors = searchNode.getState().neighbors();
            for (Board neighbour : neighbors) {
                if (previous == null || !neighbour.equals(previous.getState())) {
                    frontier.insert(new Node(searchNode, neighbour));
                    // System.out.println(neighbour);
                }
            }
            previous = searchNode;
            searchNode = frontier.delMin();
        }

        pathToGoal = searchNode.pathToNode();
        numMoves = searchNode.getNumMoves();
        solvable = true;
        solvable = true;
    }
    */

    public int moves() {
        return numMoves;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public Iterable<Board> solution() {
        return pathToGoal;
    }

    private static class Node implements Comparable<Node> {
        private final Board state;
        private final Node parent;
        private final int numMoves;
        private final int manhattanDist;

        public Node(Node parentNode, Board boardState) {
            parent = parentNode;
            state = boardState;
            manhattanDist = state.manhattan();
            if (parentNode == null) numMoves = 0;
            else numMoves = parent.getNumMoves() + 1;
        }

        public Board getState() {
            return state;
        }

        public Node getParent() {
            return parent;
        }

        public int getNumMoves() {
            return numMoves;
        }

        public Stack<Board> pathToNode() {
            Stack<Board> path = new Stack<Board>();
            Node x = this;
            path.push(x.getState());
            while (x.getParent() != null) {
                x = x.getParent();
                path.push(x.getState());
            }
            return path;
        }

        public int compareTo(Node that) {
            if (this.manhattanDist + this.numMoves < that.manhattanDist + that.numMoves) {
                return -1;
            }
            else if (this.manhattanDist + this.numMoves > that.manhattanDist + that.numMoves) {
                return 1;
            }
            else return 0;
        }
    }


    public static void main(String[] args) {
        /*
        int[][] testMatrix1 = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        int[][] testMatrix2 = { { 8, 0, 3 }, { 4, 1, 2 }, { 7, 6, 5 } };
        int[][] testMatrix3 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        int[][] testMatrix4 = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };

        Board testBoard1 = new Board(testMatrix1);
        Board testBoard2 = new Board(testMatrix2);
        Board testBoard3 = new Board(testMatrix3);
        Board testBoard4 = new Board(testMatrix4);

        Node node1 = new Node(null, testBoard1);
        Node node2 = new Node(node1, testBoard2);
        Node node3 = new Node(node2, testBoard3);
        Node node4 = new Node(node3, testBoard4);

        System.out.println(node4.pathToNode());
        System.out.println(node1.getNumMoves());
        System.out.println(node4.getNumMoves());

        int[][] testMatrix1 = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board testBoard1 = new Board(testMatrix1);
        Solver path1 = new Solver(testBoard1, Node.hammingOrder());
        Solver path2 = new Solver(testBoard1, Node.manhattanOrder());
        System.out.println(path1.pathToGoal);
        System.out.println(path2.pathToGoal);


        int[][] testMatrix2 = {
                { 3, 2, 4, 8 }, { 1, 6, 0, 12 }, { 5, 10, 7, 11 }, { 9, 13, 14, 15 }
        };
        Board testBoard2 = new Board(testMatrix2);
        Solver path3 = new Solver(testBoard2, Node.hammingOrder());
        System.out.println(path3.isSolvable());
        */

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
