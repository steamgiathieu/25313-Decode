package org.firstinspires.ftc.team25313.subsystems.vision;

import android.util.Size;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.robotcore.util.Range;

import java.util.List;

public class VisionSubsystem {

    private final WebcamName webcamName;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private final Telemetry telemetry;

    public VisionSubsystem(WebcamName webcamName, Telemetry telemetry) {
        this.webcamName = webcamName;
        this.telemetry = telemetry;
    }

    /**
     * Small DTO containing suggested movement corrections (in centimeters/degrees) to align robot
     * with the detected AprilTag. Use from teleop: read this and apply as small closed-loop
     * setpoints for drive (strafe/forward/rotate).
     *
     * Conventions (based on the AprilTag ftcPose provided by the Vision library):
     *  - x: lateral offset (cm). Positive means tag is to the robot's right -> robot should strafe right to reduce x.
     *  - y: forward offset (cm). Positive means tag is in front of the robot -> robot should drive forward to reduce y.
     *  - yaw: tag yaw relative to camera (degrees). Positive means tag rotated clockwise -> robot should rotate clockwise to reduce yaw.
     */
    public static class PositionCorrection {
        public boolean valid = false;
        public double strafeCm = 0;   // positive -> strafe right
        public double forwardCm = 0;  // positive -> drive forward
        public double rotateDeg = 0;  // positive -> rotate clockwise
        public double distanceCm = 0; // current range to tag
        public int tagId = -1;
    }

    /**
     * Compute a suggested correction to move the robot so the tag is at (desiredX, desiredY, desiredYaw).
     * All inputs/outputs are in centimeters/degrees. Caller should convert these into motor power or
     * drivetrain commands using their own control loops (PID/closed-loop). Outputs are clamped to the
     * provided max values to keep teleop corrections safe.
     *
     * @param desiredXcm lateral offset we want the tag to have relative to robot (cm). Typically 0.
     * @param desiredYcm forward offset we want the tag to have relative to robot (cm). Typically 0.
     * @param desiredYawDeg desired yaw of tag relative to robot (deg). Typically 0.
     * @param maxStrafeCm clamp for lateral correction magnitude
     * @param maxForwardCm clamp for forward correction magnitude
     * @param maxRotateDeg clamp for rotation correction magnitude
     * @return PositionCorrection with suggested strafe/forward/rotate adjustments (or valid=false when no tag).
     */
    public PositionCorrection computePositionCorrection(double desiredXcm, double desiredYcm, double desiredYawDeg,
                                                       double maxStrafeCm, double maxForwardCm, double maxRotateDeg) {
        PositionCorrection pc = new PositionCorrection();
        AprilTagDetection det = getTargetDetection();
        if (det == null || det.ftcPose == null) {
            pc.valid = false;
            return pc;
        }

        // Current measured pose of tag from camera
        double curX = det.ftcPose.x; // lateral (cm)
        double curY = det.ftcPose.y; // forward (cm)
        double curYaw = det.ftcPose.yaw; // degrees

        // error = desired - current : positive means we need to move in that positive direction
        double errX = desiredXcm - curX;
        double errY = desiredYcm - curY;
        double errYaw = desiredYawDeg - curYaw;

        // clamp suggested corrections to safe ranges
        pc.strafeCm = Range.clip(errX, -Math.abs(maxStrafeCm), Math.abs(maxStrafeCm));
        pc.forwardCm = Range.clip(errY, -Math.abs(maxForwardCm), Math.abs(maxForwardCm));
        pc.rotateDeg = Range.clip(errYaw, -Math.abs(maxRotateDeg), Math.abs(maxRotateDeg));

        pc.distanceCm = det.ftcPose.range;
        pc.tagId = det.id;
        pc.valid = true;

        if (telemetry != null) {
            telemetry.addData("VisionCorrection", "tag=%d dist=%.1fcm strafe=%.1fcm forward=%.1fcm rot=%.1fdeg",
                    pc.tagId, pc.distanceCm, pc.strafeCm, pc.forwardCm, pc.rotateDeg);
        }

        return pc;
    }

