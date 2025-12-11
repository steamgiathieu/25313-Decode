package org.firstinspires.ftc.team25313.subsystems.drivetrain.pid;

import org.firstinspires.ftc.team25313.Utility;

public class HeadingPID {

    private double kP, kI, kD;

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
        this.lastTime = System.nanoTime();
    }

    // Normalize angle to [-180, 180]
    private double angleWrap(double angle) {
        angle %= 360;
        if (angle > 180) angle -= 360;
        if (angle < -180) angle += 360;
        return angle;
    }

    public double calculate(double targetHeading, double currentHeading) {
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        double error = angleWrap(targetHeading - currentHeading);

        // Integral
        if (Math.abs(error) < 20) {
            integral += error * dt;
            integral = Utility.clamp(integral, -integralLimit, integralLimit);
        }

        // Derivative
        double derivative = (error - lastError) * derivativeFilter;

        lastError = error;

        // PID output
        double output  = kP * error + kI * integral + kD * derivative;
        return Utility.clamp(output, -1, 1);
    }
}
