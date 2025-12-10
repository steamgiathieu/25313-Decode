package org.firstinspires.ftc.team25313.subsystems.drivetrain.pid;

public class HeadingPID {

    private double kP, kI, kD;

    private double targetAngle = 0;
    private double integral = 0;
    private double lastError = 0;
    private long lastTime;

    private double integralLimit = 0.6;
    private double derivativeFilter = 0.7;

    public HeadingPID(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.lastTime = System.nanoTime();
    }

    public void reset() {
        this.integral = 0;
        this.lastError = 0;
        this.lastTime = (long) (lastTime / 1e9);
    }

    // Normalize angle to [-180, 180]
    private double angleWrap(double angle) {
        angle %= 360;
        if (angle > 180) angle -= 360;
        if (angle < -180) angle += 360;
        return angle;
    }

    private double clamp(double x, double min, double max) {
        return Math.max(min, Math.min(max, x));
    }

    public double calculate(double targetHeading, double currentHeading) {
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        double error = angleWrap(targetHeading - currentHeading);

        // Integral
        integral += error * dt;
        integral = clamp(integral, -integralLimit, integralLimit);

        // Derivative
        double derivative = (error - lastError) * derivativeFilter;

        lastError = error;

        // PID output
        return kP * error + kI * integral + kD * derivative;
    }
}
