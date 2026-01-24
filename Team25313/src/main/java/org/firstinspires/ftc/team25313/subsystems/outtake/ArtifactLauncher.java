package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;

public class ArtifactLauncher {

    private final DcMotorEx leftLauncher;
    private final DcMotorEx rightLauncher;
    private final Servo pusher;
    private final Servo window;

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
        window = hw.get(Servo.class, Constants.window);

        leftLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
        //rightLauncher.setDirection(DcMotorSimple.Direction.REVERSE);

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
        pusher.setPosition(Constants.pusherRestPos);
        window.setPosition(Constants.windowAngle);
    }

    public void stopFeeding() {
        feeding = false;
        restPusher();
    }

    public void powerUp() {
        if (powerLevel == PowerLevel.near) powerLevel = PowerLevel.mid;
        else if (powerLevel == PowerLevel.mid) powerLevel = PowerLevel.far;
    }

    public void powerDown() {
        if (powerLevel == PowerLevel.far) powerLevel = PowerLevel.mid;
        else if (powerLevel == PowerLevel.mid) powerLevel = PowerLevel.near;
    }

    public void update() {
        if (!enabled) return;
        updateVelocity();
        if (feeding) {
            intake.setOuttakeFeed();
            pusher.setPosition(Constants.pusherRestPos);
        }
    }

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
        pusher.setPosition(Constants.pusherLaunchPos); // 0.07
        window.setPosition(Constants.windowRest);
    }

    public boolean isEnabled() { return enabled; }
    public boolean isReadyToShoot() { return isReady(); }
    public PowerLevel getPowerLevel() { return powerLevel; }
    public double getTargetVelocity() { return powerLevel.velocity; }
    public double getActualVelocity() { return getCurrentVelocity(); }
    public boolean isFeeding() {
        return feeding;
    }
}
