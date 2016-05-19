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
public class Ray {

    private Vector mOrigin;
    private double mDirection;

    public Ray() {

    }

    public Ray(Vector origin, double direction) {
        setValue(origin, direction);
    }

    public Vector origin() {
        return mOrigin;
    }

    public double dir() {
        return mDirection;
    }

    public void setOrigin(Vector origin) {
        mOrigin = origin;
    }

    public void setDirection(double direction) {
        mDirection = direction;
    }

    public void setValue(Vector origin, double direction) {
        mOrigin = origin;
        mDirection = direction;
    }

    public Vector getPoint(double dist) {
        return mOrigin.operatorPlus(Vector.polar2Vector(dist, mDirection));
    }

    public boolean isInRightDir(Vector point) {
        return Math.abs(GU.getNormalizeAngleDeg((point.operatorMinus(mOrigin)).dir() - mDirection)) < 10.0;
    }

    public boolean onRay(Vector point, double buffer) {
        Vector v = point.operatorMinus(mOrigin);
        return Math.abs(GU.sin(v.dir() - mDirection) * v.mod()) < buffer && isInRightDir(point);
    }

    public boolean onRay(Vector point) {
        return onRay(point, GU.FLOAT_EPS);
    }

    public double GetDistanceFromOrigin(Vector point) {
        return (point.operatorMinus(mOrigin).mod());
    }

    public boolean intersection(Line l, MuDouble dist) {
        SinCosT value = new SinCosT(mDirection);

        double tmp = l.a() * value.getCos() + l.b() * value.getSin();
        if (Math.abs(tmp) < GU.FLOAT_EPS) {
            return false;
        } else {
            dist.value = (-l.c() - l.a() * mOrigin.X() - l.b() * mOrigin.Y()) / tmp;
            return dist.value >= 0;
        }
    }

    public boolean intersection(Line l, Vector point) {
        MuDouble dist = new MuDouble(0.0);
        boolean ret = intersection(l, dist);
        if (ret) {
            point = getPoint(dist.value);
        }
        return ret;
    }

    public boolean intersection(Ray r, Vector point) {
        Line l1 = new Line(this);
        Line l2 = new Line(r);

        if (l1.isSameSlope(l2)) {
            return false;
        }

        if (!l1.intersection(l2, point)) {
            return false;
        }

        return isInRightDir(point) && r.isInRightDir(point);
    }

    public boolean intersection(Ray r, MuDouble dist) {
        Vector point = new Vector();
        boolean ret = intersection(r, point);
        dist.value = point.dist(mOrigin);
        return ret;
    }

    public double intersection(Line l) {
        MuDouble dist = new MuDouble(0.0);
        return intersection(l, dist) ? dist.value : -1000.0;
    }

    public Vector getClosestPoint(Vector point) {
        Line l = new Line(this);
        Vector closest_point = l.getProjectPoint(point);
        return Math.abs(GU.getNormalizeAngleDeg((closest_point.operatorMinus(mOrigin)).dir() - mDirection)) < 90 ? closest_point : this.origin();
    }

};
