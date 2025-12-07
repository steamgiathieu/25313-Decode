package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);

        while (opModeIsActive()) {
            double verticalRate = 0.5;
            double horizontalRate = 0.5;
            double rotateRate = 0.4;
            double forward = gamepad1.left_stick_y * verticalRate;
            double strafe = -gamepad1.left_stick_x * horizontalRate;
            double rotate = driveSubsystem.fixedRotate(-gamepad1.right_stick_x, rotateRate);
            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

            if (gamepad1.a) intake.collect();
            else if (gamepad1.y) intake.reverse();
            else intake.stop();

            // Regular telemetry (Driver Station) and Dashboard telemetry
            telemetry.addLine("=== Telemetry ===");
            telemetry.addData("Y", forward);
            telemetry.addData("X", strafe);
            telemetry.addData("Z", rotate);
            telemetry.addData("LF", driveSubsystem.frontLeftPower);
            telemetry.addData("RF", driveSubsystem.frontRightPower);
            telemetry.addData("LB", driveSubsystem.backLeftPower);
            telemetry.addData("BR", driveSubsystem.backRightPower);
        }
    }
}