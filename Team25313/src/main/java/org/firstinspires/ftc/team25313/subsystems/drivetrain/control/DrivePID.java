package org.firstinspires.ftc.team25313.subsystems.drivetrain.control;

public class DrivePID {
    private double kP, kI, kD;

    private double target = 0;
    private double integral = 0;
    private double lastError = 0;

    private double integralLimit = 2.0;
    private double derivativeFilter = 0.7;

    public DrivePID(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void reset() {
        integral = 0;
        lastError = 0;
    }

    public double update(double currentPosition) {

        double error = target - currentPosition;

        integral += error;
        integral = clamp(integral, -integralLimit, integralLimit);

        double derivative = (error - lastError);
        derivative = derivativeFilter * derivative;

        lastError = error;

        return (kP * error) + (kI * integral) + (kD * derivative);
    }

    private double clamp(double x, double min, double max) {
        return Math.max(min, Math.min(max, x));
    }
}
