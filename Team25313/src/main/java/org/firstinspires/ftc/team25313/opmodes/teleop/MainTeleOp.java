package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.Utility;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.lift.HalfLift;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;
//    private VisionSubsystem visionSubsystem;
    private HalfLift lift;
//    private final ElapsedTime loopTimer = new ElapsedTime();
    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap, intake);
        lift = new HalfLift(hardwareMap);
//        WebcamName webcam = hardwareMap.get(WebcamName.class, Constants.webcamName);
//        visionSubsystem = new VisionSubsystem(webcam, telemetry);
//        driveSubsystem = new DriveSubsystem(hardwareMap);
//
//        visionSubsystem.initialize();
//        telemetry.addData("Status", "Ready - PD Control Enabled");
//        telemetry.update();
//
//        waitForStart();
//        loopTimer.reset();
//
//        // Scaling factors: map suggested cm/deg corrections into motor power [-maxPower, maxPower]
//        final double maxCorrectionPower = 0.6; // maximum motor power used for vision corrections
//        final double maxStrafeCm = 30.0; // must match VisionSubsystem.getTeleopCorrection clamps
//        final double maxForwardCm = 30.0;
//        final double maxRotateDeg = 15.0;

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

//            visionSubsystem.update();
//
//            VisionSubsystem.PositionCorrection pc = visionSubsystem.getTeleopCorrection();
//
//            if (gamepad1.right_bumper && pc.valid) {
//                double strafePower = Range.clip(pc.strafeCm / maxStrafeCm, -1.0, 1.0) * maxCorrectionPower;
//                double forwardPower = Range.clip(pc.forwardCm / maxForwardCm, -1.0, 1.0) * maxCorrectionPower;
//                double rotatePower = Range.clip(pc.rotateDeg / maxRotateDeg, -1.0, 1.0) * maxCorrectionPower;
//
//                // Drive robot-related (robot-centric): forward, strafe, rotate
//                driveSubsystem.driveRobotRelated(forwardPower, strafePower, rotatePower);
//            } else {
//                // No valid tag -> stop corrections
//                driveSubsystem.driveRobotRelated(0.0, 0.0, 0.0);
//            }

            telemetry.addLine(Constants.botName);
            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleOuttake(telemetry, outtake);
            Utility.teleUpdate(telemetry);
        }
    }
}