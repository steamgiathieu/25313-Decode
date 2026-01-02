package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactLauncher {

    private final DcMotor shooter;
    private final Servo lowPusher;
    private final Servo highPusher;

    private final ElapsedTime timer = new ElapsedTime();


    public enum ShooterMode { single, burst }

    public enum ActionState {
        idle,
        lowPush,
        highPush,
        wait
    }

    public enum ShooterPowerState {
        off(0),
        base(Constants.baseZonePower),
        goal(Constants.goalZonePower);

        public final double power;
        ShooterPowerState(double power) {
            this.power = power;
        }
    }

    private ShooterMode mode = ShooterMode.single;
    private ActionState actionState = ActionState.idle;
    private ShooterPowerState powerState = ShooterPowerState.off;
    private boolean launcherEnabled = false;


    private int shotCount = 0;
    private static final int burstMax = 3;

    private Runnable intakeOn = null;
    private Runnable intakeOff = null;

    public ArtifactLauncher(HardwareMap hw) {
        shooter = hw.get(DcMotor.class, Constants.shooter);
        lowPusher = hw.get(Servo.class, Constants.lowPusher);
        highPusher = hw.get(Servo.class, Constants.highPusher);

        highPusher.setDirection(Servo.Direction.REVERSE);
        rest();
    }

    public void enableLauncher() {
        launcherEnabled = true;
        powerState = ShooterPowerState.goal; // hoặc base mặc định
    }

    public void disableLauncher() {
        launcherEnabled = false;
        powerState = ShooterPowerState.off;
        actionState = ActionState.idle;
        shotCount = 0;
    }

    public void setIntakeCallbacks(Runnable on, Runnable off) {
        intakeOn = on;
        intakeOff = off;
    }

    public void toggleMode() {
        mode = (mode == ShooterMode.single) ? ShooterMode.burst : ShooterMode.single;
    }

    public void setBasePower() {
        powerState = ShooterPowerState.base;
    }

    public void setGoalPower() {
        powerState = ShooterPowerState.goal;
    }

    public void stopShooter() {
        powerState = ShooterPowerState.off;
        actionState = ActionState.idle;
        shotCount = 0;
        if (intakeOff != null) intakeOff.run();
    }

    /* ================= SHOOT ================= */

    public void shoot() {
        if (actionState == ActionState.idle && powerState != ShooterPowerState.off) {
            shotCount = 0;
            timer.reset();
            actionState = ActionState.lowPush;
        }
    }

    public void update() {
        if (!launcherEnabled) {
            shooter.setPower(0);
            return;
        }

        shooter.setPower(powerState.power);

        switch (actionState) {

            case lowPush:
                lowPusher.setPosition(Constants.lowAngle);
                if (timer.milliseconds() > 300) {
                    lowPusher.setPosition(0);
                    timer.reset();
                    actionState = ActionState.highPush;
                }
                break;

            case highPush:
                highPusher.setPosition(Constants.highAngle);
                if (timer.milliseconds() > 300) {
                    highPusher.setPosition(0);
                    timer.reset();
                    actionState = ActionState.wait;
                }
                break;

            case wait:
                if (timer.milliseconds() > 2000) {

                    shotCount++;

                    if (mode == ShooterMode.burst && shotCount < burstMax) {
                        timer.reset();
                        actionState = ActionState.lowPush;
                    } else {
                        actionState = ActionState.idle;
                        shotCount = 0;
                    }
                }
                break;
        }
        if (mode == ShooterMode.burst && launcherEnabled && actionState == ActionState.wait && shotCount > 0 && shotCount < burstMax) {
            if (intakeOn != null) intakeOn.run();
        } else {
            if (intakeOff != null) intakeOff.run();
        }
    }

    public ShooterMode getMode() {
        return mode;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public ShooterPowerState getPowerState() {
        return powerState;
    }

    public boolean isShooting() {
        return actionState != ActionState.idle;
    }

    public int getShotCount() {
        return shotCount;
    }

    private void rest() {
        lowPusher.setPosition(0);
        highPusher.setPosition(0);
    }
}
