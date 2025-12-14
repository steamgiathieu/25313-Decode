package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.FTCObject;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
//import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.pid.HeadingPID;
import org.firstinspires.ftc.team25313.subsystems.vision.GoalPose;
import org.firstinspires.ftc.team25313.subsystems.vision.ObeliskPattern;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
//    private ArtifactCollector intake;
    private VisionSubsystem vision;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
//        intake = new ArtifactCollector(hardwareMap);
        vision = new VisionSubsystem(hardwareMap);
        boolean isLockToGoal = false;
        HeadingPID headingPID = new HeadingPID(0.02, 0, 0.02);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            vision.update();

            if (gamepad1.a) {
                isLockToGoal = true;
            }
            else if (gamepad1.b) {
                isLockToGoal = false;
            }
            double verticalRate = 1;
            double horizontalRate = 1;
            double rotateRate = 1;
            double forward = gamepad1.left_stick_y * verticalRate;
            double strafe = -gamepad1.left_stick_x * horizontalRate;
            double rotate = driveSubsystem.fixedRotate(-gamepad1.right_stick_x, rotateRate);

            telemetry.addLine(Constants.botName);
            telemetry.addLine("=== Data of Vision ===");
            if (vision.hasTag()) {
                AprilTagDetection det = vision.getCurrentTag();
                telemetry.addData("TagID", det.id);
            }

            // Tag Goal pose
            GoalPose gp = vision.getGoalPose();
            if (gp != null) {
                telemetry.addData("tx", gp.tx);
                telemetry.addData("ty", gp.ty);
                telemetry.addData("tz", gp.tz);
                telemetry.addData("distance cm", gp.distanceCm());
                telemetry.addData("yaw", gp.yawDeg);
                if (isLockToGoal) {
                    double yawError = gp.yawDeg;
                    rotate = headingPID.calculate(0, yawError);
                    rotate = Math.max(-1, Math.min(1, rotate));
                }
            }

            // Obelisk motif
            ObeliskPattern.Color[] motif = vision.getObeliskPattern();
            if (motif != null) {
                telemetry.addData("Motif", motif[0] + " - " + motif[1] + " - " + motif[2]);
            }

            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

//            if (gamepad2.a) intake.collect();
//            else if (gamepad2.y) intake.reverse();
//            else intake.stop();

            // Regular telemetry (Driver Station) and Dashboard telemetry
            telemetry.addLine("=== Data of Drivetrain ===");
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