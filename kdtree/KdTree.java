/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int N = 0;

    private class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return N;
    }

    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        root = put(root, point, true, 0, 0, 1, 1);
    }

    private Node put(Node node, Point2D point, boolean byXVal, double xmin, double ymin,
                     double xmax, double ymax) {
        if (node == null) {
            N++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (node.p.equals(point)) return node;
        int cmp;
        if (byXVal) {
            cmp = Double.compare(point.x(), node.p.x());
            if (cmp < 0) {
                node.lb = put(node.lb, point, !byXVal, xmin, ymin, node.p.x(), ymax);
            }
            else {
                node.rt = put(node.rt, point, !byXVal, node.p.x(), ymin, xmax, ymax);
            }
            return node;
        }
        else {
            cmp = Double.compare(point.y(), node.p.y());
            if (cmp < 0) {
                node.lb = put(node.lb, point, !byXVal, xmin, ymin, xmax, node.p.y());
            }
            else {
                node.rt = put(node.rt, point, !byXVal, xmin, node.p.y(), xmax, ymax);
            }
            return node;
        }
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean byXVal) {
        if (node != null) {
            if (byXVal) {
                StdDraw.setPenRadius(0.003);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenRadius(0.003);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(node.p.x(), node.p.y());
            draw(node.lb, !byXVal);
            draw(node.rt, !byXVal);
        }
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        return contains(root, point, true);
    }

    private boolean contains(Node node, Point2D point, boolean byXVal) {
        if (node == null) return false;
        if (node.p.equals(point)) return true;
        int cmp;
        if (byXVal) cmp = Double.compare(point.x(), node.p.x());
        else cmp = Double.compare(point.y(), node.p.y());
        if (cmp < 0) return contains(node.lb, point, !byXVal);
        else return contains(node.rt, point, !byXVal);

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return range(rect, root, new Stack<Point2D>());
    }

    private Stack<Point2D> range(RectHV rect, Node node, Stack<Point2D> points) {
        if (node == null) return points;
        if (rect.contains(node.p)) points.push(node.p);
        if (node.lb != null && rect.intersects(node.lb.rect)) range(rect, node.lb, points);
        if (node.rt != null && rect.intersects(node.rt.rect)) range(rect, node.rt, points);
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(p, root.p, root, true);
    }

    private Point2D nearest(Point2D p, Point2D bestPoint, Node node, boolean byXVal) {
        if (node == null) return bestPoint;
        if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(bestPoint)) bestPoint = node.p;
        int cmp;
        if (byXVal) cmp = Double.compare(p.x(), node.p.x());
        else cmp = Double.compare(p.y(), node.p.y());
        if (cmp < 0) {
            if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < p
                    .distanceSquaredTo(bestPoint)) {
                bestPoint = nearest(p, bestPoint, node.lb, !byXVal);
            }
            if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < p
                    .distanceSquaredTo(bestPoint)) {
                bestPoint = nearest(p, bestPoint, node.rt, !byXVal);
            }
        }
        else {
            if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < p
                    .distanceSquaredTo(bestPoint)) {
                bestPoint = nearest(p, bestPoint, node.rt, !byXVal);
            }
            if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < p
                    .distanceSquaredTo(bestPoint)) {
                bestPoint = nearest(p, bestPoint, node.lb, !byXVal);
            }
        }
        return bestPoint;
    }


    public static void main(String[] args) {
        /*
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        int i = 0;
        Point2D q = new Point2D(0.206107, 0.904508);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.println(kdtree.nearest(new Point2D(1.0, 0.8125)));
        */


        /*
        System.out.println();
        System.out.println("Inserting: " + q);
        kdtree.insert(q);
        System.out.println("Inserted: " + kdtree.contains(q));
        System.out.println("Reference " + 10);
        System.out.println("Student " + kdtree.size());
        */


        /*
        in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            System.out.println("contains " + p + " = " + kdtree.contains(p));
        }
        Point2D p = new Point2D(500, 500);
        System.out.println("contains " + p + " = " + kdtree.contains(p));

        RectHV rect = new RectHV(0.01, 0.2, 0.98, 0.9);
        rect.draw();
        kdtree.draw();
        Iterable<Point2D> rectIntersect = kdtree.range(rect);
        for (Point2D q : rectIntersect) {
            System.out.println(q);
        }
        */
        // System.out.println(kdtree.nearest(new Point2D(0.5, 0.49)));

    }
}
