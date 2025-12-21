package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptAprilTagMultiPortal;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.FTCObject;
import org.firstinspires.ftc.team25313.Utility;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.pid.HeadingPID;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.vision.GoalPose;
import org.firstinspires.ftc.team25313.subsystems.vision.ObeliskPattern;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.slf4j.helpers.Util;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;
//    private VisionSubsystem vision;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap);

        intake.stop();
        outtake.setLauncherOff();
//        vision = new VisionSubsystem(hardwareMap);
//        boolean isLockToGoal = false;
//        HeadingPID headingPID = new HeadingPID(0.02, 0, 0.02);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
//            vision.update();

//            if (gamepad1.a) {
//                isLockToGoal = true;
//            }
//            else if (gamepad1.b) {
//                isLockToGoal = false;
//            }
            double verticalRate = 1;
            double horizontalRate = 1;
            double rotateRate = 1;
            // Gamepad left_stick_y is typically negative when pushed forward.
            // Invert the Y axis so a forward push produces positive forward power.
            double forward = gamepad1.left_stick_y * verticalRate;
            double strafe = -gamepad1.left_stick_x * horizontalRate;
            double rotate = driveSubsystem.fixedRotate(-gamepad1.right_stick_x, rotateRate);

//            telemetry.addLine(Constants.botName);
//            telemetry.addLine("=== Data of Vision ===");
//            if (vision.hasTag()) {
//                AprilTagDetection det = vision.getCurrentTag();
//                telemetry.addData("TagID", det.id);
//            }

            // Tag Goal pose
//            GoalPose gp = vision.getGoalPose();
//            if (gp != null) {
//                telemetry.addData("tx", gp.tx);
//                telemetry.addData("ty", gp.ty);
//                telemetry.addData("tz", gp.tz);
//                telemetry.addData("distance cm", gp.distanceCm());
//                telemetry.addData("yaw", gp.yawDeg);
//                if (isLockToGoal) {
//                    double yawError = gp.yawDeg;
//                    rotate = headingPID.calculate(0, yawError);
//                    rotate = Math.max(-1, Math.min(1, rotate));
//                }
//            }

            // Obelisk motif
//            ObeliskPattern.Color[] motif = vision.getObeliskPattern();
//            if (motif != null) {
//                telemetry.addData("Motif", motif[0] + " - " + motif[1] + " - " + motif[2]);
//            }

            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

            if (gamepad2.y) intake.collect();
            else if (gamepad2.a) intake.stop();

            if (gamepad2.x) outtake.setLauncherReady();
            else if (gamepad2.dpad_up) outtake.setBaseLaunch();
            else if (gamepad2.dpad_down) outtake.setGoalLaunch();
            else if (gamepad2.b) outtake.setLauncherOff();

            outtake.update();

            // Regular telemetry (Driver Station) and Dashboard telemetry
            telemetry.addLine("accelerada");
            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleIntake(telemetry, intake.isRunning());
            Utility.teleOuttake(telemetry, outtake.isRunning(), outtake.getPower());
            Utility.teleUpdate(telemetry);
        }
    }
}