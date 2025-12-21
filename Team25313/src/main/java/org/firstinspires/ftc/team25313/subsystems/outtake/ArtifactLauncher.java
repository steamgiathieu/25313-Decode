package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.Utility;

public class ArtifactLauncher {

    private final DcMotor shooter;
    private ShooterState state = ShooterState.off;
//    private final Servo ShooterAssistant;

    private double powerRest;

//    public static double assistantRest = 0.0;
//    public static double assistantPush = 0.35;


    public ArtifactLauncher(HardwareMap hwMap) {
        shooter = hwMap.get(DcMotor.class, Constants.shooter);
//        ShooterAssistant = hwMap.get(Servo.class, "ShooterAssistant");

//        ShooterAssistant.setPosition(assistantRest);
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
    }

    public ShooterState getState() {
        return state;
    }

    public double getPower() {
        return state.power;
    }

    public boolean isRunning() {
        return state != ShooterState.off;
    }
    /** Đẩy bóng vào shooter */
//    public void push() {
//        ShooterAssistant.setPosition(assistantPush);
//    }
//    public void rest() {
//        ShooterAssistant.setPosition(assistantRest);
//    }
//    public double getCurrentPowerLevel() {
//        return Power;
//    }
    public double getShooterPower(double distanceToGoal) {
        double theta = Math.atan(Constants.deltaHeight / distanceToGoal);
        double targetVelocity = Math.sqrt((Constants.deltaHeight * 2 * Constants.fallAccelerate) / Math.pow(Math.sin(theta), 2));
        double targetRPM = (30 * targetVelocity) / (Math.PI) * Constants.shooterRad;
        double power = targetRPM / 6000;
        return power;
    }
}
