package org.firstinspires.ftc.team25313.subsystems.vision;
import org.firstinspires.ftc.team25313.Constants;

import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.openftc.apriltag.AprilTagDetection;


import java.util.List;

public class AprilTagProcessor {

    public enum TargetType {
        none,
        goal,   // trụ bắn bóng
        obelisk     // motif xếp bóng
    }

    private static Constants.AllianceColor allianceColor = Constants.currentAlliance;
    private boolean lockOnce = false;     // nếu true → chỉ đọc tag 1 lần
    private boolean locked = false;       // đã đọc xong chưa

    private AprilTagDetection latestTag;
    private double distanceToTag;
    private double yawError;

    // ID
    public int[] blueAllowedTags = {21, 22, 23, 20};
    public int[] redAllowedTags  = {21, 22, 23, 24};

    public AprilTagProcessor() {}

    public void setAlliance(Constants.AllianceColor a) {
        this.allianceColor = a;
    }

    public void lockAfterFirstDetect(boolean enable) {
        this.lockOnce = enable;
    }

    // Find right tag for alliance
    private boolean isTagAllowed(int id) {
        if (allianceColor == Constants.currentAlliance) {
            for (int tag : blueAllowedTags) if (tag == id) return true;
        } else {
            for (int tag : redAllowedTags) if (tag == id) return true;
        }
        return false;
    }

    // Call for frame
    public void update(List<AprilTagDetection> detections) {
        if (lockOnce && locked) return; // lock to avoid lag

        latestTag = null;

        for (AprilTagDetection det : detections) {
            if (isTagAllowed(det.id)) {
                latestTag = det;
                break;  // just take the legit input
            }
        }

        if (latestTag != null) {
            computeTagInfo(latestTag);

            if (lockOnce) locked = true;
        }
    }

    // Calculate from Tag
    private void computeTagInfo(AprilTagDetection det) {
        // d from tag to camera (m to cm)
        double dx = det.pose.x;
        double dy = det.pose.y;
        double dz = det.pose.z;

        distanceToTag = Math.sqrt(dx*dx + dy*dy + dz*dz) * 100.0;
        yawError = Math.toDegrees(Math.atan2(dx, dz));
    }

    // Get-ter

    public boolean hasTag() {
        return latestTag != null;
    }

    public int getTagId() {
        return latestTag != null ? latestTag.id : -1;
    }

    public double getDistanceCm() {
        return distanceToTag;
    }

    public double getYawError() {
        return yawError;
    }

    // Sort Tag type
    public TargetType getTargetType() {
        if (!hasTag()) return TargetType.none;

        int id = latestTag.id;

        if (id == 1 || id == 2) return TargetType.obelisk;
        if (id == 3) return TargetType.goal;

        return TargetType.none;
    }
}
