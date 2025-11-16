//main auto file to run all starting positions are called from specific builded auto mode
package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.team25313.subsystems.vision.Camera;
import org.firstinspires.ftc.team25313.subsystems.vision.DetectArtifactProcessor;

import pedroPathing.localization.Pose2d;

@Autonomous(name = "Main Auto", group = "Auto")
public abstract class MainAuto extends LinearOpMode {
    protected Constants.AllianceColor allianceColor;
    protected StartingPos startingPos;
    protected Drivetrain drivetrain;
    protected Camera camera;
    protected DetectArtifactProcessor vision;

    protected abstract void initStartingCondition();

    @Override
    public void runOpMode() {
        //initialing
        initStartingCondition();

        drivetrain = new Drivetrain(this);
        camera = new Camera(this);
        vision = new DetectArtifactProcessor(this);

        Pose2d startPose = getStartPose(startingPos);
        drivetrain.setPose(startPose);

        telemetry.addData("Alliance", allianceColor);
        telemetry.addData("Starting Pos", startingPos);
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        //run things we should do
        executeAutoRoutine();
    }

    private Pose2d getStartPose(StartingPos pos) {
        switch (pos) {
            case BIG_TRIANGLE_BLUE:  return new Pose2d(10, 60, Math.toRadians(270));
            case SMALL_TRIANGLE_BLUE:return new Pose2d(12, 36, Math.toRadians(270));
            case BIG_TRIANGLE_RED:   return new Pose2d(-10, 60, Math.toRadians(90));
            case SMALL_TRIANGLE_RED: return new Pose2d(-12, 36, Math.toRadians(90));
            default: return new Pose2d(0, 0, 0);
        }
    }

    private void executeAutoRoutine() {
        // what we gonna complete:
        // - tag detection
        // - running to launching zone
        // - launcing
    }

    public enum StartingPos {
        BIG_TRIANGLE_BLUE, SMALL_TRIANGLE_BLUE, BIG_TRIANGLE_RED, SMALL_TRIANGLE_RED
    }
}
