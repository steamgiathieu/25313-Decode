package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@Autonomous(name = "3+3 sos far", group = "sosauto")
public class sos1 extends LinearOpMode {

    private DriveSubsystem driveSubsystem;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;

    @Override
    public void runOpMode() {

        driveSubsystem = new DriveSubsystem(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap, intake);

        waitForStart();
        if (isStopRequested()) return;

        outtake.enable();
        outtake.powerUp();

        while (opModeIsActive()) {
            outtake.update();
            idle();
        }

        intake.setOuttakeFeed();
        outtake.startFeeding();

        ElapsedTime shootTimer = new ElapsedTime();
        while (opModeIsActive() && shootTimer.milliseconds() < 2000) {
            outtake.update();
            intake.update();
            idle();
        }

        outtake.disable();
        intake.stop();

        driveSubsystem.driveRobotRelated(0.5, 0, 0);
        sleep(500);
        driveSubsystem.driveRobotRelated(0, 0, 0);
    }
}