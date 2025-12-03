package org.firstinspires.ftc.team25313.subsystems.drivetrain.util;

public class MathUtil {
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public static boolean epsilonEquals(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }
    public static double wrapAngle(double angle) {
        angle %= (2 * Math.PI);
        if (angle > Math.PI) angle -= 2 * Math.PI;
        if (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }
    public static double deadband(double value, double threshold) {
        return Math.abs(value) < threshold ? 0 : value;
    }
    public static int sign (double x) {
        return (x > 0) ? 1 : (x < 0 ? -1 : 0);
    }
    public static double signedSquare(double x) {
        return x * Math.abs(x);
    }
    public static double dist(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.hypot(dx, dy);
    }
    public static double degToRad(double deg) {
        return Math.toRadians(deg);
    }
    public static double radToDeg(double rad) {
        return Math.toDegrees(rad);
    }
    public static double smoothStep(double t) {
        return t * t * (3 - 2 * t);
    }
    public static double clampMagnitude(double value, double maxMag) {
        return clamp(value, -maxMag, maxMag);
    }
}
