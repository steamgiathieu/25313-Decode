package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@TeleOp(name = "MainTeleOp", group = "TeleOp")
public class MainTeleOp extends OpMode {

    // Subsystems
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher launcher;

    // for button edge detection
    private boolean dpadUpPrev = false;
    private boolean feedPrev = false;

    @Override
    public void init() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        launcher = new ArtifactLauncher(hardwareMap);

        telemetry.addLine("Initialized MainTeleOp!");
    }

    @Override
    public void loop() {

        // =============================================
        // DRIVETRAIN CONTROL (ROBOT-CENTRIC)
        // =============================================
        double x = -gamepad1.left_stick_y;    // forward/back
        double y = gamepad1.left_stick_x;     // strafe
        double turn = gamepad1.right_stick_x; // rotate

        driveSubsystem.setPower(x, y, turn);
        driveSubsystem.update();

        // =============================================
        // INTAKE CONTROL
        // =============================================
        // intake on: right bumper
        if (gamepad1.right_bumper) {
            intake.collect();
        }
        // reverse intake: left bumper
        else if (gamepad1.left_bumper) {
            intake.reverse();
        }
        // stop intake
        else {
            intake.stop();
        }

        // =============================================
        // OUTTAKE CONTROL (Shooter)
        // =============================================

        // A = start shooter
        if (gamepad1.a) {
            launcher.startShooter();
        }

        // B = stop shooter
        if (gamepad1.b) {
            launcher.stopShooter();
        }

        // X = shoot 1 ball (servo feed)
        boolean feedPressed = gamepad1.x;
        if (feedPressed && !feedPrev) {
            launcher.shootSingle();
        }
        feedPrev = feedPressed;

        // Dpad Up = increase power level (with edge detection)
        boolean dpadUp = gamepad1.dpad_up;
        if (dpadUp && !dpadUpPrev) {
            launcher.increasePowerLevel();
        }
        dpadUpPrev = dpadUp;

        // =============================================
        // TELEMETRY
        // =============================================
        telemetry.addLine("=== Robot Status ===");
        telemetry.addData("Pose", driveSubsystem.getPose());
        telemetry.addData("Shooter Level", launcher.getCurrentLevelIndex());
        telemetry.addData("Shooter Power", launcher.getCurrentPowerLevel());
        telemetry.addData("Intake", gamepad1.right_bumper ? "COLLECT" :
                gamepad1.left_bumper ? "REVERSE" : "STOP");

        telemetry.update();
    }
}
