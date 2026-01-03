package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactLauncher {

    private final DcMotorEx shooter;
    private final Servo lowPusher;
    private final Servo highPusher;

    private final ElapsedTime timer = new ElapsedTime();

    public enum ShooterMode { single, burst }

    public enum ActionState {
        idle,
        waitingForSpeed,
        lowPush,
        highPush,
        wait
    }

    public enum ShooterPowerState {
        off(0),
        base(Constants.baseZoneVelocity),
        goal(Constants.goalZoneVelocity);

        public final double velocity;
        ShooterPowerState(double velocity) {
            this.velocity = velocity;
        }
    }


    private ShooterMode mode = ShooterMode.single;
    private ActionState actionState = ActionState.idle;
    private ShooterPowerState powerState = ShooterPowerState.off;
    private boolean launcherEnabled = true;

    private int shotCount = 0;
    private static final int burstMax = 3;

    private Runnable intakeOn = null;
    private Runnable intakeOff = null;

    public ArtifactLauncher(HardwareMap hw) {
        shooter = hw.get(DcMotorEx.class, Constants.shooter);
        lowPusher = hw.get(Servo.class, Constants.lowPusher);
        highPusher = hw.get(Servo.class, Constants.highPusher);

        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setVelocityPIDFCoefficients(
                Constants.shooterP,
                Constants.shooterI,
                Constants.shooterD,
                Constants.shooterF
        );

        highPusher.setDirection(Servo.Direction.REVERSE);
        rest();
    }

    public void enableLauncher() {
        launcherEnabled = true;
        powerState = ShooterPowerState.goal;
    }

    public void disableLauncher() {
        launcherEnabled = false;
        powerState = ShooterPowerState.off;
        shooter.setVelocity(0);
        actionState = ActionState.idle;
        shotCount = 0;
        rest();
    }

    public void setIntakeCallbacks(Runnable on, Runnable off) {
        intakeOn = on;
        intakeOff = off;
    }

    public void toggleMode() {
        if (actionState == ActionState.idle) {
            mode = (mode == ShooterMode.single)
                    ? ShooterMode.burst
                    : ShooterMode.single;
        }
    }
    public void setBasePower() {
        powerState = ShooterPowerState.base;
    }

    public void setGoalPower() {
        powerState = ShooterPowerState.goal;
    }

    public void shoot() {
        System.out.println("SHOOT CALLED");

        if (launcherEnabled
                && actionState == ActionState.idle
                && powerState != ShooterPowerState.off) {

            System.out.println("SHOOT ACCEPTED");
            shotCount = 0;
            timer.reset();
            actionState = ActionState.lowPush;
        }
    }

    public void update() {
        if (!launcherEnabled) {
            shooter.setVelocity(0);
            rest();
            return;
        }

        if (powerState == ShooterPowerState.off) {
            shooter.setVelocity(0);
            return;
        }

        shooter.setVelocity(powerState.velocity);

        switch (actionState) {

            case waitingForSpeed:
                if (isShooterReady()) {
                    timer.reset();
                    actionState = ActionState.lowPush;
                }
                break;

            case lowPush:
                lowPusher.setPosition(Constants.lowAngle);
                if (timer.milliseconds() > 300) {
                    lowPusher.setPosition(Constants.pusherRest);
                    timer.reset();
                    actionState = ActionState.highPush;
                }
                break;

            case highPush:
                highPusher.setPosition(Constants.highAngle);
                if (timer.milliseconds() > 300) {
                    highPusher.setPosition(Constants.pusherRest);
                    timer.reset();
                    actionState = ActionState.wait;
                }
                break;

            case wait:
                if (timer.milliseconds() > 2000) {
                    shotCount++;
                    if (mode == ShooterMode.burst && shotCount < burstMax) {
                        if (intakeOn != null) intakeOn.run();

                        timer.reset();
                        actionState = ActionState.lowPush;

                    } else {
                        if (intakeOff != null) intakeOff.run();
                        actionState = ActionState.idle;
                        shotCount = 0;
                    }
                }
                break;

        }

        handleIntake();
    }

    private boolean isShooterReady() {
        return Math.abs(shooter.getVelocity() - powerState.velocity)
                < Constants.shooterVelocityTolerance;
    }

    private void handleIntake() {
        if (mode == ShooterMode.burst && actionState == ActionState.wait) {
            if (intakeOn != null) intakeOn.run();
        } else {
            if (intakeOff != null) intakeOff.run();
        }
    }

    private void rest() {
        lowPusher.setPosition(Constants.pusherRest);
        highPusher.setPosition(Constants.pusherRest);
    }

    public double getCurrentVelocity() {
        return shooter.getVelocity();
    }

    public double getTargetVelocity() {
        return powerState.velocity;
    }

    public ShooterMode getShooterMode() {
        return mode;
    }

    public boolean isLauncherEnabled() {
        return launcherEnabled;
    }

    public boolean isShooting() {
        return actionState != ActionState.idle;
    }

    public ActionState getActionState() {
        return actionState;
    }
}
