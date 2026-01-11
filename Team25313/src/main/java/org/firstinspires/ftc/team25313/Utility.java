package org.firstinspires.ftc.team25313;

import com.bylazar.panels.Panels;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;

/**
 * Utility class
 * - Math helpers
 * - Encoder helpers
 * - Unified telemetry (Driver Hub + Panels)
 * - Panels-safe
 */
public final class Utility {

    /* =======================
     * TIME
     * ======================= */

    private static final ElapsedTime runtime = new ElapsedTime();

    private Utility() { }

    /* =======================
     * PANELS DATA (STATIC)
     * Panels đọc trực tiếp
     * ======================= */

    public static final class PanelsData {
        // Drivetrain
        public static double x;
        public static double y;
        public static double rotate;

        // Vision
        public static int tagId = -1;
        public static double tx, ty, tz;
        public static double yawDeg;
        public static String motif = "N/A";

        // Intake
        public static boolean isIntake;

        // Outtake
        public static boolean isReady;
        public static double launchPower;
    }

    /* =======================
     * TELEMETRY HELPERS
     * ======================= */

    /**
     * Add data to Driver Hub telemetry
     * Panels tự đọc PanelsData (không cần gọi)
     */
    public static void teleAdd(Telemetry telemetry, String caption, Object value) {
        telemetry.addData(caption, value);
    }

    /**
     * Update telemetry (CHỈ gọi 1 lần / loop)
     */
    public static void teleUpdate(Telemetry telemetry) {
        telemetry.update();
    }

    /* =======================
     * HIGH-LEVEL TELEMETRY
     * ======================= */

    public static void teleDrivePose(
            Telemetry telemetry,
            double x, double y, double rotate
    ) {
        // Driver Hub
        telemetry.addData("X", x);
        telemetry.addData("Y", y);
        telemetry.addData("Rotate", rotate);

        // Panels
        PanelsData.x = x;
        PanelsData.y = y;
        PanelsData.rotate = rotate;
    }

//    public static void teleIntake(Telemetry telemetry, boolean isIntake) {
//        // Driver Hub
//        telemetry.addData("Intake", isIntake);
//
//        // Panels
//        PanelsData.isIntake = isIntake;
//    }

    public static void teleVision(
            Telemetry telemetry,
            VisionSubsystem vision
    ) {
        telemetry.addData("Has Target", vision.hasTarget());

        if (vision.hasTarget()) {
            telemetry.addData(
                    "Distance (m)",
                    "%.2f",
                    vision.getDistanceToGoal()
            );

            telemetry.addData(
                    "Yaw to Goal (deg)",
                    "%.1f",
                    vision.getYawToGoalDeg()
            );

            telemetry.addData(
                    "Aim Accuracy (%)",
                    "%.1f",
                    vision.getAimAccuracyPercent()
            );

            telemetry.addData(
                    "Suggested Shot",
                    vision.getSuggestedShot()
            );
        } else {
            telemetry.addLine("No goal detected");
        }
    }

    public static void teleOuttake(
            Telemetry telemetry,
            ArtifactLauncher outtake
    ) {
        telemetry.addData(
                "Velocity",
                "%.0f / %.0f",
                outtake.getActualVelocity(),
                outtake.getTargetVelocity()
        );
        telemetry.addData(
                "Ready",
                outtake.isReadyToShoot() ? "YES" : "NO"
        );
        telemetry.addData("Power level", outtake.getPowerLevel());
        PanelsData.launchPower = outtake.getTargetVelocity();
        PanelsData.isReady = outtake.isReadyToShoot();
    }


//    public static void teleVision(Telemetry telemetry, VisionS) {
//        telemetry.addData("Is enabled", vi)
//    }

    /* =======================
     * LOGCAT
     * ======================= */

    public static void logCat(String tag, String message) {
        if (Constants.debugMode) {
            RobotLog.dd(tag, message);
        }
    }

    /* =======================
     * MATH UTILITIES
     * ======================= */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double degToRad(double degrees) {
        return Math.toRadians(degrees);
    }

    public static double radToDeg(double radians) {
        return Math.toDegrees(radians);
    }

    public static double normalizeAngle(double angle) {
        while (angle > Math.PI)  angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    /* =======================
     * ENCODER UTILITIES
     * ======================= */

    public static double inchesToTicks(double inches) {
        return (inches / (2 * Math.PI * Constants.wheelRads)) *
                Constants.ticksPerRev * Constants.gearRatio;
    }

    public static double ticksToInches(double ticks) {
        return (ticks / (Constants.ticksPerRev * Constants.gearRatio)) *
                (2 * Math.PI * Constants.wheelRads);
    }

    /* =======================
     * JOYSTICK HELPERS
     * ======================= */

    public static double applyDeadzone(double value) {
        return Math.abs(value) < Constants.deadzone ? 0.0 : value;
    }

    /* =======================
     * MISC
     * ======================= */

    public static String formatDrivePower(
            double fl, double fr, double bl, double br
    ) {
        return String.format(
                "FL: %.2f | FR: %.2f | BL: %.2f | BR: %.2f",
                fl, fr, bl, br
        );
    }

    public static String boolToStr(boolean val) {
        return val ? "ON" : "OFF";
    }

    public static double average(double... values) {
        double sum = 0;
        for (double v : values) sum += v;
        return values.length > 0 ? sum / values.length : 0;
    }

    /* =======================
     * TIME
     * ======================= */

    public static void resetRuntime() {
        runtime.reset();
    }

    public static double getRuntime() {
        return runtime.seconds();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
