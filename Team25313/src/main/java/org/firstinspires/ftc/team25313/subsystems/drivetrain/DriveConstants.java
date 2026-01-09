package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.acmerobotics.dashboard.config.Config;

@Config
public class DriveConstants {

    // Robot basic stats
    public static double wheelRad = 1.90944882; //inches
    public static double gearRatio = 1.0;
    public static double trackWidth = 13.6; // d between front & back wheels in inches
    public static double lateralDistance = 7.5; // d between left & right odo wheels in inches
    public static double forwardOffset = -2.0; // forward wheel offset front robot center

    // Encoder
    public static int ticksPerRev = 8192;
    public static double ticksToInches = (2 * Math.PI * wheelRad) / ticksPerRev;

    // Motor
    public static double maxRPM = 312.0;
    public static double maxRPMFraction = 0.85;
    public static double maxVel = (maxRPM / 60.0) * (2 * Math.PI * wheelRad) * gearRatio;
    public static double maxAccelStrafe = 2.5;
    public static double maxAccelForward = 2.5;
    public static double maxAccelTurn = 3.0;

    // Feedforward
    public static double kV = 1.0 / maxVel;
    public static double kA = 0.003;
    public static double kStatic = 0.03;

    // PID
    public static double targetHeading = 0.0;
    public static double maxHeadingCorrection = 0.5;
    public static double headingP = 2.5;
    public static double headingI = 0.0;
    public static double headingD = 0.15;

    public static double driveP = 1.2;
    public static double driveI = 0.0;
    public static double driveD = 0.1;

    // Localizer switches
    public static boolean useDeadWheels = true;
    public static boolean useMotorEncoders = false;
    public static boolean useAprilTagFusion = true;

    public static double tagPoseTrust = 0.4;

    //AprilTag
    public static double cameraForward = 4.0;
    public static double cameraLeft = 0.0;
    public static double cameraUp = 9.0;

    public static double cameraYaw = 0.0;
    public static double cameraPitch = 0.0;
    public static double cameraRoll = 0.0;

    // TeleOp limits
    public static double slowModeMultiplier = 0.4;
    public static double normalModeMultiplier = 1.0;

    // Deadzone
    public static double deadzone = 0.05;

    // Tuning parameter for strafing
    public static double strafeMultiplier = 1.1; // Adjust this value to fix strafe error

}
