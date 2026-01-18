package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;

public class ArtifactLauncher {

    private final DcMotorEx leftLauncher;
    private final DcMotorEx rightLauncher;
    private final Servo pusher;
    private final Servo pusherRight;

    private final ElapsedTime slewTimer = new ElapsedTime();

    public enum PowerLevel {
        near(Constants.nearShotVelocity),
        mid(Constants.midShotVelocity),
        far(Constants.farShotVelocity);

        public final double velocity;
        PowerLevel(double v) { velocity = v; }
    }

    private PowerLevel powerLevel = PowerLevel.near;

    private boolean enabled = false;
    private boolean feeding = false;

    private double commandedVelocity = 0;

    private ArtifactCollector intake;

    public ArtifactLauncher(HardwareMap hw, ArtifactCollector intake) {
        this.intake = intake;
        leftLauncher  = hw.get(DcMotorEx.class, Constants.leftLauncher);
        rightLauncher = hw.get(DcMotorEx.class, Constants.rightLauncher);
        pusher        = hw.get(Servo.class, Constants.pusher);
        pusherRight   = hw.get(Servo.class, Constants.pusherRight);
        pusherRight.setDirection(Servo.Direction.REVERSE);


        leftLauncher.setDirection(DcMotorSimple.Direction.REVERSE);

        setupMotor(leftLauncher);
        setupMotor(rightLauncher);

        restPusher();
    }

    private void setupMotor(DcMotorEx m) {
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m.setVelocityPIDFCoefficients(
                Constants.launcherP,
                Constants.launcherI,
                Constants.launcherD,
                Constants.launcherF
        );
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void enable() {
        enabled = true;
        slewTimer.reset();
    }

    public void disable() {
        enabled = false;
        feeding = false;
        commandedVelocity = 0;

        setVelocity(0);
        restPusher();
        intake.stop();
    }

    public void startFeeding() {
        if (!enabled) return;

        feeding = true;

        intake.setOuttakeFeed();
        pusher.setPosition(Constants.pusherLaunchPos);
        pusherRight.setPosition(Constants.pusherLaunchPos);
    }

    public void stopFeeding() {
        feeding = false;
        restPusher();
    }

    public boolean powerUp() {
        if (powerLevel == PowerLevel.near) {
            powerLevel = PowerLevel.mid;
            return true;
        }
        else if (powerLevel == PowerLevel.mid) {
            powerLevel = PowerLevel.far;
            return true;
        }
        return true;
    }

    public boolean powerDown() {
        if (powerLevel == PowerLevel.far) {
            powerLevel = PowerLevel.mid;
            return true;
        }
        else if (powerLevel == PowerLevel.mid){
            powerLevel = PowerLevel.near;
            return true;
        }
        return true;
    }

    public void update() {
        if (!enabled) return;
        updateVelocity();
        if (feeding) {
            intake.setOuttakeFeed();
            pusher.setPosition(Constants.pusherLaunchPos);
            pusherRight.setPosition(Constants.pusherLaunchPos);
        }
    }
    /* ===================== INTERNAL ===================== */

    private void updateVelocity() {
        double target = powerLevel.velocity;

        double dt = slewTimer.seconds();
        slewTimer.reset();

        double maxDelta = Constants.launcherMaxAccel * dt;
        double delta = target - commandedVelocity;
        delta = Math.max(-maxDelta, Math.min(maxDelta, delta));

        commandedVelocity += delta;

        if (commandedVelocity > target + Constants.launcherOverdriveLimit) {
            commandedVelocity = target + Constants.launcherOverdriveLimit;
        }

        setVelocity(commandedVelocity);
    }

    private void setVelocity(double v) {
        leftLauncher.setVelocity(v);
        rightLauncher.setVelocity(v);
    }

    private boolean isReady() {
        return Math.abs(getCurrentVelocity() - powerLevel.velocity)
                <= Constants.launcherVelocityTolerance;
    }

    private double getCurrentVelocity() {
        return (leftLauncher.getVelocity() + rightLauncher.getVelocity()) / 2.0;
    }

    private void restPusher() {
        pusher.setPosition(Constants.pusherRestPos); // 0.07
        pusherRight.setPosition(Constants.pusherRestPos);
    }

    /* ===================== TELEMETRY ===================== */

    public boolean isEnabled() { return enabled; }
    public boolean isReadyToShoot() { return isReady(); }
    public PowerLevel getPowerLevel() { return powerLevel; }
    public double getTargetVelocity() { return powerLevel.velocity; }
    public double getActualVelocity() { return getCurrentVelocity(); }
    public boolean isFeeding() {
        return feeding;
    }

}
