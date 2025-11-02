package org.firstinspires.ftc.team25313.subsystems.drivetrain.util;

public class PIDUtil {
    private double kP, kI, kD;
    private double integral, lastError;

    public PIDUtil(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.integral = 0;
        this.lastError = 0;
    }

    public double calculate(double target, double current) {
        double error = target - current;
        integral += error;
        double derivative = error - lastError;
        lastError = error;

        return (kP * error) + (kI * integral) + (kD * derivative);
    }

    public void reset() {
        integral = 0;
        lastError = 0;
    }
}
