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
 * - Units: Centimeters (cm) and Degrees (deg)
 */
public final class Utility {

    private static final ElapsedTime runtime = new ElapsedTime();

    private Utility() { }

    /* =======================
     * PANELS DATA (STATIC)
     * Updated for detailed Vision info
     * ======================= */

    public static final class PanelsData {
        // Drivetrain
        public static double x;
        public static double y;
        public static double rotate;

        // Vision (Updated to CM and Degrees)
        public static int tagId = -1;
        public static double tx, ty, tz;     // Coordinates in CM
        public static double range;          // Direct distance in CM
        public static double bearing;        // Horizontal angle in Deg
        public static double yawDeg;         // Target rotation in Deg
        public static String suggestedShot = "N/A";

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
        boolean hasTarget = vision.hasTarget();
        telemetry.addData("Vision Target", hasTarget ? "LOCKED" : "SEARCHING");

        if (hasTarget) {
            AprilTagDetection det = vision.getTargetDetection();
            
            // Driver Hub Telemetry (All in CM/Deg)
            telemetry.addData("  ID", det.id);
            telemetry.addData("  Dist (cm)", "%.1f", vision.getDistanceToGoal());
            telemetry.addData("  X / Y (cm)", "%.1f / %.1f", vision.getXToGoal(), vision.getYToGoal());
            telemetry.addData("  Final Bearing (deg)", "%.1f", vision.getBearingToGoalDeg());
            telemetry.addData("  Shot", vision.getSuggestedShot());

            // Sync with PanelsData
            PanelsData.tagId = det.id;
            PanelsData.tx = vision.getXToGoal();
            PanelsData.ty = vision.getYToGoal();
            PanelsData.range = vision.getDistanceToGoal();
            PanelsData.bearing = vision.getBearingToGoalDeg();
            PanelsData.yawDeg = vision.getYawToGoalDeg();
            PanelsData.suggestedShot = vision.getSuggestedShot();
        } else {
            telemetry.addLine("No goal detected");
            PanelsData.tagId = -1;
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

    public static double applyDeadzone(double value) {
        return Math.abs(value) < Constants.deadzone ? 0.0 : value;
    }

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
