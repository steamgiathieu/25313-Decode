package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
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

        // 2. Khởi tạo Camera NGAY LẬP TỨC (để xem được camera stream trước khi bấm Start)
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
            // Hiển thị số lượng Tag ngay trong lúc chờ để test
            List<AprilTagDetection> detections = visionSubsystem.getAprilTagProcessor().getDetections();
            telemetry.addData("Tags in View", detections.size());
            telemetry.update();
            sleep(20);
        }

        if (isStopRequested()) return;

        timer.reset();

        // 4. Vòng lặp chính
        while (opModeIsActive()) {
            // Xử lý hình ảnh
            visionSubsystem.processFrame();
            List<AprilTagDetection> currentDetections = visionSubsystem.getAprilTagProcessor().getDetections();
            
            telemetry.addData("# Tags Detected", currentDetections.size());

            for (AprilTagDetection detection : currentDetections) {
                if (detection.ftcPose != null) {
                    telemetry.addLine(String.format("\n> TAG ID %d", detection.id));
                    telemetry.addData("  X", "%.2f", -detection.ftcPose.x);
                    telemetry.addData("  Y", "%.2f", -detection.ftcPose.y);
                }
            }

            telemetry.addData("Timer", "%.2f s", timer.seconds());
            telemetry.update();
        }

        visionSubsystem.close();
    }
}
