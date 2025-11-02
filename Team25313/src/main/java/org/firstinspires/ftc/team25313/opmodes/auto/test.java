package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.Drivetrain;
import pedropathing.localization.Pose;

@Autonomous(name = "Test Odometry Auto (Sim)", group = "Test")
public class test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);

        // Bật chế độ mô phỏng
        drivetrain.drive.setSimulationMode(true);

        drivetrain.setPose(new Pose(0, 0, 0));

        telemetry.addLine("Ready to start simulation");
        telemetry.update();

        waitForStart();

        drivetrain.driveToPose(new Pose(24, 0, Math.toRadians(90)), 0.5);

        while (opModeIsActive()) {
            drivetrain.update();
            Pose p = drivetrain.getPose();
            telemetry.addData("X", "%.2f", p.getX());
            telemetry.addData("Y", "%.2f", p.getY());
            telemetry.addData("Heading", "%.2f°", Math.toDegrees(p.getHeading()));
            telemetry.update();
        }
    }
}
