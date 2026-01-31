package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.team25313.Utility;
import org.firstinspires.ftc.team25313.pedroPathing.Constants;
import org.firstinspires.ftc.team25313.opmodes.auto.paths.*;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

import java.util.Set;

public abstract class MainAutoNear extends OpMode {
    protected ArtifactCollector intake;
    protected ArtifactLauncher outtake;

    protected Follower follower;
    protected AutoPaths paths;


    @SuppressWarnings("unused")
    protected enum AutoState {
        start,
        waitToSpinUp,
        runPath,
        waitAfterPath,
        waitToShoot,
        end
    }

    protected enum Alliance { blue, red }

    protected enum StartPosition { far, near }
    protected Alliance alliance;
    protected StartPosition startPosition;

    protected static class StartPose {

        public static Pose get(Alliance alliance, StartPosition pos) {

//            if (pos == StartPosition.far) {
//                switch (alliance) {
//                    case blue:
//                        return new Pose(
//                                56.0,
//                                8.0,
//                                Math.toRadians(90)
//                        );
//                    case red:
//                        return new Pose(
//                                88.0,
//                                8.0,
//                                Math.toRadians(90)
//                        );
//                }
//            }
            if (pos == StartPosition.near) {
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

    protected AutoState autoState = AutoState.start;

    protected int currentPathIndex = 1;
    protected static final int maxPath = 6;
    protected static final Set<Integer> shootPaths = Set.of(1, 3, 5);

    protected long waitStartTime;
    protected int shootingTimeMs = 2000;
    protected int spinUpDelayMs = 2000;

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
                outtake.setMinPow();
                outtake.enable();
                startWait();
                autoState = AutoState.waitToSpinUp;
                break;

            case waitToSpinUp:
                if (waitMillis(spinUpDelayMs)) {
                    follower.setMaxPower(0.8);
                    followCurrentPath();
                    autoState = AutoState.runPath;
                }
                break;

            case runPath:
                if (!follower.isBusy()) {
                    intake.setManualCollect();
                    if (shootPaths.contains(currentPathIndex)) {
                        startWait();
                        autoState = AutoState.waitAfterPath;
                    } else {
                        goToNextPath();
                    }
                }
                break;

            case waitAfterPath:
                outtake.startFeeding();
                startWait();
                autoState = AutoState.waitToShoot;
                break;

            case waitToShoot:
                if (waitMillis(shootingTimeMs)) {
                    outtake.stopFeeding();
                    goToNextPath();
                }
                break;

            case end:
                outtake.disable();
                break;
        }
        telemetry.addData("Autonomous STATE: ", autoState);
        Utility.teleOuttake(telemetry, outtake);
        telemetry.update();

    }

    protected void startWait() {
        waitStartTime = System.currentTimeMillis();
    }

    protected boolean waitMillis(long durationMs) {
        return System.currentTimeMillis() - waitStartTime >= durationMs;
    }

    protected abstract Alliance getAlliance();
    protected abstract StartPosition getStartPosition();

    protected void buildPaths() {
        if (alliance == Alliance.blue && startPosition == StartPosition.near) {
            paths = new NearBluePaths(follower);
        } else if (alliance == Alliance.red && startPosition == StartPosition.near) {
            paths = new NearRedPaths(follower);
        }
    }

    protected void followCurrentPath() {
        switch (currentPathIndex) {
            case 1:
                follower.followPath(paths.getPath1());
                break;
            case 2:
                follower.followPath(paths.getPath2());
                break;
            case 3:
                follower.followPath(paths.getPath3());
                break;
            case 4:
                follower.followPath(paths.getPath4());
                break;
            case 5:
                follower.followPath(paths.getPath5());
                break;
            case 6:
                follower.followPath(paths.getPath6());
                break;
            default:
                autoState = AutoState.end;
                break;
        }
    }

    protected void goToNextPath() {
        currentPathIndex++;

        if (currentPathIndex > maxPath) {
            autoState = AutoState.end;
            return;
        }

        follower.setMaxPower(0.8);
        followCurrentPath();
        autoState = AutoState.runPath;
    }
}