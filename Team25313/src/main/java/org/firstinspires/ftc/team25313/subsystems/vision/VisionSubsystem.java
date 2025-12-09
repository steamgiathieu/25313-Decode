package org.firstinspires.ftc.team25313.subsystems.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.VisionPortal;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class VisionSubsystem {

    public VisionPortal portal;
    public AprilTagProcessor tag;

    // Lưu thông tin AprilTag hiện thời
    private AprilTagDetection bestTag;
    private GoalPose latestGoalPose;
    private ObeliskPattern.Color[] latestPattern;

    public VisionSubsystem(HardwareMap hw) {

        tag = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        portal = new VisionPortal.Builder()
                .setCamera(hw.get(WebcamName.class, Constants.webcamName))
                .addProcessor(tag)
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .build();
    }

    // Cập nhật khung hình
    public void update() {
        List<AprilTagDetection> dets = tag.getDetections();
        if (dets == null || dets.isEmpty()) {
            bestTag = null;
            latestGoalPose = null;
            latestPattern = null;
            return;
        }

        bestTag = dets.get(0);   // lấy tag gần nhất đã qua filter của SDK

        int id = bestTag.id;

        // ---- Nếu là tag goal (ID 24 đỏ, 20 xanh) ----
        if (id == 24 || id == 20) {
            double tx = bestTag.ftcPose.x;
            double ty = bestTag.ftcPose.y;
            double tz = bestTag.ftcPose.z;
            double yaw = bestTag.ftcPose.yaw;

            latestGoalPose = new GoalPose(tx, ty, tz, yaw);
        }

        // ---- Nếu là obelisk motif ----
        else if (id == 21 || id == 22 || id == 23) {
            latestPattern = ObeliskPattern.decode(id);
        }
    }

    // GETTERS

    public boolean hasTag() { return bestTag != null; }

    public AprilTagDetection getCurrentTag() { return bestTag; }

    public GoalPose getGoalPose() { return latestGoalPose; }

    public ObeliskPattern.Color[] getObeliskPattern() { return latestPattern; }
}
