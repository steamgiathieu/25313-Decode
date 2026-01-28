package org.firstinspires.ftc.team25313.opmodes.teleop;

import android.util.Size;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.Locale;

@TeleOp(name = "Utility: Camera Frame Capture", group = "Utility")
public class UtilityCameraFrameCapture extends LinearOpMode
{
    final boolean USING_WEBCAM = true;
    final BuiltinCameraDirection INTERNAL_CAM_DIR = BuiltinCameraDirection.BACK;
    final int RESOLUTION_WIDTH = 640;
    final int RESOLUTION_HEIGHT = 480;

    boolean lastX;
    int frameCount;
    long capReqTime;

    @Override
    public void runOpMode()
    {
        VisionPortal portal = null;

        VisionPortal.Builder builder = new VisionPortal.Builder()
                .setCameraResolution(new Size(RESOLUTION_WIDTH, RESOLUTION_HEIGHT))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG) // MJPEG giúp stream mượt hơn trên Driver Hub
                .setAutoStopLiveView(false); // Không tự động tắt stream khi OpMode dừng ở Init

        if (USING_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, Constants.webcamName));
        } else {
            builder.setCamera(INTERNAL_CAM_DIR);
        }

        portal = builder.build();

        // Tích hợp stream hình ảnh lên FTC Dashboard (để xem trên laptop)
        FtcDashboard.getInstance().startCameraStream(portal, 0);

        // Đợi camera khởi động
        while (!isStopRequested() && portal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Status", "Waiting for camera...");
            telemetry.update();
        }

        telemetry.addLine("Camera Ready!");
        telemetry.addLine("Để xem trên Driver Hub: Bấm 3 dấu chấm -> Camera Stream");
        telemetry.update();

        while (!isStopRequested())
        {
            boolean x = gamepad1.x;

            if (x && !lastX)
            {
                portal.saveNextFrameRaw(String.format(Locale.US, "CameraFrameCapture-%06d", frameCount++));
                capReqTime = System.currentTimeMillis();
            }
            lastX = x;

            if (gamepad1.y) {
                portal.resumeStreaming();
            } else if (gamepad1.a) {
                portal.stopStreaming();
            }

            telemetry.addLine("######## Camera Capture Utility ########");
            telemetry.addData(" > Camera Status", portal.getCameraState());
            telemetry.addLine(" > Press X to capture a frame");
            telemetry.addLine(" > Press Y to Resume | A to Stop Streaming");
            telemetry.addLine("\nĐể xem stream trên Driver Hub:");
            telemetry.addLine("1. Bấm nút 3 chấm góc trên bên phải DS");
            telemetry.addLine("2. Chọn 'Camera Stream'");

            if (capReqTime != 0) {
                telemetry.addLine("\nCAPTURED FRAME!");
            }

            if (capReqTime != 0 && System.currentTimeMillis() - capReqTime > 1000) {
                capReqTime = 0;
            }

            telemetry.update();
            sleep(20);
        }

        if (portal != null) {
            portal.close();
        }
    }
}
