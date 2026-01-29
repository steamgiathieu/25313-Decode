package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class ArtifactLauncher {

    private final DcMotorEx leftLauncher;
    private final DcMotorEx rightLauncher;
    private final Servo pusher;
    private final Servo window;

    private final ElapsedTime slewTimer = new ElapsedTime();
    VoltageSensor voltageSensor;

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
        this.voltageSensor = hw.voltageSensor.iterator().next();

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
                Constants.adaptive_launcherF
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
        if (powerLevel == PowerLevel.near) {
            powerLevel = PowerLevel.mid;
        }
        else if (powerLevel == PowerLevel.mid) powerLevel = PowerLevel.far;
    }

    public void powerDown() {
        if (powerLevel == PowerLevel.far) powerLevel = PowerLevel.mid;
        else if (powerLevel == PowerLevel.mid) powerLevel = PowerLevel.near;
    }

    private static boolean reconfig_flag = false;
    public void update() {
        //check voltage level and reconfigure Motor
        if(voltageSensor.getVoltage() >= Constants.highVoltage){
            Constants.adaptive_launcherF = Constants.launcherF_high;
        }
        else if(voltageSensor.getVoltage() <= Constants.lowVoltage){
            Constants.adaptive_launcherF = Constants.launcherF_low;
        }
        else{
            Constants.adaptive_launcherF = Constants.launcherF;
        }
        setupMotor(leftLauncher);
        setupMotor(rightLauncher);

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
    public void setPowerLevel(PowerLevel _set_power){ powerLevel = _set_power;};
    public double getTargetVelocity() { return powerLevel.velocity; }
    public double getActualVelocity() { return getCurrentVelocity(); }
    public boolean isFeeding() {
        return feeding;
    }
}
