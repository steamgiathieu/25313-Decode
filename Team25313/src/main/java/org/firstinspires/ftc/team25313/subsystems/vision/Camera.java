package org.firstinspires.ftc.team25313.subsystems.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.team25313.FTCObject;

public class Camera extends FTCObject {
    private VisionPortal visionPortal;
    private WebcamName webcamName;

    public Camera(LinearOpMode opMode) {
        super(opMode);
    }

    public void initCamera() {
        webcamName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        visionPortal = new VisionPortal.Builder()
                .setCamera(webcamName)
                .setCameraResolution(new android.util.Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();
    }

    public VisionPortal getVisionPortal() {
        return visionPortal;
    }

    public void closeCamera() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
