package org.firstinspires.ftc.team25313.subsystems.drivetrain;

public class DriveConstants {

    // Robot basic stats
    public static final double wheelRad = 1.90944882; //inches
    public static final double gearRatio = 1.0;
    public static final double trackWidth = 13.6; // d between front & back wheels in inches
    public static final double lateralDistance = 7.5; // d between left & right odo wheels in inches
    public static final double forwardOffset = -2.0; // forward wheel offset front robot center

    // Encoder
    public static final int ticksPerRev = 8192;
    public static final double ticksToInches = (2 * Math.PI * wheelRad) / ticksPerRev;

    // Motor
    public static final double maxRPM = 312.0;
    public static final double maxRPMFraction = 0.85;
    public static final double maxVel = (maxRPM / 60.0) * (2 * Math.PI * wheelRad) * gearRatio;
    public static final double maxAccelStrafe = 2.5;
    public static final double maxAccelForward = 2.5;
    public static final double maxAccelTurn = 3.0;

    // Feedforward
    public static final double kV = 1.0 / maxVel;
    public static final double kA = 0.003;
    public static final double kStatic = 0.03;

    // PID
    public static double targetHeading = 0.0;
    public static final double maxHeadingCorrection = 0.5;
    public static final double headingP = 2.5;
    public static final double headingI = 0.0;
    public static final double headingD = 0.15;

    public static final double driveP = 1.2;
    public static final double driveI = 0.0;
    public static final double driveD = 0.1;

    // Localizer switches
    public static final boolean useDeadWheels = true;
    public static final boolean useMotorEncoders = false;
    public static final boolean useAprilTagFusion = true;

    public static final double tagPoseTrust = 0.4;

    //AprilTag
    public static final double cameraForward = 4.0;
    public static final double cameraLeft = 0.0;
    public static final double cameraUp = 9.0;

    public static final double cameraYaw = 0.0;
    public static final double cameraPitch = 0.0;
    public static final double cameraRoll = 0.0;

    // TeleOp limits
    public static final double slowModeMultiplier = 0.4;
    public static final double normalModeMultiplier = 1.0;

    // Deadzone
    public static final double deadzone = 0.05;

}