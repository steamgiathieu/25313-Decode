//main auto file to run all starting positions are called from specific builded auto mode
package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;

@Autonomous(name = "Main Auto", group = "Auto")
public abstract class MainAuto extends LinearOpMode {

    protected Constants.AllianceColor allianceColor;
    protected StartingPos startingPos;

    protected DriveSubsystem driveSubsystem;
//    protected Camera camera;

    protected abstract void initStartingCondition();

    @Override
    public void runOpMode() {
        initStartingCondition();

        // Khởi tạo đúng HardwareMap
        driveSubsystem = new DriveSubsystem(hardwareMap);
//        camera = new Camera(this);

        // Lấy pose xuất phát
//        Pose startPose = getStartPose(startingPos);
//        driveSubsystem.getDrive().setPoseEstimate(startPose);

        telemetry.addData("Alliance", allianceColor);
        telemetry.addData("Starting Pos", startingPos);
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        executeAutoRoutine();
    }

    private Pose getStartPose(StartingPos pos) {
        switch (pos) {
            case BIG_TRIANGLE_BLUE:  return new Pose(26, 128, Math.toRadians(315));
            case SMALL_TRIANGLE_BLUE:return new Pose(56, 8, Math.toRadians(90));
            case BIG_TRIANGLE_RED:   return new Pose(115, 128, Math.toRadians(225));
            case SMALL_TRIANGLE_RED: return new Pose(87.5, 8, Math.toRadians(90));
            default: return new Pose(0, 0, 0);
        }
    }

    private void executeAutoRoutine() {
        // TODO: viết các bước auto thật
        // · Detect artifact
        // · Move to position
        // · Launch balls
    }

    public enum StartingPos {
        BIG_TRIANGLE_BLUE,
        SMALL_TRIANGLE_BLUE,
        BIG_TRIANGLE_RED,
        SMALL_TRIANGLE_RED
    }
}
