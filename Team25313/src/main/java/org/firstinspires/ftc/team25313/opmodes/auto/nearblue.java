package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@Autonomous(name = "near blue", group = "sosauto")
public class nearblue extends LinearOpMode {
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


        driveSubsystem.driveRobotRelated(0.5, 0, 0);
        sleep(800);
        driveSubsystem.driveRobotRelated(0, 0, 0);
        outtake.enable();
        outtake.powerDown();

        ElapsedTime spinUpTimer = new ElapsedTime();
        while (opModeIsActive() && spinUpTimer.milliseconds() < 3000) {
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

        driveSubsystem.driveRobotRelated(0, 0.5, 0);
        sleep(500);
        driveSubsystem.driveRobotRelated(0, 0, 0);
    }
}