package org.firstinspires.ftc.team25313.subsystems.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

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
            // Sử dụng easyCreateWithDefaults để đảm bảo độ ổn định cao nhất
            if (aprilTag == null) {
                aprilTag = AprilTagProcessor.easyCreateWithDefaults();
            }
            if (visionPortal == null) {
                visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTag);
            }
            return true;
        } catch (Exception e) {
            telemetry.addData("Vision Error", e.getMessage());
            return false;
        }
    }

    public void processFrame() {
        if (visionPortal != null) {
            telemetry.addData("Camera State", visionPortal.getCameraState());
        }
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
