package org.firstinspires.ftc.team25313.subsystems.vision;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.apriltag.*;

import java.util.List;

public class VisionSubsystem {

    public enum Alliance {
        blue,
        red
    }

    public enum ShotLevel {
        near,
        mid,
        far,
        none
    }

    private final AprilTagProcessor tagProcessor;

    private Alliance alliance = Alliance.blue;
    private int goalTagId;

    private double distanceToGoal = -1;   // meters
    private double yawToGoalDeg = 0;       // degrees
    private boolean hasTarget = false;

    public VisionSubsystem(AprilTagProcessor tagProcessor, Alliance alliance) {
        this.tagProcessor = tagProcessor;
        setAlliance(alliance);
    }

    /* ===================== CONFIG ===================== */

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
        goalTagId = (alliance == Alliance.blue)
                ? Constants.blueGoalTagId
                : Constants.redGoalTagId;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    /* ===================== UPDATE ===================== */

    public void update() {
        hasTarget = false;
        distanceToGoal = -1;
        yawToGoalDeg = 0;

        List<AprilTagDetection> detections = tagProcessor.getDetections();

        for (AprilTagDetection det : detections) {
            if (det.metadata == null) continue;
            if (det.id != goalTagId) continue;

            distanceToGoal = det.ftcPose.range;
            yawToGoalDeg = det.ftcPose.yaw;
            hasTarget = true;
            break;
        }
    }

    /* ===================== LOGIC ===================== */

    public ShotLevel getSuggestedShot() {
        if (!hasTarget) return ShotLevel.none;

        if (distanceToGoal <= Constants.shotNearMaxDist) {
            return ShotLevel.near;
        } else if (distanceToGoal <= Constants.shotMidMaxDist) {
            return ShotLevel.mid;
        } else {
            return ShotLevel.far;
        }
    }

    public double getAimAccuracyPercent() {
        if (!hasTarget) return 0;

        double error = Math.abs(yawToGoalDeg);
        double accuracy = 1.0 - (error / Constants.maxAimYawDeg);
        return Math.max(0, Math.min(accuracy, 1.0)) * 100.0;
    }

    public double getRotateHint() {
        if (!hasTarget) return 0;
        return yawToGoalDeg / Constants.maxAimYawDeg;
    }

    /* ===================== GETTERS ===================== */

    public boolean hasTarget() {
        return hasTarget;
    }

    public double getDistanceToGoal() {
        return distanceToGoal;
    }

    public double getYawToGoalDeg() {
        return yawToGoalDeg;
    }
}
