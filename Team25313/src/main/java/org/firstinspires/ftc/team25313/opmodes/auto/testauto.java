package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveConstants;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous(name = "testauto", group = "Autonomous")
public class testauto extends LinearOpMode {

    private VisionSubsystem visionSubsystem;
    private DriveSubsystem driveSubsystem;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        // 1. Khởi tạo Subsystem
        WebcamName webcam = hardwareMap.get(WebcamName.class, "Webcam 1");
        visionSubsystem = new VisionSubsystem(webcam, telemetry);
        driveSubsystem = new DriveSubsystem(hardwareMap);

        // 2. Khởi tạo Camera
        telemetry.addData("Status", "Initializing Vision...");
        telemetry.update();
        
        if (visionSubsystem.initialize()) {
            telemetry.addData("Status", "Vision Ready - Press Play");
        } else {
            telemetry.addData("Status", "Vision Init FAILED!");
        }
        telemetry.update();

        // 3. Chờ người dùng bấm Start
        while (!isStarted() && !isStopRequested()) {
            List<AprilTagDetection> detections = visionSubsystem.getAprilTagProcessor().getDetections();
            telemetry.addData("Tags in View", detections.size());
            telemetry.update();
            sleep(20);
        }

        if (isStopRequested()) return;

        timer.reset();

        // 4. Vòng lặp chính
        while (opModeIsActive()) {
            double seconds = timer.seconds();

            // ĐIỀU KHIỂN ROBOT ĐI CHÉO (TIẾN - TRÁI)
//            if (seconds < 3.0) {
//                // Sử dụng diagonalWeight để tinh chỉnh độ lệch khi đi chéo
//                // x = -0.5 * diagonalWeight để bù đắp lực cản môi trường
//                double forward = 0.5;
//                double strafe = -0.5 * DriveConstants.diagonalWeight;
//
//                driveSubsystem.drive(forward, strafe, 0);
//                telemetry.addData("Movement", "Diagonal Forward-Left");
//                telemetry.addData("Weight Used", DriveConstants.diagonalWeight);
//            } else {
//                driveSubsystem.drive(0, 0, 0);
//                telemetry.addData("Movement", "Finished - Stopped");
//            }

            // Xử lý hình ảnh AprilTag
            visionSubsystem.processFrame();
            List<AprilTagDetection> currentDetections = visionSubsystem.getAprilTagProcessor().getDetections();
            
            telemetry.addData("# Tags Detected", currentDetections.size());

            for (AprilTagDetection detection : currentDetections) {
                if (detection.ftcPose != null) {
                    telemetry.addLine(String.format("\n> TAG ID %d", detection.id));
                    telemetry.addData("  Bot X relative to Tag", "%.2f inch", -detection.ftcPose.x);
                    telemetry.addData("  Bot Y relative to Tag", "%.2f inch", -detection.ftcPose.y);
                    
                    // Tính khoảng cách 2D (trên mặt đất) và 3D từ Camera đến Tag
                    double dist2D = Math.hypot(detection.ftcPose.x, detection.ftcPose.y);
                    double dist3D = detection.ftcPose.range;

                    telemetry.addData("  Distance 2D to Tag", "%.2f inch", dist2D);
                    telemetry.addData("  Distance 3D to Tag", "%.2f inch", dist3D);
                }
            }

            telemetry.addData("Timer", "%.2f s", seconds);
            telemetry.update();
        }

        driveSubsystem.drive(0, 0, 0);
        visionSubsystem.close();
    }
}
