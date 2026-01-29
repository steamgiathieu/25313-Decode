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


    @SuppressWarnings("unused")
    protected enum AutoState {
        start, waitToSpinUp,
        path1, wait1, waitToShoot, path2, path3, wait3,
        path4, path5, wait5, path6, path7, wait7,
        path8, end
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
                                Math.toRadians(90)
                        );
                    case red:
                        return new Pose(
                                88.0,
                                8.0,
                                Math.toRadians(90)
                        );
                }
            }
            else if (pos == StartPosition.near) {
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
    protected int shootingTime = 1800;
    protected int shootingCounter = 0;
    protected long waitStartTime;
    // When we perform a timed shooting window we set this so the generic waitToShoot
    // state knows which AutoState to go to after the window completes.
    protected AutoState postShootState = null;
    // true while a shooting window has been started and we are waiting inside waitToShoot
    protected boolean shootingWindowActive = false;

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
        autoState = AutoState.path1;
    }

    @Override
    public void loop() {
        follower.update();
        intake.update();
        outtake.update();

        switch (autoState) {
            case start:
                if (startPosition == StartPosition.far) outtake.setMaxPow();
                else if (startPosition == StartPosition.near) outtake.setMinPow();
                outtake.enable();
                autoState = AutoState.waitToSpinUp;
                intake.stop();
                break;
            case waitToSpinUp:
                // start the first path once spin-up waiting is done
                autoState = AutoState.path1;
                follower.setMaxPower(0.6);
                follower.followPath(paths.getPath1());
                break;
            case path1:
                if (!follower.isBusy()) {
                    // finished path1 -> prepare for shooting window (wait1)
                    autoState = AutoState.wait1;
                }
                break;
            case wait1:
                // When the shooter is spun up and ready, start the feeder and a timer
                if (outtake.isReadyToShoot() && !shootingWindowActive) {
                    startShootingWindow(AutoState.path2);
                }
                break;
            case waitToShoot:
                if (waitMillis(shootingTime)) {
                    outtake.stopFeeding();
                    shootingWindowActive = false;

                    autoState = postShootState;
                    postShootState = null;

                    follower.setMaxPower(0.6);
                    switch (autoState) {
                        case path2: follower.followPath(paths.getPath2()); break;
                        case path4: follower.followPath(paths.getPath4()); break;
                        case path6: follower.followPath(paths.getPath6()); break;
                        case path8: follower.followPath(paths.getPath8()); break;
                    }
                }
                break;
            case path2:
                if (!follower.isBusy()) {
                    // finished path2 -> immediately start path3
                    autoState = AutoState.path3;
                    follower.setMaxPower(0.6);
                    follower.followPath(paths.getPath3());
                }
                break;
            case path3:
                if (!follower.isBusy()) {
                    // finished path3 -> prepare for shooting window (wait3)
                    autoState = AutoState.wait3;
                }
                break;
            case wait3:
                if (outtake.isReadyToShoot() && !shootingWindowActive) {
                    startShootingWindow(AutoState.path4);
                }
                break;
            case path4:
                if (!follower.isBusy()) {
                    // finished path4 -> start path5
                    autoState = AutoState.path5;
                    follower.setMaxPower(0.6);
                    follower.followPath(paths.getPath5());
                }
                break;
            case path5:
                if (!follower.isBusy()) {
                    // finished path5 -> prepare for shooting window (wait5)
                    autoState = AutoState.wait5;
                }
                break;
            case wait5:
                if (outtake.isReadyToShoot() && !shootingWindowActive) {
                    startShootingWindow(AutoState.path6);
                }
                break;
            case path6:
                if (!follower.isBusy()) {
                    // finished path6 -> start path7
                    autoState = AutoState.path7;
                    follower.setMaxPower(0.6);
                    follower.followPath(paths.getPath7());
                }
                break;
            case path7:
                if (!follower.isBusy()) {
                    // finished path7 -> prepare for shooting window (wait7)
                    autoState = AutoState.wait7;
                }
                break;
            case wait7:
                if (outtake.isReadyToShoot() && !shootingWindowActive) {
                    startShootingWindow(AutoState.path8);
                }
                break;
            case path8:
                if (!follower.isBusy()) {
                    // final path finished -> end
                    autoState = AutoState.end;
                }
                break;
            case end:
                stopAll();
                break;
        }
        telemetry.addData("Autonomous STATE: ", autoState);
        telemetry.addData("Shooting counter: ", "%d", shootingCounter);
        telemetry.update();

    }

    protected void startWait() {
        waitStartTime = System.currentTimeMillis();
    }

    protected boolean waitMillis(long durationMs) {
        return System.currentTimeMillis() - waitStartTime >= durationMs;
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

    protected void startShootingWindow(AutoState next) {
        startWait();
        outtake.startFeeding();
        postShootState = next;
        shootingWindowActive = true;
        autoState = AutoState.waitToShoot;
    }
}
