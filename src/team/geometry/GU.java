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
public class GU {

    public static final double FLOAT_EPS = 0.000006;

    public static double getNormalizeAngleDeg(double ang, double min_ang) {
        if (ang < min_ang) {
            do {
                ang += 360.0;
            } while (ang < min_ang);
        } else {
            double max_ang = 360.0 + min_ang;

            while (ang >= max_ang) {
                ang -= 360.0;
            }
        }
        return ang;
    }

    public static double getNormalizeAngleDeg(double ang) {
        return getNormalizeAngleDeg(ang, -180.0);
    }

    public static double rad2Deg(double x) {
        return x * 180.0 / Math.PI;
    }

    public static double deg2Rad(double x) {
        return x * Math.PI / 180.0;
    }

    public static double sin(double ang) {
        return Math.sin(deg2Rad(ang));
    }

    public static double cos(double ang) {
        return Math.cos(deg2Rad(ang));
    }

    public static double tan(double x){
	return Math.tan(deg2Rad(x));
    }

    public static double aCos(double x){
	return ((x) >= 1.0 - 0.000006 ? 0.0 : ((x) <= -1.0 + 0.000006 ? 180.0 : (rad2Deg(Math.acos(x)))));
    }

    public static double aSin(double x){
	return ((x) >= 1.0 - 0.000006 ? 90.0 : ((x) <= -1.0 + 0.000006 ? -90.0 : (rad2Deg(Math.asin(x)))));
    }

    public static double aTan(double x){
	return (rad2Deg(Math.atan(x)));
    }

    public static double aTan2(double y, double x){
	return ((Math.abs(x) < 0.000006 && Math.abs(y) < 0.000006) ? 0 : (rad2Deg(Math.atan2(y, x))));
    }

}
