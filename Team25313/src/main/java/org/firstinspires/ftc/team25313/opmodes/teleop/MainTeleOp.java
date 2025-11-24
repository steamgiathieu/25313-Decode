package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {

    // Subsystems
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;

    // button edge detection
    private boolean feedPrev = false;
    private boolean UpPrev = false;
    private boolean DownPrev = false;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap);

        telemetry.addLine("Initialized MainTeleOp!");
        waitForStart();

        // =============================================
        // DRIVETRAIN CONTROL (Robot-Centric)
        // =============================================
        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            // dùng hàm mới của drivetrain đơn giản
            driveSubsystem.drive(forward, strafe, turn);

            // =============================================
            // INTAKE CONTROL
            // =============================================
            if (gamepad1.right_bumper) {
                intake.collect();
            } else if (gamepad1.left_bumper) {
                intake.reverse();
            } else {
                intake.stop();
            }

            // =============================================
            // LAUNCHER CONTROL
            // =============================================

            if (gamepad2.a) outtake.startShooter();
            if (gamepad2.b) outtake.stopShooter();

            boolean feedPressed = gamepad2.x;
            if (feedPressed && !feedPrev) {
                outtake.shootSingle();
            }
            feedPrev = feedPressed;

            boolean Up = gamepad2.dpad_up;
            boolean Down = gamepad2.dpad_down;
            if (Up && !UpPrev) {
                outtake.increasePowerLevel();
            }
            else if (Down && !DownPrev) {
                outtake.decreasePowerLevel();
            }

            UpPrev = Up;
            DownPrev = Down;

            // =============================================
            // TELEMETRY
            // =============================================
            telemetry.addLine("=== Robot Status ===");
            telemetry.addData("Shooter Level", outtake.getCurrentLevelIndex());
            telemetry.addData("Shooter Power", outtake.getCurrentPowerLevel());
            telemetry.addData("Intake Mode",
                    gamepad2.right_bumper ? "COLLECT" :
                            gamepad2.left_bumper ? "REVERSE" : "STOP");
            telemetry.update();
        }
    }
}
