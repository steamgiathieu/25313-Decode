package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.opmodes.auto.paths.*;

public abstract class MainAuto extends OpMode {

    protected Follower follower;
    protected AutoPaths paths;

    protected enum AutoState {
        START,
        PATH_1,
        WAIT_1,
        PATH_2,
        PATH_3,
        WAIT_3,
        PATH_4,
        PATH_5,
        WAIT_5,
        PATH_6,
        DONE
    }

    protected AutoState autoState = AutoState.START;
    protected long waitStartTime;

    // ---- abstract ----
    protected abstract void initStartingCondition();

    @Override
    public void init() {
        initStartingCondition();

        follower = org.firstinspires.ftc.team25313.pedroPathing.Constants.createFollower(hardwareMap);

        follower.setStartingPose(getStartingPose());

        buildPaths();
    }

    @Override
    public void loop() {
        follower.update();

        runIntake();
        runOuttake();

        switch (autoState) {

            case START:
                follower.followPath(paths.getPath1());
                autoState = AutoState.PATH_1;
                break;

            case PATH_1:
                if (!follower.isBusy()) {
                    startWait();
                    autoState = AutoState.WAIT_1;
                }
                break;

            case WAIT_1:
                if (waitPassed()) {
                    shoot();
                    follower.followPath(paths.getPath2());
                    autoState = AutoState.PATH_2;
                }
                break;

            case PATH_2:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath3());
                    autoState = AutoState.PATH_3;
                }
                break;

            case PATH_3:
                if (!follower.isBusy()) {
                    startWait();
                    autoState = AutoState.WAIT_3;
                }
                break;

            case WAIT_3:
                if (waitPassed()) {
                    shoot();
                    follower.followPath(paths.getPath4());
                    autoState = AutoState.PATH_4;
                }
                break;

            case PATH_4:
                if (!follower.isBusy()) {
                    follower.followPath(paths.getPath5());
                    autoState = AutoState.PATH_5;
                }
                break;

            case PATH_5:
                if (!follower.isBusy()) {
                    startWait();
                    autoState = AutoState.WAIT_5;
                }
                break;

            case WAIT_5:
                if (waitPassed()) {
                    shoot();
                    follower.followPath(paths.getPath6());
                    autoState = AutoState.PATH_6;
                }
                break;

            case PATH_6:
                if (!follower.isBusy()) {
                    autoState = AutoState.DONE;
                }
                break;

            case DONE:
                stopAll();
                break;
        }
    }

    // ---------- helpers ----------

    protected void buildPaths() {
        paths = new SmallTriangleBluePaths(follower);
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

    protected void runIntake() {
        // intake chạy liên tục
    }

    protected void runOuttake() {
        // outtake giữ sẵn
    }

    protected void shoot() {
        // đẩy servo bắn
    }

    protected void stopAll() {
        // stop motor/servo
    }
}
