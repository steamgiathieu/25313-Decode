//package org.firstinspires.ftc.team25313.opmodes.teleop;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import org.firstinspires.ftc.team25313.Utility;
//import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
//import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
//import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
//
//@TeleOp(name = "Main TeleOp", group = "TeleOp")
//public class MainTeleOp extends LinearOpMode {
//    private DriveSubsystem driveSubsystem;
//    private ArtifactCollector intake;
//    private ArtifactLauncher outtake;
//    @Override
//    public void runOpMode() {
//        driveSubsystem = new DriveSubsystem(hardwareMap);
//        intake = new ArtifactCollector(hardwareMap);
//        outtake = new ArtifactLauncher(hardwareMap, intake);
//
//        waitForStart();
//        if (isStopRequested()) return;
//
//        while (opModeIsActive()) {
//            double forward = gamepad1.left_stick_y;
//            double strafe = -gamepad1.left_stick_x;
//            double rotate = -gamepad1.right_stick_x;
//
//            driveSubsystem.driveRobotRelated(forward, strafe, rotate);
//
//            if (!outtake.isFeeding()) {
//                if (gamepad2.right_bumper) intake.setManualCollect();
//                else if (gamepad2.left_bumper) intake.setManualReverse();
//                else intake.stop();
//            }
//
//            if (gamepad2.x) outtake.enable();
//            if (gamepad2.b) outtake.disable();
//
//            if (gamepad2.dpad_up) outtake.powerUp();
//            if (gamepad2.dpad_down) outtake.powerDown();
//
//            if (gamepad2.y) {
//                outtake.startFeeding();
//            } else {
//                outtake.stopFeeding();
//            }
//
//            outtake.update();
//            intake.update();
//
//            telemetry.addLine("accelerada");
//            Utility.teleDrivePose(telemetry, forward, strafe, rotate);
//            Utility.teleOuttake(telemetry, outtake);
//            Utility.teleUpdate(telemetry);
//        }
//    }
//}