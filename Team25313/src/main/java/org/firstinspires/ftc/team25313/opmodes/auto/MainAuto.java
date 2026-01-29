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
        start, waitToSpinUp, waitToStable,
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
    protected int shootingTime = 1800, waitingTimeForStable = 800;
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
                    // finished path1 -> enter wait1 so the existing wait-to-shoot logic can run
                    autoState = AutoState.wait1;
                    startWait();
                }
                break;
            case wait1:
                // When the shooter is spun up and ready, start the feeder and a timer
                if (outtake.isReadyToShoot() && !shootingWindowActive) {
                    startShootingWindow(AutoState.path2);
                }
                break;

            case waitToStable:
                if(waitMillis(waitingTimeForStable) && outtake.isReadyToShoot()){
                    autoState = AutoState.waitToShoot;
                    outtake.startFeeding();
                    startWait(); //start wait for shooting
                }
                break;

            case waitToShoot:
                outtake.startFeeding();
                if(waitMillis(shootingTime)){
                    outtake.stopFeeding();
                    // If a postShootState was set by startShootingWindow, go to that state and start its path.
                    if (postShootState != null) {
                        AutoState next = postShootState;
                        postShootState = null;
                        shootingWindowActive = false;

                        switch (next) {
                            case path2:
                                follower.setMaxPower(0.8);
                                follower.followPath(paths.getPath2());
                                autoState = AutoState.path2;
                                break;
                            case path4:
                                follower.setMaxPower(0.8);
                                follower.followPath(paths.getPath4());
                                autoState = AutoState.path4;
                                break;
                            case path6:
                                follower.setMaxPower(0.8);
                                follower.followPath(paths.getPath6());
                                autoState = AutoState.path6;
                                break;
                            case path8:
                                follower.setMaxPower(0.8);
                                follower.followPath(paths.getPath8());
                                autoState = AutoState.path8;
                                break;
                            default:
                                // If next isn't a driving state, just set it
                                autoState = next;
                                break;
                        }
                    } else {
                        // legacy shootingCounter-driven behavior
                        if(shootingCounter == 0) {
                            follower.setMaxPower(0.8);
                            follower.followPath(paths.getPath2());
                            autoState = AutoState.path2;
                        } else if (shootingCounter == 1) {
                            follower.setMaxPower(0.8);
                            autoState = AutoState.path4;
                            follower.followPath(paths.getPath4());
                        }
                        else if (shootingCounter == 2) {
                            autoState = AutoState.path6;
                            follower.setMaxPower(0.8);
                            follower.followPath(paths.getPath6());
                        }
                        shootingCounter = Math.min(shootingCounter + 1, 4);
                        shootingWindowActive = false;
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
                    // finished path3 -> enter wait3 so the existing wait-to-shoot logic can run
                    autoState = AutoState.wait3;
                    startWait();
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
                    // finished path5 -> enter wait5 so the existing wait-to-shoot logic can run
                    autoState = AutoState.wait5;
                    startWait();
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
                    // finished path7 -> enter wait7 so the existing wait-to-shoot logic can run
                    autoState = AutoState.wait7;
                    startWait();
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
