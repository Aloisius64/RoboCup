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
public class Line {

    double mA;
    double mB;
    double mC;

    public Line() {
        mA = 0.0;
        mB = 0.0;
        mC = 0.0;
    }

    /*
     * a*x+b*y+c=0
     */
    public Line(double a, double b, double c) {
        this.mA = a;
        this.mB = b;
        this.mC = c;
    }

    public Line(Vector point1, Vector point2) {
        if (Math.abs(point1.X() - point2.X()) < GU.FLOAT_EPS) {
            mA = 1.0;
            mB = 0.0;
            mC = -point1.X();
        } else {
            mA = (point2.Y() - point1.Y()) / (point2.X() - point1.X());
            mB = -1.0;
            mC = point1.Y() - point1.X() * mA;
        }
    }

    public Line(Ray r) {
        Line line = new Line(r.origin(), r.origin().operatorPlus(Vector.polar2Vector(1.0, r.dir())));
        this.mA = line.mA;
        this.mB = line.mB;
        this.mC = line.mC;
    }

    public double a() {
        return mA;
    }

    public double b() {
        return mB;

    }

    public double c() {
        return mC;
    }

    public double dir() {
        return GU.aTan(-mA / mB);
    }

    public double getX(double y) {
        if (Math.abs(mA) > 0.0) {
            return ((-mC - mB * y) / mA);
        } else {
            return 0.0;
        }
    }

    public double getY(double x) {
        if (Math.abs(mB) > 0.0) {
            return ((-mC - mA * x) / mB);
        } else {
            return 0.0;
        }
    }

    public boolean isOnLine(Vector point, double buffer) {
        return Math.abs(mA * point.X() + mB * point.Y() + mC) < buffer;
    }

    public boolean isOnLine(Vector point) {
        return isOnLine(point, GU.FLOAT_EPS);
    }

    public boolean isUpLine(Vector point) {
        return !isOnLine(point) && (mA * point.X() + mB * point.Y() + mC > 0);
    }

    public boolean halfPlaneTest(Vector pt) {
        if (Math.abs(mB) > 0.0) {
            return pt.Y() > getY(pt.X());
        }
        return pt.X() < -mC / mA;
    }

    public boolean isSameSlope(Line l, double buffer) {
        return (Math.abs(mB) < buffer && Math.abs(l.mB) < buffer) || Math.abs(mA / mB - l.mA / l.mB) < buffer;
    }

    public boolean isSameSlope(Line l) {
        return isSameSlope(l, GU.FLOAT_EPS);
    }

    public double dist(Vector point) {
        return Math.abs(mA * point.X() + mB * point.Y() + mC) / Math.sqrt(mA * mA + mB * mB);
    }

    public boolean isPointInSameSide(Vector pt1, Vector pt2) {
        Line tl = new Line(pt1, pt2);
        if (isSameSlope(tl)) {
            return true;
        }

        // TODO Review this
        Vector inter_point = new Vector();
        intersection(tl, inter_point);

        return (inter_point.X() - pt1.X()) * (pt2.X() - inter_point.X()) <= 0;
    }

    public Line getPerpendicular(Vector pt) {
        return new Line(mB, -mA, mA * pt.Y() - mB * pt.X());
    }

    /*
     * Set this line be the perpendicular bisector of pos1 and pos2;
     */
    public void perpendicularBisector(Vector pos1, Vector pos2) {
        mA = 2 * (pos2.X() - pos1.X());
        mB = 2 * (pos2.Y() - pos1.Y());
        mC = pos1.X() * pos1.X() - pos2.X() * pos2.X() + pos1.Y() * pos1.Y() - pos2.Y() * pos2.Y();
    }

    public Vector getProjectPoint(Vector pt) {
        Vector joint_pt = new Vector();
        intersection(getPerpendicular(pt), joint_pt);
        return joint_pt;
    }

    public Vector mirrorPoint(Vector pt) {
        return getProjectPoint(pt).operatorMultiply(2.0).operatorMinus(pt);
    }

    public void lineFromPline(Vector pos1, Vector pos2) {
        mA = 2 * (pos2.X() - pos1.X());
        mB = 2 * (pos2.Y() - pos1.Y());
        mC = pos1.X() * pos1.X() - pos2.X() * pos2.X() + pos1.Y() * pos1.Y() - pos2.Y() * pos2.Y();
    }

    public double getA() {
        return mA;
    }

    public double getB() {
        return mB;
    }

    public double getC() {
        return mC;
    }

    public boolean isInBetween(Vector pt, Vector end1, Vector end2) {
        assert (isOnLine(end1) && isOnLine(end2));

        Vector project_pt = getProjectPoint(pt);
        double dist2 = end1.dist2(end2);

        return (project_pt.dist2(end1) < dist2 + GU.FLOAT_EPS && project_pt.dist2(end2) < dist2 + GU.FLOAT_EPS);
    }

    public boolean intersection(Line l, Vector point) {
        if (isSameSlope(l)) {
            return false;
        }

        if (Math.abs(mB) > 0.0) {
            if (Math.abs(l.b()) > 0.0) {
                point.setX((mC * l.b() - mB * l.c()) / (l.a() * mB - mA * l.b()));
                point.setY(getY(point.X()));
            } else {
                point = new Vector(-l.c() / l.a(), getY(-l.c() / l.a()));
            }
        } else {
            point = new Vector(-mC / mA, l.getY(-mC / mA));
        }

        return true;
    }

    public Vector intersection(Line l) {
        Vector point = new Vector();
        if (intersection(l, point)) {
            return point;
        }
        return new Vector();
    }

    public boolean intersection(Ray r, Vector point) {
        Line l = new Line(r);

        if (!intersection(l, point)) {
            return false;
        } else {
            return r.isInRightDir(point);
        }
    }

    public Vector getClosestPointInBetween(Vector pt, Vector end1, Vector end2) {
        assert (isOnLine(end1) && isOnLine(end2));

        if (isInBetween(pt, end1, end2)) {
            return getProjectPoint(pt);
        } else if (end1.dist2(pt) < end2.dist2(pt)) {
            return end1;
        } else {
            return end2;
        }
    }

    /*
     * Get the central perpendicular line from two points
     */
    public static Line getCentralPerpendicularLine(Vector pos1, Vector pos2) {
        double a = 2.0 * (pos2.X() - pos1.X());
        double b = 2.0 * (pos2.Y() - pos1.Y());
        double c = pos1.X() * pos1.X() - pos2.X() * pos2.X() + pos1.Y() * pos1.Y() - pos2.Y() * pos2.Y();
        return new Line(a, b, c);
    }
};
