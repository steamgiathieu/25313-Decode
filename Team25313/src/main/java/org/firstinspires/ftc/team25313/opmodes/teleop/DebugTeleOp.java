package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.Utility;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp(name = "Debug TeleOp", group = "TeleOp")
public class DebugTeleOp extends LinearOpMode {

    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;
    private VisionSubsystem vision;

    private VisionPortal visionPortal;
    private AprilTagProcessor tagProcessor;

    @Override
    public void runOpMode() {

        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap, intake);

        tagProcessor = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(tagProcessor)
                .setAutoStopLiveView(false)
                .build();

        vision = new VisionSubsystem(tagProcessor, VisionSubsystem.Alliance.blue);

        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.x) vision.setAlliance(VisionSubsystem.Alliance.blue);
            if (gamepad1.b) vision.setAlliance(VisionSubsystem.Alliance.red);

            telemetry.addLine("Alliance Select:");
            telemetry.addLine("X = Blue | B = Red");
            telemetry.addData("Current", vision.getAlliance());
            telemetry.update();
        }

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double forward = gamepad1.left_stick_y;
            double strafe  = -gamepad1.left_stick_x;
            double rotate  = -gamepad1.right_stick_x;

            driveSubsystem.driveRobotRelated(forward, strafe, rotate);

            vision.update();

            if (!outtake.isFeeding()) {
                if (gamepad1.right_bumper) {
                    intake.setManualCollect();
                    outtake.setPusherIntake();
                }
                else if (gamepad1.left_bumper) {
                    intake.setManualReverse();
                    outtake.setPusherPrep();
                }
                else {
                    intake.stop();
                    outtake.setPusherPrep();
                }
            }

            if (gamepad1.x) outtake.enable();
            if (gamepad1.b) outtake.disable();

            if (gamepad1.dpadUpWasPressed()) outtake.powerUp();
            if (gamepad1.dpadDownWasPressed()) outtake.powerDown();

            if (gamepad1.y) {
                outtake.startFeeding();
            } else if (outtake.isFeeding()) {
                outtake.stopFeeding();
            }

            outtake.update();
            intake.update();

            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
            Utility.teleOuttake(telemetry, outtake);

            telemetry.addData("Has Target", vision.hasTarget());

            if (vision.hasTarget()) {
                telemetry.addData("Distance (m)", "%.2f", vision.getDistanceToGoal());
                telemetry.addData("Yaw (deg)", "%.1f", vision.getYawToGoalDeg());
                telemetry.addData("Yaw Aligned", vision.isYawAligned() ? "YES" : "NO");
                telemetry.addData("Aim Accuracy (%)", "%.1f", vision.getAimAccuracyPercent());
                telemetry.addData("Suggested Shot", vision.getSuggestedShot());

                if (!vision.isYawAligned()) {
                    telemetry.addData(
                            "Driver Hint",
                            vision.getYawToGoalDeg() > 0 ? "Rotate RIGHT" : "Rotate LEFT"
                    );
                }
            } else {
                telemetry.addLine("No goal detected");
            }

            telemetry.update();
        }

        visionPortal.close();
    }
}
