package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.Utility;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.lift.HalfLift;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;
    private HalfLift lift;
    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap, intake);
        lift = new HalfLift(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double forward = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double rotate = -gamepad1.right_stick_x;

            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

            if (gamepad1.dpad_up) lift.lift();
            else if (gamepad1.dpad_down) lift.pull();
            else lift.stop();

            if (!outtake.isFeeding()) {
                if (gamepad2.right_bumper) intake.setManualCollect();
                else if (gamepad2.left_bumper) intake.setManualReverse();
                else intake.stop();
            }

            if (gamepad2.x) outtake.enable();
            if (gamepad2.b) outtake.disable();

            if (gamepad2.dpadUpWasPressed()) outtake.powerUp();
            if (gamepad2.dpadDownWasPressed()) outtake.powerDown();

            if (gamepad2.y) {
                outtake.startFeeding();
            } else {
                outtake.stopFeeding();
            }

            outtake.update();
            intake.update();

            telemetry.addLine(Constants.botName);
            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleOuttake(telemetry, outtake);
            Utility.teleUpdate(telemetry);
        }
    }
}