/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.geometry;

/**
 *
 * @author aloisius
 */
public class Circle {

    private Vector mCenter;
    private double mRadius;

    public Circle() {
        this.mCenter = new Vector();
        this.mRadius = 0.0;
    }

    public Circle(Vector center, double radius) {
        this.mCenter = center;
        this.mRadius = radius;
    }

    public Circle(double center_x, double center_y, double radius) {
        this.mCenter = new Vector(center_x, center_y);
        this.mRadius = radius;
    }

    public Circle(Vector point1, Vector point2, Vector point3) {
        Line l1 = Line.getCentralPerpendicularLine(point1, point2);
        Line l2 = Line.getCentralPerpendicularLine(point2, point3);

        if (l1.intersection(l2, mCenter) == false) {
            mCenter = new Vector();
        }
        mRadius = mCenter.dist(point1);
    }

    public Vector center() {
        return mCenter;
    }

    public double radius() {
        return mRadius;
    }

    public void setCenter(Vector center) {
        mCenter = center;
    }

    public void setRadius(double radius) {
        mRadius = radius;
    }

    public boolean isWithin(Vector p, double buffer) {
        return mCenter.dist(p) <= mRadius + buffer;
    }

    public boolean isWithin(Vector p) {
        return isWithin(p, GU.FLOAT_EPS);
    }

    /*
     * Get intersection points between the circle and the ray. t1 is nearer to
     * the origin than t2. \param r the ray. \param t1 will be set to the
     * distance from origin of the ray to intersection point 1. \param t2 will
     * be set to the distance from origin of the ray to intersection point 2.
     * \param buffer controls precision. \return number of intersection points.
     */
    public int intersection(Ray r, double t1, double t2, double buffer) {
        Vector rel_center = (mCenter.operatorMinus(r.origin())).rotate(-r.dir());

        if ((mRadius + buffer) <= Math.abs(rel_center.Y())) {
            return 0;
        } else if (mRadius <= Math.abs(rel_center.Y())) {
            t1 = rel_center.X() - 0.13;
            t2 = rel_center.X() + 0.13;
            return 2;
        } else {
            double dis = Math.sqrt(mRadius * mRadius - rel_center.Y() * rel_center.Y());
            t1 = rel_center.X() - dis;
            t2 = rel_center.X() + dis;
            return 2;
        }
    }

    public int intersection(Ray r, double t1, double t2) {
        return intersection(r, t1, t2, GU.FLOAT_EPS);
    }

    public int intersection(Circle circle, Vector v1, Vector v2, double buffer) {
        double d, a, b, c, p, q, r;
        Double cos_value[] = new Double[2];
        Double sin_value[] = new Double[2];
        if (mCenter.dist(circle.center()) <= buffer
                && Math.abs(mRadius - circle.radius()) <= buffer) {
            return -1;
        }

        d = mCenter.dist(circle.center());
        if (d > mRadius + circle.radius()
                || d < Math.abs(mRadius - circle.radius())) {
            return 0;
        }

        a = 2.0 * mRadius * (mCenter.X() - circle.center().X());
        b = 2.0 * mRadius * (mCenter.Y() - circle.center().Y());
        c = circle.radius() * circle.radius() - mRadius * mRadius
                - mCenter.dist2(circle.center());
        p = a * a + b * b;
        q = -2.0 * a * c;
        if (Math.abs(d - mRadius - circle.radius()) <= buffer
                || (Math.abs(d - Math.abs(mRadius - circle.radius())) <= buffer)) {
            cos_value[0] = -q / p / 2.0;
            sin_value[0] = Math.sqrt(1 - cos_value[0] * cos_value[0]);

            v1.setX(mRadius * cos_value[0] + mCenter.X());
            v1.setY(mRadius * sin_value[0] + mCenter.Y());

            if (Math.abs(v1.dist2(circle.center())
                    - circle.radius() * circle.radius()) >= buffer) {
                v1.setY(mCenter.Y() - mRadius * sin_value[0]);
            }
            return 1;
        }

        r = c * c - b * b;
        cos_value[0] = (Math.sqrt(q * q - 4.0 * p * r) - q) / p / 2.0;
        cos_value[1] = (-Math.sqrt(q * q - 4.0 * p * r) - q) / p / 2.0;
        sin_value[0] = Math.sqrt(1 - cos_value[0] * cos_value[0]);
        sin_value[1] = Math.sqrt(1 - cos_value[1] * cos_value[1]);

        v1.setX(mRadius * cos_value[0] + mCenter.X());
        v2.setX(mRadius * cos_value[1] + mCenter.X());
        v1.setY(mRadius * sin_value[0] + mCenter.Y());
        v2.setY(mRadius * sin_value[1] + mCenter.Y());

        if (Math.abs(v1.dist2(circle.center())
                - circle.radius() * circle.radius()) >= buffer) {
            v1.setY(mCenter.Y() - mRadius * sin_value[0]);
        }

        if (Math.abs(v2.dist2(circle.center())
                - circle.radius() * circle.radius()) >= buffer) {
            v2.setY(mCenter.Y() - mRadius * sin_value[0]);
        }

        if (v1.dist(v2) <= buffer) {
            if (v1.Y() > 0) {
                v2.setY(-v2.Y());
            } else {
                v1.setY(-v1.Y());
            }
        }
        return 2;
    }

};
