/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.geometry;

/**
 *
 * @author aloisius
 */
public class Rectangular {

    private double mLeft;
    private double mRight;
    private double mTop;
    private double mBottom;

    public Rectangular() {
        this.mLeft = 0.0;
        this.mRight = 0.0;
        this.mTop = 0.0;
        this.mBottom = 0.0;
    }

    public Rectangular(double left, double right, double top, double bottom) {
        this.mLeft = left;
        this.mRight = right;
        this.mTop = top;
        this.mBottom = bottom;
    }

    public Rectangular(Vector center, Vector size) {
        mLeft = center.X() - size.X() / 2.0;
        mRight = center.X() + size.X() / 2.0;
        mTop = center.Y() - size.Y() / 2.0;
        mBottom = center.Y() + size.Y() / 2.0;
    }

    public double left() {
        return mLeft;
    }

    public double right() {
        return mRight;
    }

    public double top() {
        return mTop;
    }

    public double bottom() {
        return mBottom;
    }

    public void setLeft(double left) {
        mLeft = left;
    }

    public void setRight(double right) {
        mRight = right;
    }

    public void setTop(double top) {
        mTop = top;
    }

    public void setBottom(double bottom) {
        mBottom = bottom;
    }

    public Vector topLeftCorner() {
        return new Vector(mLeft, mTop);
    }

    public Vector topRightCorner() {
        return new Vector(mRight, mTop);
    }

    public Vector bottomLeftCorner() {
        return new Vector(mLeft, mBottom);
    }

    public Vector bottomRightCorner() {
        return new Vector(mRight, mBottom);
    }

    public Line topEdge() {
        return new Line(topLeftCorner(), topRightCorner());
    }

    public Line bottomEdge() {
        return new Line(bottomLeftCorner(), bottomRightCorner());
    }

    public Line leftEdge() {
        return new Line(topLeftCorner(), bottomLeftCorner());
    }

    public Line rightEdge() {
        return new Line(topRightCorner(), bottomRightCorner());
    }

    public boolean isWithin(Vector v, double buffer) {
        return (v.X() >= mLeft - buffer) && (v.X() <= mRight + buffer) && (v.Y() >= mTop - buffer) && (v.Y() <= mBottom + buffer);
    }

    public boolean isWithin(Vector v) {
        return isWithin(v, GU.FLOAT_EPS);
    }

    public Vector adjustToWithin(Vector v) {
        Vector r = v;

        if (r.X() < mLeft) {
            r.setX(mLeft);
        } else if (r.X() > mRight) {
            r.setX(mRight);
        }

        if (r.Y() < mTop) {
            r.setY(mTop);
        } else if (r.Y() > mBottom) {
            r.setY(mBottom);
        }

        return r;
    }

    public boolean intersection(Ray r, Vector point) {
        if (!isWithin(r.origin())) {
            return false;
        }

        int n = 0;
        Vector[] points = new Vector [4];
        for (int i = 0; i < 4; i++) {
            points[i] = new Vector();
        }
        
        if (topEdge().intersection(r, points[n])) {
            if (isWithin(points[n])) {
                n++;
            }
        }

        if (bottomEdge().intersection(r, points[n])) {
            if (isWithin(points[n])) {
                n++;
            }
        }

        if (leftEdge().intersection(r, points[n])) {
            if (isWithin(points[n])) {
                n++;
            }
        }

        if (rightEdge().intersection(r, points[n])) {
            if (isWithin(points[n])) {
                n++;
            }
        }

        if (n == 0) {
            return false;
        } else if (n == 1) {
            point = points[0];
            return true;
        } else if (n >= 2) {
            int max_idx = 0;
            double max_dist2 = points[0].dist2(r.origin());

            for (int j = 1; j < n; ++j) {
                double dist2 = points[j].dist2(r.origin());
                if (dist2 > max_dist2) {
                    max_idx = j;
                    max_dist2 = dist2;
                }
            }

            point = points[max_idx];
            return true;
        }

        return false;
    }

    public Vector intersection(Ray r) {
        Vector point = new Vector();
        boolean ret = intersection(r, point);
        if (!ret) {
            return r.origin();
        }
        return point;
    }

};
