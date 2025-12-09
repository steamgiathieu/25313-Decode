package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.vision.GoalPose;
import org.firstinspires.ftc.team25313.subsystems.vision.ObeliskPattern;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private VisionSubsystem vision;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        vision = new VisionSubsystem(hardwareMap);

        while (opModeIsActive()) {
            vision.update();

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
            }

            // Obelisk motif
            ObeliskPattern.Color[] motif = vision.getObeliskPattern();
            if (motif != null) {
                telemetry.addData("Motif", motif[0] + " - " + motif[1] + " - " + motif[2]);
            }

            double verticalRate = 0.5;
            double horizontalRate = 0.5;
            double rotateRate = 0.4;
            double forward = gamepad1.left_stick_y * verticalRate;
            double strafe = -gamepad1.left_stick_x * horizontalRate;
            double rotate = driveSubsystem.fixedRotate(-gamepad1.right_stick_x, rotateRate);
            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

            if (gamepad1.a) intake.collect();
            else if (gamepad1.y) intake.reverse();
            else intake.stop();

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