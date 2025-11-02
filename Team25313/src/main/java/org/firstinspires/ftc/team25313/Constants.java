package org.firstinspires.ftc.team25313;

import com.acmerobotics.dashboard.config.Config;

/**
 * Centralized configuration for the entire robot.
 * Use with FTC Dashboard for live tuning and consistent constants management.
 */
@Config
public final class Constants {

    /*** --- General Robot Info --- ***/
    public static final String ROBOT_NAME = "ConChoCaoBangBoPC";
    public static final boolean USE_DASHBOARD = true;

    /*** --- Alliance (for auto & teleop logic) --- ***/
    public enum AllianceColor { BLUE, RED }
    public static AllianceColor CURRENT_ALLIANCE = AllianceColor.BLUE;

    /*** --- Drivetrain Motor Names --- ***/
    public static final String FRONT_LEFT_MOTOR  = "frontLeft";
    public static final String FRONT_RIGHT_MOTOR = "frontRight";
    public static final String BACK_LEFT_MOTOR   = "backLeft";
    public static final String BACK_RIGHT_MOTOR  = "backRight";

    /*** --- Odometry Pods --- ***/
    public static final String ODO_LEFT  = "odoLeft";
    public static final String ODO_RIGHT = "odoRight";
    public static final String ODO_BACK  = "odoBack";

    /*** --- Drivetrain Constants --- ***/
    public static final double TICKS_PER_REV = 537.7; // GoBilda 312 RPM
    public static final double MAX_RPM = 312.0;
    public static final double WHEEL_RADIUS = 1.8898; // inches
    public static final double GEAR_RATIO = 1.0; // 1:1
    public static final double TRACK_WIDTH = 14.5; // inches
    public static final double ODOMETRY_TRACK_WIDTH = 14.8; // inches
    public static final double ODOMETRY_CENTER_OFFSET = -7.2; // inches

    /*** --- PIDF for Drive Motors --- ***/
    public static double DRIVE_kP = 1.0;
    public static double DRIVE_kI = 0.0;
    public static double DRIVE_kD = 0.1;
    public static double DRIVE_kF = 0.0;

    /*** --- Motion Constraints (for PedroPathing / trajectory control) --- ***/
    public static double MAX_VEL = 50.0; // inches/s
    public static double MAX_ACCEL = 45.0; // inches/s^2
    public static double MAX_ANG_VEL = Math.toRadians(180);
    public static double MAX_ANG_ACCEL = Math.toRadians(180);

    /*** --- Intake Subsystem --- ***/
    public static final String INTAKE_MOTOR = "intakeMotor";
    public static final double INTAKE_POWER = 1.0;

    /*** --- Outtake Subsystem --- ***/
    public static final String OUTTAKE_MOTOR = "outtakeMotor";
    public static final String OUTTAKE_SERVO = "outtakeServo";
    public static final double OUTTAKE_SHOOT_POWER = 0.9;
    public static final double OUTTAKE_SERVO_PUSH = 0.7;
    public static final double OUTTAKE_SERVO_RETRACT = 0.2;

    /*** --- Lift Subsystem --- ***/
    public static final String LIFT_MOTOR = "liftMotor";
    public static final int LIFT_LOW = 200;
    public static final int LIFT_MID = 400;
    public static final int LIFT_HIGH = 600;
    public static final double LIFT_POWER = 0.8;

    /*** --- Camera & Vision --- ***/
    public static final String WEBCAM_NAME = "Webcam 1";
    public static final String APRILTAG_PROCESSOR = "aprilTagProcessor";
    public static final double CAMERA_X_OFFSET = 0.0; // inches
    public static final double CAMERA_Y_OFFSET = 0.0; // inches
    public static final double CAMERA_HEADING_OFFSET = 0.0; // radians

    /*** --- Miscellaneous --- ***/
    public static final boolean FIELD_CENTRIC = true;
    public static final boolean USE_ODOMETRY = true;
    public static final boolean DEBUG_MODE = true;

    /*** --- Utility Methods --- ***/
    private Constants() {
        // Prevent instantiation
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }
}
