package org.firstinspires.ftc.team25313;

public final class Constants {

    // General Robot Info
    public static final String botName = "25313 'Curry'";


    // Drivetrain motor name
    public static final String frontLeft  = "front_left_drive";
    public static final String frontRight = "front_right_drive";
    public static final String backLeft   = "back_left_drive";
    public static final String backRight  = "back_right_drive";
    public static final double maxVel = 1;
    public static final double rotateRate = 0.5;
    public static final double deadzone = 0.05;


    // Intake Subsystem
    public static final String collector = "collector";
    public static final String leftCollector = "leftCollector";
    public static final String rightCollector = "rightCollector";
    public static final double intakeMotorIn = 0.8;
    public static double intakeMotorShoot = 0.6;
    public static final double farZoneIntakeMotorShoot = 0.5;
    public static final double closeZoneIntakeMotorShoot = 0.7;
    public static final double autoIntakeMotorShoot = 0.5;
    public static final double intakeServoIn = 0.8;
    public static final double shootingTime = 2000; //ms

    // Outtake Subsystem
    public static final String leftLauncher = "leftLauncher";
    public static final String rightLauncher = "rightLauncher";
    public static final String pusher = "pusher";
    public static final String window = "window";
    public static final double nearShotVelocity = 1300;
    public static final double midShotVelocity  = 1420;
    public static final double farShotVelocity  = 1640;
    public static final double launcherP = 23;
    public static final double launcherI = 0.0;
    public static final double launcherD = 0.0001;
    public static final double launcherF = 11.6;
    public static final double low_power_launcherF = 12.3;
    public static double adaptive_launcherF = 11.6;
    public static final double launcherMaxAccel = 500;
    public static final double launcherOverdriveLimit = 40;
    public static final double launcherVelocityTolerance = 35;
    public static final double pusherRestPos = 0.15;
    public static final double pusherLaunchPos = 0.08;
    public static final double windowAngle = 0.2;
    public static final double windowRest = 0.05;

    public static final double nominalVoltage = 12.0;

    // Lift Subsystem
    public static final String lifter = "lifter";
    public static final double liftPower = 1;

    // Camera & Vision
    public static final String webcamName = "Webcam 1";
    public static final int blueGoalTagId = 20;
    public static final int redGoalTagId  = 24;
    public static final double shotNearMaxDist = 1.2;
    public static final double shotMidMaxDist  = 2.0;
    public static final double minValidTagDistM = 0.3;
    public static final double maxValidTagDistM = 4.0;
    public static final double yawNearDeg = 30.0;
    public static final double yawMidDeg  = 25.0;
    public static final double yawFarDeg  = 20.0;
    public static final double distanceAlpha = 0.3;


    // Utility Methods
    private Constants() {
        // Prevent instantiation
    }

}
