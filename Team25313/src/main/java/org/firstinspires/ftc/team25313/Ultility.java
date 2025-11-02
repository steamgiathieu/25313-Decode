package org.firstinspires.ftc.team25313;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public final class Ultility {

    private static final ElapsedTime runtime = new ElapsedTime();

    private Ultility() { } // Ngăn không cho khởi tạo

    /*** --- Math Utilities --- ***/

    /** Giới hạn giá trị trong khoảng [min, max] */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /** Chuyển inch sang tick encoder */
    public static double inchesToTicks(double inches) {
        return (inches / (2 * Math.PI * Constants.WHEEL_RADIUS)) *
                Constants.TICKS_PER_REV * Constants.GEAR_RATIO;
    }

    /** Chuyển tick encoder sang inch */
    public static double ticksToInches(double ticks) {
        return (ticks / (Constants.TICKS_PER_REV * Constants.GEAR_RATIO)) *
                (2 * Math.PI * Constants.WHEEL_RADIUS);
    }

    /** Chuyển độ sang radian */
    public static double degToRad(double degrees) {
        return Math.toRadians(degrees);
    }

    /** Chuyển radian sang độ */
    public static double radToDeg(double radians) {
        return Math.toDegrees(radians);
    }

    /** Chuẩn hóa góc về [-π, π] */
    public static double normalizeAngle(double angle) {
        while (angle > Math.PI)  angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    /*** --- Telemetry & Logging --- ***/

    /** Hiển thị log thông qua Telemetry */
    public static void log(Telemetry telemetry, String caption, Object value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }

    /** Ghi log ra Logcat (Android) */
    public static void log(String tag, String message) {
        if (Constants.DEBUG_MODE)
            RobotLog.dd(tag, message);
    }

    /*** --- Timing Utilities --- ***/

    /** Reset thời gian */
    public static void resetRuntime() {
        runtime.reset();
    }

    /** Trả về thời gian đã trôi (giây) */
    public static double getRuntime() {
        return runtime.seconds();
    }

    /** Dừng tạm thời (blocking sleep) */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /*** --- Joystick & Control Helpers --- ***/

    /** Áp dụng deadzone cho joystick */
    public static double applyDeadzone(double value, double threshold) {
        return (Math.abs(value) < threshold) ? 0.0 : value;
    }

    /** Chuyển giá trị trục joystick theo hướng ngược lại (do FTC y bị đảo) */
    public static double reverseY(double value) {
        return -value;
    }

    /** Kết hợp các giá trị motor mecanum (giúp debug nhanh) */
    public static String formatDrivePower(double fl, double fr, double bl, double br) {
        return String.format("FL: %.2f | FR: %.2f | BL: %.2f | BR: %.2f", fl, fr, bl, br);
    }

    /*** --- Miscellaneous --- ***/

    /** Chuyển boolean sang chuỗi dễ đọc */
    public static String boolToStr(boolean val) {
        return val ? "ON" : "OFF";
    }

    /** Tính trung bình của mảng double */
    public static double average(double... values) {
        double sum = 0;
        for (double v : values) sum += v;
        return values.length > 0 ? sum / values.length : 0;
    }
}
