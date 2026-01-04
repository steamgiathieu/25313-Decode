package org.firstinspires.ftc.team25313.subsystems.vision;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class VisionSubsystem {

    private VisionPortal portal;
    private AprilTagProcessor tagProcessor;

    private Constants.AllianceColor alliance;

    private double lastDistance = -1;
    private boolean hasValidTag = false;

    public VisionSubsystem(HardwareMap hardwareMap, Constants.AllianceColor alliance) {
        this.alliance = alliance;

        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(false)
                .setDrawCubeProjection(false)
                .setDrawTagOutline(false)
                .build();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(tagProcessor)
                .build();

        portal.stopStreaming();
    }

    public boolean hasTarget() {
        return hasValidTag;
    }

    public double getDistance() {
        return lastDistance;
    }

    public double getRecommendedVelocity() {
        if (!hasValidTag) return -1;

        double baseDist = 70;
        double goalDist = 30;

        double baseVel = 4300;
        double goalVel = 3600;

        double d = Math.min(Math.max(lastDistance, goalDist), baseDist);

        return goalVel +
                (baseVel - goalVel) * (d - goalDist) / (baseDist - goalDist);
    }

    private int getTargetTagId() {
        return alliance == Constants.AllianceColor.blue ? 20 : 24;
    }

    public void update() {
        hasValidTag = false;

        for (AprilTagDetection detection : tagProcessor.getDetections()) {

            if (detection.id == getTargetTagId()) {
                lastDistance = detection.ftcPose.range; // inch
                hasValidTag = true;
                break;
            }
        }
    }

    public void enablePanelsStream() {
        portal.resumeStreaming();
    }

    public void disablePanelsStream() {
        portal.stopStreaming();
    }
}
