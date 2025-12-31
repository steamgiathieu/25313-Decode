package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactLauncher {

    // hardware
    private final DcMotor lowShooter;
    private final DcMotor highShooter;
    private final Servo lowPusher;
    private final Servo highPusher;

    private final ElapsedTime timer = new ElapsedTime();


    public enum FireMode {
        single,
        burst
    }

    public enum FireState {
        idle,
        lowPush,
        highPush,
        reset,
        cooldown
    }

    public enum PowerState {
        off,
        spinUp,
        hold
    }


    private FireMode fireMode = FireMode.single;
    private FireState fireState = FireState.idle;
    private PowerState powerState = PowerState.off;

    private int shotCount = 0;
    private static final int burstCount = 3;

    private double targetPower = Constants.baseZonePower;

    // ===== timing (ms) =====
    private static final long lowPushTime = 300;
    private static final long highPushTime = 200;
    private static final long cooldownTime = 2000;


    public ArtifactLauncher(HardwareMap hw) {
        lowShooter = hw.get(DcMotor.class, Constants.lowShooter);
        highShooter = hw.get(DcMotor.class, Constants.highShooter);

        lowPusher = hw.get(Servo.class, Constants.lowPusher);
        highPusher = hw.get(Servo.class, Constants.highPusher);

        highPusher.setDirection(Servo.Direction.REVERSE);

        lowPusher.setPosition(0);
        highPusher.setPosition(0);
    }

    public void spinUp() {
        powerState = PowerState.spinUp;
        timer.reset();
    }

    public void stop() {
        powerState = PowerState.off;
        fireState = FireState.idle;
        shotCount = 0;

        lowShooter.setPower(0);
        highShooter.setPower(0);

        lowPusher.setPosition(0);
        highPusher.setPosition(0);
    }

    public void setBasePower() {
        targetPower = Constants.baseZonePower;
    }

    public void setGoalPower() {
        targetPower = Constants.goalZonePower;
    }

    // ===== mode =====

    public void toggleFireMode() {
        fireMode = (fireMode == FireMode.single)
                ? FireMode.burst
                : FireMode.single;
    }

    public void requestFire() {
        if (powerState == PowerState.hold && fireState == FireState.idle) {
            shotCount = 0;
            fireState = FireState.lowPush;
            timer.reset();
        }
    }

    public void update() {

        // motor power FSM
        switch (powerState) {
            case off:
                lowShooter.setPower(0);
                highShooter.setPower(0);
                break;

            case spinUp:
                lowShooter.setPower(0.5);
                highShooter.setPower(0.5);

                if (timer.milliseconds() > 500) {
                    powerState = PowerState.hold;
                }
                break;

            case hold:
                lowShooter.setPower(targetPower);
                highShooter.setPower(targetPower);
                break;
        }

        switch (fireState) {

            case idle:
                break;

            case lowPush:
                lowPusher.setPosition(Constants.lowAngle);
                if (timer.milliseconds() > lowPushTime) {
                    lowPusher.setPosition(0);
                    fireState = FireState.highPush;
                    timer.reset();
                }
                break;

            case highPush:
                highPusher.setPosition(Constants.highAngle);
                if (timer.milliseconds() > highPushTime) {
                    highPusher.setPosition(0);
                    fireState = FireState.reset;
                    timer.reset();
                }
                break;

            case reset:
                shotCount++;

                if (fireMode == FireMode.burst && shotCount < burstCount) {
                    fireState = FireState.cooldown;
                    timer.reset();
                } else {
                    fireState = FireState.idle;
                }
                break;

            case cooldown:
                if (timer.milliseconds() > cooldownTime) {
                    fireState = FireState.lowPush;
                    timer.reset();
                }
                break;
        }
    }

    public FireMode getFireMode() {
        return fireMode;
    }

    public FireState getFireState() {
        return fireState;
    }

    public PowerState getPowerState() {
        return powerState;
    }

    public double getTargetPower() {
        return targetPower;
    }
}
