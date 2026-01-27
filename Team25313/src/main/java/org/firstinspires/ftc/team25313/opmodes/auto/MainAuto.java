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
        waitToSpinUp, waitToShoot,
        path1, path2, path3, path4, path5, path6, path7, path8, path9,
        intake1, intake2, intake3, intake4,
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
            return new Pose(0, 0, 0);
        }
    }

    protected AutoState autoState = AutoState.start;
    protected int shootingTime = 2000;
    protected int shootingCounter = 0;
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
                outtake.setPowerLevel(ArtifactLauncher.PowerLevel.far);
                outtake.enable();
                autoState = AutoState.waitToSpinUp;
                break;
            case waitToSpinUp:
                if (outtake.isReadyToShoot()){
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
            case path5:
            case path8:
                if (!follower.isBusy()) {
                    autoState = AutoState.waitToShoot;
                    startWait();
                }
                break;
            case waitToShoot:
                if(!waitMillis(shootingTime)) outtake.startFeeding();
                else {
                    if(shootingCounter == 0) {
                        follower.followPath(paths.getPath3());
                        autoState = AutoState.path3;
                    } else if (shootingCounter == 1) {
                        follower.followPath(paths.getPath6());
                        autoState = AutoState.path6;
                    }
                    else if (shootingCounter == 2) {
                        follower.followPath(paths.getPath9());
                        autoState = AutoState.path9;
                    }
                    shootingCounter ++;
                }
                break;
            case path3:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath4());
                    autoState = AutoState.path4;
                    intake.setManualCollect();
                }
                break;
            case path4:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath5());
                    autoState = AutoState.path5;
                    intake.stop();
                }
                break;
            case path6:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath7());
                    autoState = AutoState.path7;
                    intake.setManualCollect();
                }
                break;
            case path7:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath8());
                    autoState = AutoState.path8;
                }
                break;
            case path9:
                if (!follower.isBusy()) {
                    autoState = AutoState.end;
                }
                break;
            case end:
                outtake.stopFeeding();
                intake.stop();
                break;
        }
        telemetry.addData("Autonomous STATE: ", "%d", autoState);
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
        if (alliance == Alliance.blue) {
            paths = new FarBluePaths(follower);
        } else {
            paths = new FarRedPaths(follower);
        }
    }
}
