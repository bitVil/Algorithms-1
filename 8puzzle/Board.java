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
            }
}
