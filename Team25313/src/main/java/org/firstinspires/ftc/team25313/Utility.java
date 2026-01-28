package org.firstinspires.ftc.team25313;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

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
        telemetry.addData(
                "PIDF",
                "P: %.4f | I: %.4f | D: %.4f | F: %.4f",
                Constants.launcherP,
                Constants.launcherI,
                Constants.launcherD,
                Constants.adaptive_launcherF
        );
        telemetry.addData("Intake Power: ", "%.4f", Constants.intakeMotorShoot);

        PanelsData.launchPower = outtake.getTargetVelocity();
        PanelsData.isReady = outtake.isReadyToShoot();
    }


    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double normalizeAngle(double angle) {
        while (angle > Math.PI)  angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

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
