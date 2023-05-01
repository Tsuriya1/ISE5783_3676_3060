package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for Sphere Point class
 */
class SphereTest {

    // Test method for GetNormal method
    @Test
    void TestGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // Test method for GetNormal method
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(0, -2, 0);
        Sphere s = new Sphere(2, p1);
        assertEquals(new Vector(0, 1, 0), s.getNormal(p2));
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(p2),
                sphere.findIntersections(new Ray(new Point(0.8, 0.6, 0),new Vector(3,1,0))),
                "Ray crosses sphere on one point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 2, 0), new Vector(1, 1, 0))),
                "Ray's line after the sphere");

        // ============================ Boundary Values Tests ========================//

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts on sphere and goes inside (1 point)
        assertEquals(List.of(p2),
                sphere.findIntersections(new Ray(p1,new Vector(3,1,0))),
                "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p2, new Vector(3,1,0))),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0))),
                "Ray starts before the sphere and we expect be 2 intersections");

        // TC14: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new Point(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0))),
                "Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 point)
        assertEquals(List.of(new Point(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "Ray starts inside and goes outside");

        // TC16: Ray starts at the center (1 point)
        assertEquals(List.of(new Point(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))),
                "Ray starts at the center and goes outside");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, -1, -1), new Vector(0, 1, 1))),
                "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 1, 1))),
                "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 2, 2), new Vector(0, 1, 1))),
                "Ray starts after the tangent point");
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1))),
                "Ray starts after the tangent point");
    }
}