/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointTreeSet;

    public PointSET() {
        pointTreeSet = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return pointTreeSet.isEmpty();
    }

    public int size() {
        return pointTreeSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("paramater cannot be of type 'null'.");
        }
        pointTreeSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("paramater cannot be of type 'null'.");
        }
        return pointTreeSet.contains(p);
    }

    public void draw() {
        for (Point2D p : pointTreeSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("paramater cannot be of type 'null'.");
        }
        Stack<Point2D> rectPoints = new Stack<Point2D>();
        for (Point2D p : pointTreeSet) {
            if (rect.contains(p)) rectPoints.push(p);
        }
        return rectPoints;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("paramater cannot be of type 'null'.");
        }
        Point2D nearestPoint = null;
        for (Point2D q : pointTreeSet) {
            if (nearestPoint == null || p.distanceTo(q) < p.distanceTo(nearestPoint)) {
                nearestPoint = q;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        // KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            // kdtree.insert(p);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();
        System.out.println("Size: " + brute.size());
    }
}
