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
