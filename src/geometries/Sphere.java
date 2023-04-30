package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere extends RadialGeometry {
    private Point center;

    public Sphere(double radius,Point center) {
        super(radius);
        this.center = center;
    }

    public Vector getNormal(Point point) {
        return center.subtract(point).normalize();
    }


    public List<Point> findIntersections(Ray ray) {

        Point p0 = ray.getP0(); // ray's starting point
        Point O = center; //the sphere's center point
        Vector V = ray.getDir(); // "the v vector" from the presentation

        // if p0 on center, calculate with line parametric representation
        // the direction vector normalized.
        if (O.equals(p0)) {
            Point newPoint = p0.add(ray.getDir().scale(radius));
            return List.of(newPoint);
        }

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= radius) {
            return null;
        }

        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(p1, p2);
        }

        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(p1);
        }

        if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(p2);
        }
        return null;
    }
}