package org.firstinspires.ftc.team25313.subsystems.drivetrain.util;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveConstants;

/**
 * Utility class để calibrate sai số encoder hoặc bánh xe.
 * Dùng để đo offset hoặc scale factor khi chạy test quãng đường.
 */
public class Calibration {

    public static double calculateEncoderCorrection(double actualDistance, double expectedTicks) {
        double expectedDistance = DriveConstants.encoderTicksToInches(expectedTicks);
        return actualDistance / expectedDistance;
    }

    public static double averageTicks(DcMotorEx... motors) {
        double sum = 0;
        for (DcMotorEx m : motors) sum += m.getCurrentPosition();
        return sum / motors.length;
    }

    public static double encoderToInches(double ticks) {
        return DriveConstants.encoderTicksToInches(ticks);
    }
}

