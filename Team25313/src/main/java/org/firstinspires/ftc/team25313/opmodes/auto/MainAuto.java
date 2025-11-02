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

        // --- Chạy auto chính ---
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
        // Ví dụ logic auto chính
        // 1. Phát hiện tag
        // 2. Di chuyển đến scoring zone
        // 3. Bắn hoặc thả vật thể
        // 4. Park
        // Tùy chỉnh theo PedroPathing
    }

    public enum StartingPos {
        BIG_TRIANGLE_BLUE, SMALL_TRIANGLE_BLUE, BIG_TRIANGLE_RED, SMALL_TRIANGLE_RED
    }
}
