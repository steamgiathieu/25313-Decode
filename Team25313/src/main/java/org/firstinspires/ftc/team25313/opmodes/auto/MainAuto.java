package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.team25313.pedroPathing.Constants;
import org.firstinspires.ftc.team25313.opmodes.auto.paths.*;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

public abstract class MainAuto extends OpMode {
    protected ArtifactCollector intake;
    protected ArtifactLauncher outtake;

    protected Follower follower;
    protected AutoPaths paths;

    protected enum AutoState {
        start,
        path1, path2, path3, wait3, path4, path5, path6, wait6, path7,
        end;
    }

    protected enum Alliance { blue, red }

    protected enum StartPosition { far, near }
    protected Alliance alliance;
    protected StartPosition startPosition;

    protected static class StartPose {

        public static Pose get(Alliance alliance, StartPosition pos) {

            if (pos == StartPosition.far) {
                switch (alliance) {
                    case blue:
                        return new Pose(
                                56.0,
                                8.0,
                                Math.toRadians(110)
                        );
                    case red:
                        return new Pose(
                                88.0,
                                8.0,
                                Math.toRadians(70)
                        );
                }
            }
            return new Pose(0, 0, 0);
        }
    }

    protected AutoState autoState = AutoState.start;
    protected long postShotStartTime = 0;
    protected long waitStartTime;

    @Override
    public void init() {
        alliance = getAlliance();
        startPosition = getStartPosition();

        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap, intake);

        follower = Constants.createFollower(hardwareMap);

        Pose startPose = StartPose.get(alliance, startPosition);
        follower.setStartingPose(startPose);

        buildPaths();

        intake.setOuttakeFeed();
        outtake.enable();
        outtake.stopFeeding();

        startWait();
        autoState = AutoState.start;
    }

    @Override
    public void loop() {
        follower.update();

        intake.update();
        outtake.update();

        switch (autoState) {
            case start:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    autoState = AutoState.path1;

                    follower.followPath(paths.getPath1());
                }
                break;
            case path1:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath2());
                    autoState = AutoState.path2;
                }
                break;
            case path2:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath3());
                    autoState = AutoState.path3;
                }
                break;
            case path3:
                if (!follower.isBusy()) {
                    autoState = AutoState.wait3;
                }
                break;
            case wait3:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    autoState = AutoState.path4;

                    follower.followPath(paths.getPath4());
                }
                break;
            case path4:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath5());
                    autoState = AutoState.path5;
                }
                break;
            case path5:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath6());
                    autoState = AutoState.path6;
                }
                break;
            case path6:
                if (!follower.isBusy()) {
                    autoState = AutoState.wait6;
                }
                break;
            case wait6:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    autoState = AutoState.path7;

                    follower.followPath(paths.getPath7());
                }
                break;
            case path7:
                if (!follower.isBusy()) {
                    autoState = AutoState.end;
                    stopAll();
                }
                break;
            case end:
                break;
        }
    }

    protected void startWait() {
        waitStartTime = System.currentTimeMillis();
    }

    protected void startPostShotWait() {
        postShotStartTime = System.currentTimeMillis();
    }

    protected void stopAll() {
        intake.stop();
        outtake.disable();
    }

    protected abstract Alliance getAlliance();
    protected abstract StartPosition getStartPosition();

    protected void buildPaths() {
        if (alliance == Alliance.blue) {
            paths = new FarBluePaths(follower);
        } else {
            paths = new FarRedPaths(follower);
        }
    }
}
