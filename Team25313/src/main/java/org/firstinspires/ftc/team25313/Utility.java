package org.firstinspires.ftc.team25313;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public final class Utility {

    private static final ElapsedTime runtime = new ElapsedTime();

    private Utility() { }

    // Math Utilities
    // Limit in [min, max]
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    // inch to tick encoder
    public static double inchesToTicks(double inches) {
        return (inches / (2 * Math.PI * Constants.wheelRads)) *
                Constants.ticksPerRev * Constants.gearRatio;
    }
    // tick encoder to inch
    public static double ticksToInches(double ticks) {
        return (ticks / (Constants.ticksPerRev * Constants.gearRatio)) *
                (2 * Math.PI * Constants.wheelRads);
    }

    // deg to rad
    public static double degToRad(double degrees) {
        return Math.toRadians(degrees);
    }

    // rad to deg
    public static double radToDeg(double radians) {
        return Math.toDegrees(radians);
    }

    // calibrate angle to [-π, π]
    public static double normalizeAngle(double angle) {
        while (angle > Math.PI)  angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    // Telemetry & Logging

    // Show log in telemetry
    public static void teleLog(Telemetry telemetry, String caption, Object value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }

    // print to LogCat
    public static void logCat(String tag, String message) {
        if (Constants.debugMode)
            RobotLog.dd(tag, message);
    }

    // Timing utilities

    // Reset time
    public static void resetRuntime() {
        runtime.reset();
    }

    // return in seconds
    public static double getRuntime() {
        return runtime.seconds();
    }

    // sleep
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Joystick helper

    // deadzone
    public static double applyDeadzone(double value, double threshold) {
        return (Math.abs(value) < threshold) ? 0.0 : value;
    }

    // Reverse Y
    public static double reverseY(double value) {
        return -value;
    }

    // Combine motors values
    public static String formatDrivePower(double fl, double fr, double bl, double br) {
        return String.format("FL: %.2f | FR: %.2f | BL: %.2f | BR: %.2f", fl, fr, bl, br);
    }

    // Miscellaneous

    // boolean to string
    public static String boolToStr(boolean val) {
        return val ? "ON" : "OFF";
    }

    // avarage state for double
    public static double average(double... values) {
        double sum = 0;
        for (double v : values) sum += v;
        return values.length > 0 ? sum / values.length : 0;
    }

    public static double applyDeadzone(double value) {
        return Math.abs(value) < Constants.deadzone ? 0 : value;
    }
}
