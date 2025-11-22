//package org.firstinspires.ftc.team25313.opmodes.auto;
//
//import com.pedropathing.geometry.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
//
//@Autonomous(name = "Test Odometry Auto (Sim)", group = "Test")
//public class test extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        DriveSubsystem driveSubsystem = new DriveSubsystem(hardwareMap);
//
//        // Bật chế độ mô phỏng
//        driveSubsystem.drive.setSimulationMode(true);
//
//        driveSubsystem.setPose(new Pose(0, 0, 0));
//
//        telemetry.addLine("Ready to start simulation");
//        telemetry.update();
//
//        waitForStart();
//
//        driveSubsystem.driveToPose(new Pose(24, 0, Math.toRadians(90)), 0.5);
//
//        while (opModeIsActive()) {
//            driveSubsystem.update();
//            Pose p = driveSubsystem.getPose();
//            telemetry.addData("X", "%.2f", p.getX());
//            telemetry.addData("Y", "%.2f", p.getY());
//            telemetry.addData("Heading", "%.2f°", Math.toDegrees(p.getHeading()));
//            telemetry.update();
//        }
//    }
//}
