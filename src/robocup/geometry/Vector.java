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
public class Vector {

    private double mX;
    private double mY;

    public Vector() {
        this.mX = 0.0;
        this.mY = 0.0;
    }

    public Vector(double x, double y) {
        this.mX = x;
        this.mY = y;
    }

    public double X() {
        return mX;
    }

    public double Y() {
        return mY;
    }

    public void setX(double x) {
        mX = x;
    }

    public void setY(double y) {
        mY = y;
    }

    public void setValue(double x, double y) {
        mX = x;
        mY = y;
    }

    public void setValuePolar(double r, double theta) {
        SinCosT value = new SinCosT(theta);

        mX = r * value.getCos();
        mY = r * value.getSin();
    }

    public Vector operatorMinus() {
        return new Vector(-mX, -mY);
    }

    public Vector operatorPlus(Vector a) {
        return new Vector(mX + a.mX, mY + a.mY);
    }

    public Vector operatorMinus(Vector a) {
        return new Vector(mX - a.mX, mY - a.mY);
    }

    public Vector operatorMultiply(double a) {
        return new Vector(mX * a, mY * a);
    }

    public Vector operatorDivide(double a) {
        if (a > -GU.FLOAT_EPS && a < GU.FLOAT_EPS) {
            a = Math.signum(a) * GU.FLOAT_EPS;
        }

        return new Vector(mX / a, mY / a);
    }

    public void operatorPlusEqual(Vector a) {
        mX += a.mX;
        mY += a.mY;
    }

    public void operatorPlusEqual(double a) {
        mX += a;
        mY += a;
    }

    public void operatorMinusEqual(Vector a) {
        mX -= a.mX;
        mY -= a.mY;
    }

    public void operatorMinusEqual(double a) {
        mX -= a;
        mY -= a;
    }

    public void operatorMultiplyEqual(double a) {
        mX *= a;
        mY *= a;
    }

    public void operatorDivideEqual(double a) {
        mX /= a;
        mY /= a;
    }

    public boolean operatorEqual(Vector a) {
        return mX == a.mX && mY == a.mY;
    }

    public boolean operatorNotEqual(Vector a) {
        return !operatorEqual(a);
    }

    public boolean operatorNotEqual(double a) {
        return (mX != a) || (mY != a);
    }

    public String dump() {
        return new String("(" + mX + ", " + mY + ")");
    }

    public double mod() {
        return Math.sqrt(mX * mX + mY * mY);
    }

    public double mod2() {
        return mX * mX + mY * mY;
    }

    public double dist(Vector a) {
        return this.operatorMinus(a).mod();
    }

    public double dist2(Vector a) {
        return this.operatorMinus(a).mod2();
    }

    public double dir() {
        return GU.aTan2(mY, mX);
    }

    /**
     * \return a Vector with length "length" at the same direction, or Vector
     * (0, 0) if the original Vector was (0, 0).
     */
    public Vector setLength(double length) {
        if (mod() > 0.0) {
            return this.operatorMultiply(length / mod());
        }
        return new Vector();
    }

    /**
     * \return a Vector with length 1.0 at the same direction.
     */
    public Vector normalize() {
        return setLength(1.0);
    }

    /**
     * \return a Vector rotated by angle.
     */
    public Vector rotate(double angle) {
        return rotate(new SinCosT(angle));
    }

    public Vector rotate(SinCosT value) {
        return new Vector(mX * value.getCos() - mY * value.getSin(), mY * value.getCos() + mX * value.getSin());
    }

    /**
     * check if a point is approximate equal to *this;
     *
     * return true when they are approximate equal, false else;
     */
    public boolean ApproxEqual(Vector a) {
        return Math.abs(mX - a.X()) < GU.FLOAT_EPS && Math.abs(mY - a.Y()) < GU.FLOAT_EPS;
    }

    public static Vector polar2Vector(double mod, double ang) {
        SinCosT value = new SinCosT(ang);
        return new Vector(mod * value.getCos(), mod * value.getSin());
    }

};
