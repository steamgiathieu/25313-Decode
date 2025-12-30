package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactLauncher {
    private final DcMotor shooter;
    private final Servo lowPusher;
    private final Servo highPusher;

    private final ElapsedTime timer = new ElapsedTime();

    public enum Mode { single, burst }
    public enum ActionState {
        idle,
        lowPush,
        lowReset,
        highPush,
        highReset
    }
    public enum ShooterTarget { none, base, goal }
    private Mode mode = Mode.single;
    private ActionState actionState = ActionState.idle;
    private ShooterTarget target = ShooterTarget.none;

    private int shotCount = 0;
    private static final int burstMax = 3;

    private static final double lowPushPos  = 0.3;
    private static final double highPushPos = 0.25;

    private static final long lowPushTime   = 300;
    private static final long lowResetTime  = 100;
    private static final long highPushTime  = 300;

    public ArtifactLauncher(HardwareMap hw) {
        shooter = hw.get(DcMotor.class, Constants.shooter);
        lowPusher = hw.get(Servo.class, Constants.lowPusher);
        highPusher = hw.get(Servo.class, Constants.highPusher);

        highPusher.setDirection(Servo.Direction.REVERSE);
        resetServos();
    }

    public void fire() {
        if (actionState == ActionState.idle && target != ShooterTarget.none) {
            shotCount = 0;
            timer.reset();
            actionState = ActionState.lowPush;
        }
    }

    public void update() {
        updateShooterPower();
        updateFSM();
    }

    public void toggleMode() {
        mode = (mode == Mode.single) ? Mode.burst : Mode.single;
    }

    public void stop() {
        shooter.setPower(0);
        actionState = ActionState.idle;
        target = ShooterTarget.none;
        shotCount = 0;
        resetServos();
    }

    public void selectBaseTarget() {
        target = ShooterTarget.base;
    }

    public void selectGoalTarget() {
        target = ShooterTarget.goal;
    }

    private void updateFSM() {
        switch (actionState) {

            case lowPush:
                lowPusher.setPosition(lowPushPos);
                if (timer.milliseconds() >= lowPushTime) {
                    lowPusher.setPosition(0);
                    timer.reset();
                    actionState = ActionState.lowReset;
                }
                break;

            case lowReset:
                if (timer.milliseconds() >= lowResetTime) {
                    timer.reset();
                    actionState = ActionState.highPush;
                }
                break;

            case highPush:
                highPusher.setPosition(highPushPos);
                if (timer.milliseconds() >= highPushTime) {
                    highPusher.setPosition(0);
                    timer.reset();
                    actionState = ActionState.highReset;
                }
                break;

            case highReset:
                shotCount++;
                if (mode == Mode.burst && shotCount < burstMax) {
                    timer.reset();
                    actionState = ActionState.lowPush;
                } else {
                    actionState = ActionState.idle;
                    shotCount = 0;
                }
                break;

            case idle:
                break;
        }
    }

    private void updateShooterPower() {
        switch (target) {
            case base:
                shooter.setPower(Constants.baseZonePower);
                break;
            case goal:
                shooter.setPower(Constants.goalZonePower);
                break;
            case none:
                shooter.setPower(0);
                break;
        }
    }

    private void resetServos() {
        lowPusher.setPosition(0);
        highPusher.setPosition(0);
    }

    public boolean isBusy() {
        return actionState != ActionState.idle;
    }

    public Mode getMode() { return mode; }
    public ActionState getActionState() { return actionState; }
    public ShooterTarget getTarget() { return target; }
}
