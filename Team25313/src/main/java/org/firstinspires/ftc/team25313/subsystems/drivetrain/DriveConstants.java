package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.acmerobotics.dashboard.config.Config;

@Config
public class DriveConstants {
    // Encoder và bánh
    public static final double TICKS_PER_REV = 560; // REV HD Hex 20:1
    public static final double MAX_RPM = 300;

    public static final double WHEEL_RADIUS = 2.0; // inch
    public static final double GEAR_RATIO = 1.0; // 1:1
    public static final double TRACK_WIDTH = 13.0; // inch

    // Hệ số tốc độ
    public static final double MAX_VEL = 45.0;   // in/s
    public static final double MAX_ACCEL = 35.0;
    public static final double MAX_ANG_VEL = Math.toRadians(180);
    public static final double MAX_ANG_ACCEL = Math.toRadians(180);

    // PedroPathing coefficients
    public static final double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static final double kA = 0.0;
    public static final double kStatic = 0.0;

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }
}
