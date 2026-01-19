package org.firstinspires.ftc.team25313;

public final class Constants {

    // General Robot Info
    public static final String botName = "accelerada";
    public static final double botHeight = 0.45; //meters

    // Alliances
    public enum AllianceColor { blue, red }
    public static AllianceColor currentAlliance = AllianceColor.blue;

    // Drivetrain motor name
    public static final String frontLeft  = "front_left_drive";
    public static final String frontRight = "front_right_drive";
    public static final String backLeft   = "back_left_drive";
    public static final String backRight  = "back_right_drive";


    // Drivetrain constants
    public static final double pulsePerRev = 7.0;
    public static final double quadrature = 4.0;
    public static final double wheelRads = 2.5; // inches
    public static final double gearRatio = 19.2; // 19.2:1
    public static final double ticksPerRev = pulsePerRev * quadrature * gearRatio;
    public static final double ticksToInches = (2 * Math.PI * wheelRads) / ticksPerRev;
    public static final double deadzone = 0.05;
    public static final double maxPowOfMove = 0.8;
    public static final double rotateRate = 0.5;

    // PIDF for Drive motors
    public static double drive_kP = 1.0;
    public static double drive_kI = 0.0;
    public static double drive_kD = 0.1;
    public static double drive_kF = 0.0;

    // Motion Constraints (for PedroPathing)
    public static double maxVel = 50.0; // inches/s
    public static double maxAccel = 45.0; // inches/s^2
    public static double maxAngVel = Math.toRadians(180);
    public static double maxAngAccel = Math.toRadians(180);

    // Intake Subsystem
    public static final String collector = "collector";
    public static final double intakeMotorIn = 0.7;
    public static final double intakeMotorShoot = 0.65;

    // Outtake Subsystem
    public static final String leftLauncher = "leftLauncher";
    public static final String rightLauncher = "rightLauncher";
    public static final String pusher = "pusher";
    public static final String pusherRight = "pusherRight";
    public static final double nearShotVelocity = 1280;
    public static final double midShotVelocity  = 1420;
    public static final double farShotVelocity  = 1620;
    public static final double launcherP = 23;
    public static final double launcherI = 0.0;
    public static final double launcherD = 0.001;
    public static final double launcherF = 11.6;

    public static final double launcherMaxAccel = 500;
    public static final double launcherOverdriveLimit = 40;
    public static final double launcherVelocityTolerance = 35;
    public static final double pusherRestPos = 0.08;
    public static final double pusherLaunchPos = 0.17;
    public static final double pusherRightRestPos = 0.05
            ;
    public static final double pusherRightLaunchPos = 0.2;

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
    public static final int blueGoalTagId = 20;
    public static final int redGoalTagId  = 24;
    public static final double maxAimYawDeg = 15.0;
    public static final double shotNearMaxDist = 1.2;
    public static final double shotMidMaxDist  = 2.0;
    public static final double maxShootYawDeg = 5.0; // ví dụ ±5 độ


    // Miscellaneous
    public static final boolean isFieldCentric = true;
    public static final boolean useOdometry = true;
    public static final boolean debugMode = true;
    public static final boolean isPanelsAvailable = false;

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

    // Stats from ARENA
    public static double goalHeight = 1.3655; //meters
    public static double deltaHeight = goalHeight - botHeight;
    public static double fallAccelerate = 9.8;
}
