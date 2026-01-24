package org.firstinspires.ftc.team25313.subsystems.drivetrain.util;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveConstants;

public class MathUtil {
    public static double applyDeadzone(double value) {
        if (Math.abs(value) < DriveConstants.deadzone) {
            return 0;
        }
        return value;
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
