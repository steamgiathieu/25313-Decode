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

    protected enum AutoStateForFar {
        start,
        path1, path2, path3, wait3, path4, path5, path6, wait6, path7,
        end;
    }

    protected enum AutoStateForNear {
        path1, wait1, path2, path3, path4, wait4, path5, path6, path7, wait7, path8, end;
    }

    protected AutoStateForFar farState;
    protected AutoStateForNear nearState;

    protected enum Alliance {blue, red}

    protected enum StartPosition {far, near}

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
            } else if (pos == StartPosition.near) {
                switch (alliance) {
                    case blue:
                        return new Pose(
                                21.0,
                                123.0,
                                Math.toRadians(145)
                        );
                    case red:
                        return new Pose(
                                123.0,
                                123.0,
                                Math.toRadians(35)
                        );
                }
            }
            return new Pose(0, 0, 0);
        }
    }

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
        if (startPosition == StartPosition.far) {
            farState = AutoStateForFar.start;
        } else {
            nearState = AutoStateForNear.path1;
        }
    }

    @Override
    public void loop() {
        follower.update();

        intake.update();
        outtake.update();

        if (startPosition == StartPosition.far) {
            outtake.setMaxPow();
            runFarAuto();
        } else if (startPosition == StartPosition.near) {
            outtake.powerDown();
            runNearAuto();
        }
    }

    protected void runFarAuto() {
        switch (farState) {
            case start:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    farState = AutoStateForFar.path1;
                    follower.followPath(paths.getPath1());
                }
                break;
            case path1:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath2());
                    farState = AutoStateForFar.path2;
                }
                break;
            case path2:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath3());
                    farState = AutoStateForFar.path3;
                }
                break;
            case path3:
                if (!follower.isBusy()) {
                    farState = AutoStateForFar.wait3;
                }
                break;
            case wait3:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    farState = AutoStateForFar.path4;

                    follower.followPath(paths.getPath4());
                }
                break;
            case path4:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath5());
                    farState = AutoStateForFar.path5;
                }
                break;
            case path5:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath6());
                    farState = AutoStateForFar.path6;
                }
                break;
            case path6:
                if (!follower.isBusy()) {
                    farState = AutoStateForFar.wait6;
                }
                break;
            case wait6:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    farState = AutoStateForFar.path7;

                    follower.followPath(paths.getPath7());
                }
                break;
            case path7:
                if (!follower.isBusy()) {
                    farState = AutoStateForFar.end;
                    stopAll();
                }
                break;
            case end:
                break;
        }
    }

    protected void runNearAuto() {
        switch (nearState) {
            case path1:
                if (!follower.isBusy()) {
                    nearState = AutoStateForNear.wait1;
                }
                break;
            case wait1:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    nearState = AutoStateForNear.path2;
                    follower.followPath(paths.getPath2());
                }
                break;
            case path2:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath3());
                    nearState = AutoStateForNear.path3;
                }
                break;
            case path3:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath4());
                    nearState = AutoStateForNear.path4;
                }
                break;
            case path4:
                if (!follower.isBusy()) {
                    nearState = AutoStateForNear.wait4;
                }
                break;
            case wait4:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    nearState = AutoStateForNear.path5;
                    follower.followPath(paths.getPath5());
                }
                break;
            case path5:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath6());
                    nearState = AutoStateForNear.path6;
                }
                break;
            case path6:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath7());
                    nearState = AutoStateForNear.path7;
                }
                break;
            case path7:
                if (!follower.isBusy()) {
                    nearState = AutoStateForNear.wait7;
                }
                break;
            case wait7:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    nearState = AutoStateForNear.path8;
                    follower.followPath(paths.getPath8());
                }
                break;
            case path8:
                if (!follower.isBusy()) {
                    farState = AutoStateForFar.end;
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
        if (alliance == Alliance.blue && startPosition == StartPosition.far) {
            paths = new FarBluePaths(follower);
        } else if (alliance == Alliance.red && startPosition == StartPosition.far) {
            paths = new FarRedPaths(follower);
        } else if (alliance == Alliance.blue && startPosition == StartPosition.near) {
            paths = new NearBluePaths(follower);
        } else if (alliance == Alliance.red && startPosition == StartPosition.near) {
            paths = new NearRedPaths(follower);
        }
    }
}
