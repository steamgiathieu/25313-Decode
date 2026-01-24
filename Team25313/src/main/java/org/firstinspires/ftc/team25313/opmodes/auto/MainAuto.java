package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.team25313.pedroPathing.Constants;
import org.firstinspires.ftc.team25313.opmodes.auto.paths.*;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

@Disabled

public abstract class MainAuto extends OpMode {
    protected ArtifactCollector intake;
    protected ArtifactLauncher outtake;

    protected Follower follower;
    protected AutoPaths paths;

    protected enum AutoState {
        startShoot,
        path1, path2, path3, path4,
        wait4,
        path5, path6, path7, path8,
        wait8,
        path9,
        end
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
                                54.0,
                                10.0,
                                Math.toRadians(105)
                        );
                    case red:
                        return new Pose(
                                90.0,
                                10.0,
                                Math.toRadians(75)
                        );
                }
            }
            return new Pose(0, 0, 0);
        }
    }

    protected AutoState autoState = AutoState.startShoot;
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

        intake.setManualCollect();
        outtake.enable();
        outtake.stopFeeding();

        startWait();
        autoState = AutoState.startShoot;
    }

    @Override
    public void loop() {
        follower.update();

        intake.update();
        outtake.update();

        switch (autoState) {
            case startShoot:
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
                    follower.followPath(paths.getPath4());
                    autoState = AutoState.path4;
                }
                break;
            case path4:
                if (!follower.isBusy()) {
                    autoState = AutoState.wait4;
                }
                break;
            case wait4:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    autoState = AutoState.path5;

                    follower.followPath(paths.getPath5());
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
                    follower.followPath(paths.getPath7());
                    autoState = AutoState.path7;
                }
                break;
            case path7:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath8());
                    autoState = AutoState.path8;
                }
                break;
            case path8:
                if (!follower.isBusy()) {
                    autoState = AutoState.wait8;
                }
                break;
            case wait8:
                if (outtake.isReadyToShoot()) {
                    outtake.startFeeding();
                    startPostShotWait();
                    autoState = AutoState.path9;

                    follower.followPath(paths.getPath9());
                }
                break;
            case path9:
                if (!follower.isBusy()) {
                    autoState = AutoState.end;
                    stopAll();
                }
                break;
            case end:
                break;
        }
    }

    protected Pose getStartingPose() {
        return new Pose(72, 8, Math.toRadians(90));
    }

    protected void startWait() {
        waitStartTime = System.currentTimeMillis();
    }

    protected boolean waitPassed() {
        return System.currentTimeMillis() - waitStartTime >= 4500;
    }

    protected void startPostShotWait() {
        postShotStartTime = System.currentTimeMillis();
    }

    protected boolean postShotWaitPassed() {
        return System.currentTimeMillis() - postShotStartTime >= 2000;
    }

    protected void shoot() {
        // đẩy servo bắn
    }

    protected void stopAll() {
        // stop motor/servo
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
