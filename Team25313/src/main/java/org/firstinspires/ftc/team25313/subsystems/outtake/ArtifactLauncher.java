package org.firstinspires.ftc.team25313.subsystems.outtake;

import android.health.connect.datatypes.units.Power;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactLauncher {

    private final DcMotor shooter;
    private final Servo lowPusher;

    private final ElapsedTime timer = new ElapsedTime();

    // enum

    public enum Mode { single, burst }
    public enum ActionState { idle, push, reset, next }
    public enum ShooterPowerState {
        off(0),
        ready(0.45),     // lực mồi để lên tốc
        base(Constants.baseZonePower),
        goal(Constants.goalZonePower);

        public final double power;

        ShooterPowerState(double power) {
            this.power = power;
        }
    }
    public enum ShooterTarget {
        none,
        base,
        goal
    }

    // state

    private Mode mode = Mode.single;
    private ActionState actionState = ActionState.idle;
    private ShooterTarget target = ShooterTarget.none;
    private ShooterPowerState powerState = ShooterPowerState.off;

    private int shotCount = 0;
    private static final int burstMax = 3;

    private double holdPower = 0.55; // default

    // intake callback

    private Runnable intakeLoader = null;

    // constructor

    public ArtifactLauncher(HardwareMap hw) {
        shooter = hw.get(DcMotor.class, Constants.shooter);
        lowPusher = hw.get(Servo.class, Constants.lowPusher);
        lowPusher.setPosition(0);
    }

    // config

    public void setIntakeLoader(Runnable intakeAction) {
        this.intakeLoader = intakeAction;
    }

    public void setMode(Mode m) {
        mode = m;
    }

    public void setPowerForGoalZone() {
        holdPower = Constants.goalZonePower;
    }

    public void setPowerForBaseZone() {
        holdPower = Constants.baseZonePower;
    }

    // shoot api

    public void launch() {
        if (actionState == ActionState.idle && isReadyToShoot()) {
            shotCount = 0;
            timer.reset();
            actionState = ActionState.push;
        }
    }

    public void selectBaseTarget() {
        target = ShooterTarget.base;
        powerState = ShooterPowerState.base;
        timer.reset();
    }

    public void selectGoalTarget() {
        target = ShooterTarget.goal;
        powerState = ShooterPowerState.goal;
        timer.reset();
    }

    public void clearTarget() {
        target = ShooterTarget.none;
        powerState = ShooterPowerState.off;
    }

    // update for fsm

    public void update() {

        updateShooterPower();

        switch (actionState) {

            case idle:
                break;

            case push:
                lowPusher.setPosition(Constants.lowAngle);
                if (timer.milliseconds() > 300) {
                    lowPusher.setPosition(0);
                    timer.reset();
                    actionState = ActionState.reset;
                }
                break;

            case reset:
                if (timer.milliseconds() > 300) {
                    shotCount++;

                    if (mode == Mode.burst && shotCount < burstMax) {
                        actionState = ActionState.next;
                    } else {
                        stopShooter();
                        actionState = ActionState.idle;
                    }
                    timer.reset();
                }
                break;

            case next:
                if (intakeLoader != null) intakeLoader.run();
                actionState = ActionState.push;
                break;
        }
    }

    // power fsm

    private void startSpinUp() {
        powerState = ShooterPowerState.ready;
        timer.reset();
    }

    private void stopShooter() {
        powerState = ShooterPowerState.off;
    }

    private void updateShooterPower() {
        switch (powerState) {
            case ready:
                shooter.setPower(ShooterPowerState.ready.power);

                // chờ motor ổn định
                if (timer.milliseconds() > 300) {
                    if (target == ShooterTarget.base) {
                        powerState = ShooterPowerState.base;
                    } else if (target == ShooterTarget.goal) {
                        powerState = ShooterPowerState.goal;
                    }
                }
                break;

            default:
                shooter.setPower(powerState.power);
                break;
        }
    }

    private boolean isReadyToShoot() {
        if (target == ShooterTarget.none) return false;
        return powerState == ShooterPowerState.base
                || powerState == ShooterPowerState.goal;
    }

    // get

    public boolean isBusy() {
        return actionState != ActionState.idle;
    }

    public ShooterPowerState getPowerState() {
        return powerState;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public Mode getMode() {
        return mode;
    }

    public double getCurrentPower() {
        return shooter.getPower();
    }
}
