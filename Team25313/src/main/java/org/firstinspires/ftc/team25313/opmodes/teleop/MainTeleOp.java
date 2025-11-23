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

    // button edge detection
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
        // DRIVETRAIN CONTROL (Robot-Centric)
        // =============================================
        double forward = -gamepad1.left_stick_y;
        double strafe  = gamepad1.left_stick_x;
        double turn    = gamepad1.right_stick_x;

        // dùng hàm mới của drivetrain đơn giản
        driveSubsystem.drive(forward, strafe, turn);

        // =============================================
        // INTAKE CONTROL
        // =============================================
        if (gamepad1.right_bumper) {
            intake.collect();
        }
        else if (gamepad1.left_bumper) {
            intake.reverse();
        }
        else {
            intake.stop();
        }

        // =============================================
        // LAUNCHER CONTROL
        // =============================================

        if (gamepad2.a) launcher.startShooter();
        if (gamepad2.b) launcher.stopShooter();

        boolean feedPressed = gamepad2.x;
        if (feedPressed && !feedPrev) {
            launcher.shootSingle();
        }
        feedPrev = feedPressed;

        boolean dpadUp = gamepad2.dpad_up;
        if (dpadUp && !dpadUpPrev) {
            launcher.increasePowerLevel();
        }
        dpadUpPrev = dpadUp;

        // =============================================
        // TELEMETRY
        // =============================================
        telemetry.addLine("=== Robot Status ===");
        telemetry.addData("Shooter Level", launcher.getCurrentLevelIndex());
        telemetry.addData("Shooter Power", launcher.getCurrentPowerLevel());
        telemetry.addData("Intake Mode",
                gamepad2.right_bumper ? "COLLECT" :
                        gamepad2.left_bumper ? "REVERSE" : "STOP");
        telemetry.update();
    }
}