    /**
     * Convenience wrapper for teleop: suggest corrections aiming to center the tag (0,0,0) and clamp
     * to conservative values. Tweak max values if you need stronger corrections.
     */
    public PositionCorrection getTeleopCorrection() {
        // default clamps (cm / deg) - adjust as needed for your robot
        return computePositionCorrection(0.0, 0.0, 0.0, 30.0, 30.0, 15.0);
    }

    public boolean initialize() {
        try {
            if (aprilTag == null) {
                AprilTagLibrary myTagLibrary = new AprilTagLibrary.Builder()
                        .addTag(Constants.blueGoalTagId, "Blue Goal", Constants.aprilTagSize, DistanceUnit.INCH)
                        .addTag(Constants.redGoalTagId, "Red Goal", Constants.aprilTagSize, DistanceUnit.INCH)
                        .build();

                // Sử dụng lại thông số Calib gốc vì đã quay lại độ phân giải 1920x1080
                aprilTag = new AprilTagProcessor.Builder()
                        .setLensIntrinsics(
                                Constants.cameraFx, 
                                Constants.cameraFy, 
                                Constants.cameraCx, 
                                Constants.cameraCy)
                        .setTagLibrary(myTagLibrary)
                        .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                        .build();
            }

            if (visionPortal == null && webcamName != null) {
                visionPortal = new VisionPortal.Builder()
                        .setCamera(webcamName)
                        .setCameraResolution(new Size(1920, 1080)) // Quay lại 1920x1080 theo hỗ trợ của cam
                        .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                        .addProcessor(aprilTag)
                        .build();
            }
            return true;
        } catch (Exception e) {
            if (telemetry != null) telemetry.addData("Vision Error", e.getMessage());
            return false;
        }
    }

    public AprilTagDetection getTargetDetection() {
        if (aprilTag == null) return null;
        List<AprilTagDetection> detections = aprilTag.getDetections();
        AprilTagDetection bestDetection = null;
        double minBearing = 1000;

        for (AprilTagDetection detection : detections) {
            if (detection.ftcPose != null) {
                if (Math.abs(detection.ftcPose.bearing) < minBearing) {
                    minBearing = Math.abs(detection.ftcPose.bearing);
                    bestDetection = detection;
                }
            }
        }
        return bestDetection;
    }

    public double getBearingToGoalDeg() {
        AprilTagDetection det = getTargetDetection();
        if (det == null) return 0.0;

        double bearing = det.ftcPose.bearing + Constants.visionAimOffset;

        if (det.id == Constants.blueGoalTagId) {
            bearing -= Constants.visionAimOffset;
        } else if (det.id == Constants.redGoalTagId) {
            bearing += Constants.visionAimOffset;
        }
        return bearing;
    }

    public double getDistanceToGoal() {
        AprilTagDetection det = getTargetDetection();
        return (det != null) ? det.ftcPose.range : 0.0;
    }

    public double getXToGoal() {
        AprilTagDetection det = getTargetDetection();
        return (det != null) ? det.ftcPose.x : 0.0;
    }

    public double getYToGoal() {
        AprilTagDetection det = getTargetDetection();
        return (det != null) ? det.ftcPose.y : 0.0;
    }

    public double getYawToGoalDeg() {
        AprilTagDetection det = getTargetDetection();
        return (det != null) ? det.ftcPose.yaw : 0.0;
    }

    public String getSuggestedShot() {
        double dist = getDistanceToGoal(); 
        if (dist <= 0) return "NONE";
        if (dist < 120.0) return "NEAR";
        if (dist < 200.0) return "MID";
        return "FAR";
    }

    public void update() {
        if (telemetry != null) {
            AprilTagDetection det = getTargetDetection();
            if (det != null) {
                telemetry.addData("Vision", "Tag %d spotted (1920x1080 MJPEG)", det.id);
                telemetry.addData("  Final Bearing", "%.2f deg", getBearingToGoalDeg());
                telemetry.addData("  Distance", "%.1f cm", det.ftcPose.range);
            }
        }
    }

    public boolean hasTarget() { return getTargetDetection() != null; }
    public AprilTagProcessor getAprilTagProcessor() { return aprilTag; }
    public void close() { if (visionPortal != null) visionPortal.close(); }
}
