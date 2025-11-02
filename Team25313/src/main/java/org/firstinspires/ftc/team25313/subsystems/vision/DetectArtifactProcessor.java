package org.firstinspires.ftc.team25313.subsystems.vision;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.FTCObject;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class DetectArtifactProcessor extends FTCObject {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private WebcamName webcamName;

    private AprilTagDetection latestDetection = null;

    public DetectArtifactProcessor(LinearOpMode opMode) {
        super(opMode);
    }

    public void init() {
        webcamName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .setDrawCubeProjection(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(webcamName)
                .addProcessor(aprilTagProcessor)
                .build();
    }

    public void updateDetection() {
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();
        if (!detections.isEmpty()) {
            latestDetection = detections.get(0); // lấy tag gần nhất
            addData("Tag ID", String.valueOf(latestDetection.id));
            addData("Distance (m)", String.format("%.2f", latestDetection.ftcPose.range));
            addData("Yaw", String.format("%.2f", latestDetection.ftcPose.yaw));
            update();
        } else {
            latestDetection = null;
        }
    }

    public AprilTagDetection getLatestDetection() {
        return latestDetection;
    }

    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
