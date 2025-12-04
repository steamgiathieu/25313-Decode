package org.firstinspires.ftc.team25313;

import com.acmerobotics.dashboard.config.Config;

@Config
public final class Constants {

    // General Robot Info
    public static final String botName = "STEAM GIA THIEU";
    public static final boolean isUsingDashboard = true;

    // Alliances
    public enum AllianceColor { BLUE, RED }
    public static AllianceColor currentAlliance = AllianceColor.BLUE;

    // Drivetrain motor name
    public static final String frontLeft  = "frontLeft";
    public static final String frontRight = "frontRight";
    public static final String backLeft   = "backLeft";
    public static final String backRight  = "backRight";

    // Odometry pods
    public static final String odoLeft  = "odoLeft";
    public static final String odoRight = "odoRight";
    public static final String odoBack  = "odoBack";

    // Drivetrain constants
    public static final double ticksPerRev = 537.7;
    public static final double maxRPM = 312.0;
    public static final double wheelRads = 1.8898; // inches
    public static final double gearRatio = 1.0; // 1:1
    public static final double trackWidth = 14.5; // inches
    public static final double odoTrackWidth = 14.8; // inches
    public static final double odoCenterOffset = -7.2; // inches

    // PIDF for Drive motors
    public static double drive_kP = 1.0;
    public static double drive_kI = 0.0;
    public static double drive_kD = 0.1;
    public static double drive_kF = 0.0;

    // Motion Constraints (for PedroPathing / trajectory control)
    public static double maxVel = 50.0; // inches/s
    public static double maxAccel = 45.0; // inches/s^2
    public static double maxAngVel = Math.toRadians(180);
    public static double maxAngAccel = Math.toRadians(180);

    // Intake Subsystem
    public static final String intake = "intake_servo";
    public static final double intakeIn = 1.0;
    public static final double intakeOut = -1.0;

    // Outtake Subsystem
    public static final String leftShooter = "leftShooter";
    public static final String rightShooter = "rightShooter";
    public static final double launchPower = 0.5;

    // Lift Subsystem
    public static final String leftLift = "leftLift";
    public static final String rightLift = "rightLift";
    public static final int liftLow = 200;
    public static final int liftMid = 400;
    public static final int liftHigh = 600;
    public static final double liftPower = 0.8;

    // Camera & Vision
    public static final String webcamName = "Webcam 1";
    public static final String aprilTagProcessor = "aprilTagProcessor";
    public static final double camXOffset = 0.0; // inches
    public static final double camYOffset = 0.0; // inches
    public static final double camHeadingOffset = 0.0; // radians

    // Miscellaneous
    public static final boolean fieldCentric = true;
    public static final boolean useOdometry = true;
    public static final boolean debugMode = true;

    // Utility Methods
    private Constants() {
        // Prevent instantiation
    }

    public static double encoderTicksToInches(double ticks) {
        return wheelRads * 2 * Math.PI * gearRatio * ticks / ticksPerRev;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * gearRatio * 2 * Math.PI * wheelRads / 60.0;
    }
}
