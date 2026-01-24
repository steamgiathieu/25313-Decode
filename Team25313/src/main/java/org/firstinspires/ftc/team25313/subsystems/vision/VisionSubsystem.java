package org.firstinspires.ftc.team25313.subsystems.vision;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class VisionSubsystem {

    public enum Alliance { blue, red }
    public enum ShotLevel { near, mid, far, none }

    private final AprilTagProcessor tagProcessor;

    private Alliance alliance;
    private int goalTagId;

    private boolean hasTarget = false;
    private double rawDistanceM = -1;
    private double filteredDistanceM = -1;
    private double yawDeg = 0;

    public VisionSubsystem(AprilTagProcessor processor, Alliance alliance) {
        this.tagProcessor = processor;
        setAlliance(alliance);
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
        goalTagId = (alliance == Alliance.blue)
                ? Constants.blueGoalTagId
                : Constants.redGoalTagId;
    }

    public void update() {
        hasTarget = false;
        rawDistanceM = -1;
        yawDeg = 0;

        for (AprilTagDetection det : tagProcessor.getDetections()) {
            if (det.metadata == null) continue;
            if (det.id != goalTagId) continue;

            double dist = det.ftcPose.range;

            if (dist < Constants.minValidTagDistM ||
                    dist > Constants.maxValidTagDistM) {
                continue;
            }

            rawDistanceM = dist;
            yawDeg = det.ftcPose.yaw;
            hasTarget = true;
            break;
        }

        if (hasTarget) {
            if (filteredDistanceM < 0) {
                filteredDistanceM = rawDistanceM;
            } else {
                filteredDistanceM =
                        Constants.distanceAlpha * rawDistanceM +
                                (1 - Constants.distanceAlpha) * filteredDistanceM;
            }
        }
    }

    public ShotLevel getShotLevel() {
        if (!hasTarget) return ShotLevel.none;

        if (filteredDistanceM <= Constants.shotNearMaxDist) {
            return ShotLevel.near;
        } else if (filteredDistanceM <= Constants.shotMidMaxDist) {
            return ShotLevel.mid;
        } else {
            return ShotLevel.far;
        }
    }

    public double getAllowedYawDeg() {
        switch (getShotLevel()) {
            case near: return Constants.yawNearDeg;
            case mid:  return Constants.yawMidDeg;
            case far:  return Constants.yawFarDeg;
            default:   return 0;
        }
    }

    public boolean canShoot() {
        if (!hasTarget) return false;
        return Math.abs(yawDeg) <= getAllowedYawDeg();
    }

    public double getRotateHint() {
        if (!hasTarget) return 0;

        double maxYaw = getAllowedYawDeg();
        if (maxYaw <= 0) return 0;

        return yawDeg / maxYaw; // -1 .. 1
    }

    public double getAimAccuracyPercent() {
        if (!hasTarget) return 0;

        double error = Math.abs(yawDeg);
        double allowed = getAllowedYawDeg();

        if (allowed <= 0) return 0;

        double acc = 1.0 - (error / allowed);
        return Math.max(0, Math.min(acc, 1.0)) * 100.0;
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public double getDistanceMeters() {
        return filteredDistanceM;
    }

    public double getYawDeg() {
        return yawDeg;
    }

    public Alliance getAlliance() { return alliance; }
}