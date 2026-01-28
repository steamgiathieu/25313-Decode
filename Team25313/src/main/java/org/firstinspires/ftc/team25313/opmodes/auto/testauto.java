package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;

@Autonomous(name = "test calib - ANTI SHAKE", group = "Autonomous")
public class testauto extends LinearOpMode {

    private VisionSubsystem visionSubsystem;
    private DriveSubsystem driveSubsystem;
    
    // Biến cho PD Control
    private double lastError = 0;
    private final ElapsedTime loopTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        WebcamName webcam = hardwareMap.get(WebcamName.class, Constants.webcamName);
        visionSubsystem = new VisionSubsystem(webcam, telemetry);
        driveSubsystem = new DriveSubsystem(hardwareMap);

        visionSubsystem.initialize();
        telemetry.addData("Status", "Ready - PD Control Enabled");
        telemetry.update();

        waitForStart();
        loopTimer.reset();

        while (opModeIsActive()) {
            visionSubsystem.update(); 
            
            if (visionSubsystem.hasTarget()) {
                double currentError = visionSubsystem.getBearingToGoalDeg();
                double deltaTime = loopTimer.seconds();
                loopTimer.reset();

                if (Math.abs(currentError) > Constants.visionAimTolerance) {
                    // P-Term: Lực tỉ lệ lỗi
                    double p = currentError * Constants.visionAimGain;
                    
                    // D-Term: Lực cản (damping) tỉ lệ với tốc độ thay đổi lỗi
                    // Giúp robot không vọt qua tâm
                    double d = ((currentError - lastError) / deltaTime) * Constants.visionAimD;
                    
                    double rotatePower = p + d;
                    
                    // Thêm Min Power
                    rotatePower += Math.signum(rotatePower) * Constants.visionAimMinPower;
                    
                    rotatePower = Range.clip(rotatePower, -Constants.visionAimMaxPower, Constants.visionAimMaxPower);
                    
                    driveSubsystem.setMotorPower(rotatePower, rotatePower, -rotatePower, -rotatePower);
                    lastError = currentError;
                    
                    telemetry.addData("PD Status", "P: %.3f, D: %.3f", p, d);
                } else {
                    driveSubsystem.setMotorPower(0, 0, 0, 0);
                    telemetry.addData("PD Status", "LOCKED");
                    lastError = 0;
                }
            } else {
                driveSubsystem.setMotorPower(0, 0, 0, 0);
                lastError = 0;
            }
            telemetry.update();
        }
        visionSubsystem.close();
    }
}
