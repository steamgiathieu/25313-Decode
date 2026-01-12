package org.firstinspires.ftc.team25313;

//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
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
 * - Panels-safe (init 1 lần, không crash)
 */
public final class Utility {

    /* =======================
     * TIME
     * ======================= */

    private static final ElapsedTime runtime = new ElapsedTime();

    private Utility() { }

    /* =======================
     * PANELS CORE
     * ======================= */

//    private static TelemetryManager panels;

    /** GỌI 1 LẦN trong init() của BẤT KỲ OpMode nào */
//    public static void initPanels() {
//        if (panels != null) return;
//
//        try {
//            panels = PanelsTelemetry.INSTANCE.getTelemetry();
//            panels.debug("Panels", "Initialized");
//        } catch (Exception e) {
//            panels = null; // chạy không có Panels vẫn OK
//        }
//    }

//    public static boolean panelsEnabled() {
//        return panels != null;
//    }

    /* =======================
     * PANELS DATA
     * Panels đọc trực tiếp
     * ======================= */

    public static final class PanelsData {
        // Drivetrain
        public static double x;
        public static double y;
        public static double rotate;

        // Vision
        public static int tagId = -1;
        public static double distance;
        public static double yawDeg;
        public static double accuracy;
        public static String suggestedShot = "NONE";

        // Intake
        public static boolean isIntake;

        // Outtake
        public static boolean isReady;
        public static double launchPower;
    }

    /* =======================
     * TELEMETRY HELPERS
     * ======================= */

    public static void teleAdd(Telemetry telemetry, String caption, Object value) {
        telemetry.addData(caption, value);
    }

    /** GỌI 1 LẦN / loop */
    public static void teleUpdate(Telemetry telemetry) {
        telemetry.update();
//        if (panels != null) {
//            panels.update(telemetry);
//        }
    }

    /* =======================
     * HIGH-LEVEL TELEMETRY
     * ======================= */

    public static void teleDrivePose(
            Telemetry telemetry,
            double x, double y, double rotate
    ) {
        telemetry.addData("X", x);
        telemetry.addData("Y", y);
        telemetry.addData("Rotate", rotate);

        PanelsData.x = x;
        PanelsData.y = y;
        PanelsData.rotate = rotate;
    }

    public static void teleVision(
            Telemetry telemetry,
            VisionSubsystem vision
    ) {
        telemetry.addData("Vision Target", vision.hasTarget());

        if (vision.hasTarget()) {
            telemetry.addData("Distance (m)", "%.2f", vision.getDistanceToGoal());
            telemetry.addData("Yaw (deg)", "%.1f", vision.getYawToGoalDeg());
            telemetry.addData("Accuracy (%)", "%.1f", vision.getAimAccuracyPercent());
            telemetry.addData("Shot", vision.getSuggestedShot());

            PanelsData.distance = vision.getDistanceToGoal();
            PanelsData.yawDeg = vision.getYawToGoalDeg();
            PanelsData.accuracy = vision.getAimAccuracyPercent();
            PanelsData.suggestedShot = vision.getSuggestedShot().name();
        } else {
            telemetry.addLine("No goal detected");

            PanelsData.suggestedShot = "NONE";
            PanelsData.accuracy = 0;
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
        telemetry.addData("Ready", outtake.isReadyToShoot());
        telemetry.addData("Power", outtake.getPowerLevel());

        PanelsData.launchPower = outtake.getTargetVelocity();
        PanelsData.isReady = outtake.isReadyToShoot();
    }

    /* =======================
     * LOGCAT
     * ======================= */

    public static void logCat(String tag, String message) {
        if (Constants.debugMode) {
            RobotLog.dd(tag, message);
        }
    }

    /* =======================
     * MATH
     * ======================= */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double normalizeAngle(double angle) {
        while (angle > Math.PI)  angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    /* =======================
     * ENCODER
     * ======================= */

    public static double inchesToTicks(double inches) {
        return (inches / (2 * Math.PI * Constants.wheelRads))
                * Constants.ticksPerRev * Constants.gearRatio;
    }

    public static double ticksToInches(double ticks) {
        return (ticks / (Constants.ticksPerRev * Constants.gearRatio))
                * (2 * Math.PI * Constants.wheelRads);
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

    public static double applyDeadzone(double value) {
        return Math.abs(value) < Constants.deadzone ? 0.0 : value;
    }
}
