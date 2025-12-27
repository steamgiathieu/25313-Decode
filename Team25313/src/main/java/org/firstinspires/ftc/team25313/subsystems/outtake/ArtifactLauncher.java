package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team25313.Constants;

public class ArtifactLauncher {

    private final DcMotor shooter;
    private final Servo lowPusher;
    private final Servo highPusher;
    private ShooterState state = ShooterState.off;
    private ShooterActionState actionState = ShooterActionState.idle;
    private ShooterMode mode = ShooterMode.single;
    private final ElapsedTime timer = new ElapsedTime();
    private int shotCount = 0;
    private static final int burstMax = 3;

    public ArtifactLauncher(HardwareMap hwMap) {
        shooter = hwMap.get(DcMotor.class, Constants.shooter);
        lowPusher = hwMap.get(Servo.class, Constants.lowPusher);
        highPusher = hwMap.get(Servo.class, Constants.highPusher);
        highPusher.setDirection(Servo.Direction.REVERSE);
        rest();
    }

    public enum ShooterState {
        off(0),
        ready(Constants.shooterReady),
        base(Constants.baseZonePower),
        goal(Constants.goalZonePower);

        public final double power;

        ShooterState(double power) {
            this.power = power;
        }
    }

    public enum ShooterActionState {idle, spinup, pushlow, pushhigh, reset}
    public enum ShooterMode {single, continuous}
    public void setLauncherReady() {
        state = ShooterState.ready;
    }
    public void setBaseLaunch() {
        if (state != ShooterState.off) {
            state = ShooterState.base;
        }
    }

    public void setGoalLaunch() {
        if (state != ShooterState.off) {
            state = ShooterState.goal;
        }
    }
    public void setLauncherOff() {
        state = ShooterState.off;
    }

    public void update() {

        shooter.setPower(state.power);
        switch (actionState) {
            case pushlow:
                lowPusher.setPosition(Constants.lowAngle);
                if (timer.milliseconds() > 250) {
                    lowPusher.setPosition(0);
                    timer.reset();
                    actionState = ShooterActionState.pushhigh;
                }
                break;
            case pushhigh:
                highPusher.setPosition(Constants.highAngle);
                if (timer.milliseconds() > 250) {
                    highPusher.setPosition(0);
                    timer.reset();
                    actionState = ShooterActionState.reset;
                }
                break;
            case reset:
                if (timer.milliseconds() > 300) {
                    shotCount++;
                    if (mode == ShooterMode.continuous) {
                        timer.reset();
                        actionState = ShooterActionState.pushlow;
                    } else {
                        actionState = ShooterActionState.idle;
                        shotCount = 0;
                    }
                }
                break;
        }

    }

    public void toggleMode() {
        mode = (mode == ShooterMode.single) ? ShooterMode.continuous : ShooterMode.single;
    }

    public void shoot() {
        if (actionState == ShooterActionState.idle && state != ShooterState.off) {
            timer.reset();
            actionState = ShooterActionState.pushlow;
        }
    }

    public void rest() {
        lowPusher.setPosition(0);
        highPusher.setPosition(0);
    }

    public boolean isRunning() {
        return state != ShooterState.off;
    }
    public ShooterState getState() {
        return state;
    }
    public ShooterActionState getActionState() {
        return actionState;
    }
    public ShooterMode getMode() {
        return mode;
    }
}
