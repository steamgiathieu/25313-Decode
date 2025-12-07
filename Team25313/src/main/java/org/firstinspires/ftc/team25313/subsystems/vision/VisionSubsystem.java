package org.firstinspires.ftc.team25313.subsystems.vision;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class VisionSubsystem {
    private final VisionPortal portal;
    private final AprilTagProcessor tag;

    public VisionSubsystem(HardwareMap hw) {
        tag = new AprilTagProcessor.Builder().build();

        portal = new VisionPortal.Builder()
                .setCamera(hw.get(WebcamName.class, "Webcam 1"))
                .addProcessor(tag)
                .enableLiveView(true)
                .build();
    }

    public VisionPortal getPortal() {
        return portal;
    }
}
