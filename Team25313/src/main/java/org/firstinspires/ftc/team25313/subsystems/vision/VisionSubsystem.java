package org.firstinspires.ftc.team25313.subsystems.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class VisionSubsystem {

    public enum Alliance {
        blue, red
    }

    private final WebcamName webcamName;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private final Telemetry telemetry;
    private Alliance alliance;

    public VisionSubsystem(WebcamName webcamName, Telemetry telemetry) {
        this.webcamName = webcamName;
        this.telemetry = telemetry;
        this.alliance = Alliance.blue;
    }

    public VisionSubsystem(AprilTagProcessor tagProcessor, Alliance alliance) {
        this.webcamName = null;
        this.telemetry = null;
        this.aprilTag = tagProcessor;
        this.alliance = alliance;
    }

    public boolean initialize() {
        try {
            if (aprilTag == null) {
                aprilTag = AprilTagProcessor.easyCreateWithDefaults();
            }
            if (visionPortal == null && webcamName != null) {
                // Sử dụng cấu hình mặc định, LiveView sẽ tự động hiển thị trên Robot Controller (nếu có màn hình)
                // và có thể xem được qua Driver Station "Camera Stream".
                visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTag);
            }
            return true;
        } catch (Exception e) {
            if (telemetry != null) telemetry.addData("Vision Error", e.getMessage());
            return false;
        }
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public void update() {
        processFrame();
    }

    public void processFrame() {
        if (visionPortal != null && telemetry != null) {
            telemetry.addData("Camera State", visionPortal.getCameraState());
        }
    }

    public boolean hasTarget() {
        return aprilTag != null && !aprilTag.getDetections().isEmpty();
    }

    public double getDistanceToGoal() {
        return 0.0;
    }

    public double getYawToGoalDeg() {
        return 0.0;
    }

    public String getSuggestedShot() {
        return "None";
    }

    public double getAimAccuracyPercent() {
        return 0.0;
    }

    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
            visionPortal = null;
        }
    }

    public AprilTagProcessor getAprilTagProcessor() {
        return aprilTag;
    }
}
