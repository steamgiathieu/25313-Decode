package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.Utility;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;
    boolean prevLaunch = false;
    boolean prevMode = false;
    boolean prevUp = false;
    boolean prevDown = false;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double forward = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double rotate = -gamepad1.right_stick_x;

            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

            if (gamepad2.y) intake.collect();
            else if (gamepad2.a) intake.reverse();
            else intake.stop();

            // where is outtake hah
            telemetry.addLine("accelerada");
            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleIntake(telemetry, intake.isRunning());
            Utility.teleOuttake(telemetry, outtake);
            Utility.teleUpdate(telemetry);
        }
    }
}