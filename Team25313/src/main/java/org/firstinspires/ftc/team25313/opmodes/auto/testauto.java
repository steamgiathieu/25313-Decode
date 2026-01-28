package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem.PositionCorrection;

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

        // Scaling factors: map suggested cm/deg corrections into motor power [-maxPower, maxPower]
        final double maxCorrectionPower = 0.6; // maximum motor power used for vision corrections
        final double maxStrafeCm = 30.0; // must match VisionSubsystem.getTeleopCorrection clamps
        final double maxForwardCm = 30.0;
        final double maxRotateDeg = 15.0;

        while (opModeIsActive()) {
            visionSubsystem.update();

            PositionCorrection pc = visionSubsystem.getTeleopCorrection();
            if (pc.valid) {
                // Convert suggested distances/angles to normalized motor powers
                double strafePower = Range.clip(pc.strafeCm / maxStrafeCm, -1.0, 1.0) * maxCorrectionPower;
                double forwardPower = Range.clip(pc.forwardCm / maxForwardCm, -1.0, 1.0) * maxCorrectionPower;
                double rotatePower = Range.clip(pc.rotateDeg / maxRotateDeg, -1.0, 1.0) * maxCorrectionPower;

                // Drive robot-related (robot-centric): forward, strafe, rotate
                driveSubsystem.driveRobotRelated(forwardPower, strafePower, rotatePower);

                telemetry.addData("VisionTag", "id=%d dist=%.1fcm", pc.tagId, pc.distanceCm);
                telemetry.addData("Corr", "F=%.2f S=%.2f R=%.2f", forwardPower, strafePower, rotatePower);
            } else {
                // No valid tag -> stop corrections
                driveSubsystem.driveRobotRelated(0.0, 0.0, 0.0);
            }

            telemetry.update();
        }
        visionSubsystem.close();
    }
}
