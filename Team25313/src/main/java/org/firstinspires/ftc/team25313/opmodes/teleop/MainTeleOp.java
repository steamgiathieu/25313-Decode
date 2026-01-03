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
    private boolean lastLB2 = false;
    private boolean lastRB2 = false;
    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap);
        outtake.setIntakeCallbacks(
                () -> intake.collect(),
                () -> intake.stop()
        );
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

            if (gamepad2.x) outtake.enableLauncher();
            else if (gamepad2.b) outtake.disableLauncher();
            if (gamepad2.dpad_up) outtake.setBasePower();
            else if (gamepad2.dpad_down) outtake.setGoalPower();
            if (gamepad2.left_bumper && !lastLB2) outtake.toggleMode();
            lastLB2 = gamepad2.left_bumper;
            if (gamepad2.right_bumper && !lastRB2) outtake.shoot();
            lastRB2 = gamepad2.right_bumper;

            outtake.update();

            telemetry.addLine("accelerada");
            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleIntake(telemetry, intake.isRunning());
            Utility.teleOuttake(telemetry, outtake);
            Utility.teleUpdate(telemetry);
        }
    }
}