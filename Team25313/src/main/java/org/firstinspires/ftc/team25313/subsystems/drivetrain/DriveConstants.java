package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.acmerobotics.dashboard.config.Config;

@Config
public class DriveConstants {

    // Robot basic stats
    public static double wheelRad = 1.90944882; //inches
    public static double gearRatio = 1.0;
    public static double trackWidth = 13.6; 
    public static double lateralDistance = 7.5; 
    public static double forwardOffset = -2.0; 

    // Motor & Encoder
    public static int ticksPerRev = 8192;
    public static double ticksToInches = (2 * Math.PI * wheelRad) / ticksPerRev;
    public static double maxRPM = 312.0;
    public static double maxVel = (maxRPM / 60.0) * (2 * Math.PI * wheelRad) * gearRatio;

    // Tuning parameters
    public static double normalModeMultiplier = 1.0;
    public static double deadzone = 0.05;

    /**
     * Hệ số bù ma sát khi đi NGANG thuần túy.
     * Đưa về 1.0 vì robot đi không bị chậm, tránh việc dạt ngang quá mức.
     */
    public static double strafeMultiplier = 1.0;

    /**
     * Hệ số bù khi đi CHÉO (Diagonal).
     * Robot bị dạt sang ngang quá nhiều -> Giảm xuống 0.85 để nắn đường đi thẳng hơn.
     */
    public static double diagonalWeight = 0.85; 

}
