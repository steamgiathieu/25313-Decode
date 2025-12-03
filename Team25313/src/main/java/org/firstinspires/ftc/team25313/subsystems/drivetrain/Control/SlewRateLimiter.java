package org.firstinspires.ftc.team25313.subsystems.drivetrain.Control;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.util.MathUtil;

public class SlewRateLimiter {
    private double rateLimit;
    private double prev;
    private double lastTime;

    public SlewRateLimiter(double rateLimit) {
        this.rateLimit = rateLimit;
        prev = 0;
        lastTime = System.nanoTime();
    }

    public double calculate(double input) {
        double now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        double delta = input - prev;
        double limited = MathUtil.clamp(delta, -rateLimit * dt, rateLimit * dt);
        prev += limited;

        return prev;
    }
}