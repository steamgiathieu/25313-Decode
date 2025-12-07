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

    // Constructor for VisionSubsystem that takes the webcam and telemetry
    public VisionSubsystem(WebcamName webcamName, Telemetry telemetry) {
        this.webcamName = webcamName;
        this.telemetry = telemetry;
    }

    // Initialize the camera
    public boolean initialize() {
        try {
            // Create the AprilTag processor.
            aprilTag = AprilTagProcessor.easyCreateWithDefaults();

            // Create the vision portal the easy way.
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    webcamName, aprilTag);

            telemetry.addData("Vision", "Camera initialized.");
            return true;
        } catch (Exception e) {
            telemetry.addData("Vision", "Camera initialization failed: " + e.getMessage());
            telemetry.update();
            return false;
        }
    }

    // Process the frame (you can add your vision processing logic here)
    public void processFrame() {
        if (visionPortal != null) {
            // Get detections from AprilTag processor
            int detectionCount = aprilTag.getDetections().size();
            telemetry.addData("Vision Status", "Streaming");
            telemetry.addData("AprilTags Detected", detectionCount);
        } else {
             telemetry.addData("Vision Status", "Camera not initialized");
        }
        telemetry.update();
    }

    // Close the camera when done
    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }

    public AprilTagProcessor getAprilTagProcessor() {
        return aprilTag;
    }
}
