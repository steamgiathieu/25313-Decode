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
    boolean burstPrev = false;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap);

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
            double forward = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double rotate = -gamepad1.right_stick_x;

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
            else if (gamepad2.a) intake.reverse();
            else intake.stop();

            if (gamepad2.x) outtake.setLauncherReady();
            else if (gamepad2.dpad_up) outtake.setBaseLaunch();
            else if (gamepad2.dpad_down) outtake.setGoalLaunch();
            else if (gamepad2.b) outtake.setLauncherOff();
            boolean isBursting = gamepad2.left_bumper;
            if (isBursting && !burstPrev) {
                outtake.toggleMode();
            }
            burstPrev = isBursting;
            if (gamepad2.right_bumper) {
                outtake.shoot();
            }
            outtake.update();

            // Regular telemetry (Driver Station) and Dashboard telemetry
            telemetry.addLine("accelerada");
            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleIntake(telemetry, intake.isRunning());
            Utility.teleOuttake(telemetry, outtake);
            Utility.teleUpdate(telemetry);
        }
    }
}