package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.control.HeadingPID;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.control.SlewRateLimiter;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.util.MathUtil;
import org.firstinspires.ftc.team25313.Constants;
public class DriveSubsystem {

    public DcMotor leftFront, leftBack, rightFront, rightBack;
    private IMU imu;

//    private final SlewRateLimiter xLimiter;
//    private final SlewRateLimiter yLimiter;
//    private final SlewRateLimiter turnLimiter;

//    private final HeadingPID headingPID;

    public DriveSubsystem(HardwareMap hardwareMap) {

        leftFront = hardwareMap.get(DcMotor.class, Constants.frontLeft);
        leftBack = hardwareMap.get(DcMotor.class, Constants.backLeft);
        rightFront = hardwareMap.get(DcMotor.class, Constants.frontRight);
        rightBack = hardwareMap.get(DcMotor.class, Constants.backRight);

        leftFront.setDirection(com.qualcomm.robotcore.hardware.DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters myIMUparameters;
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );
        imu.initialize(myIMUparameters);

        // Control classes
//        xLimiter = new SlewRateLimiter(DriveConstants.maxAccelStrafe);
//        yLimiter = new SlewRateLimiter(DriveConstants.maxAccelForward);
//        turnLimiter = new SlewRateLimiter(DriveConstants.maxAccelTurn);
//
//        headingPID = new HeadingPID(
//                DriveConstants.headingP,
//                DriveConstants.headingI,
//                DriveConstants.headingD
//        );
    }
    public double frontLeftPower;
    public double frontRightPower;
    public double backLeftPower;
    public double backRightPower;

    public void driveRobotRelated(double forward, double strafe, double rotate) {
//        forward = MathUtil.applyDeadzone(forward);
//        strafe = MathUtil.applyDeadzone(strafe);
//        rotate = MathUtil.applyDeadzone(rotate);

        frontLeftPower = forward + strafe + rotate;
        frontRightPower = forward - strafe - rotate;
        backLeftPower = forward - strafe + rotate;
        backRightPower = forward + strafe - rotate;

        double min = -0.5;
        double max = 0.5;

        frontLeftPower = MathUtil.clamp(frontLeftPower, min, max);
        frontRightPower = MathUtil.clamp(frontRightPower, min, max);
        backLeftPower = MathUtil.clamp(backLeftPower, min, max);
        backRightPower = MathUtil.clamp(backRightPower, min, max);

        setMotorPower(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    public void driveFieldRelated(double forward, double strafe, double rotate) {
        forward = MathUtil.applyDeadzone(forward);
        strafe = MathUtil.applyDeadzone(strafe);
        rotate = MathUtil.applyDeadzone(rotate);

        // Smooth movement with slew rate limiters
//        x = xLimiter.calculate(x);
//        y = yLimiter.calculate(y);
//        turn = turnLimiter.calculate(turn);

//        if (Math.abs(turn) < 0.02) {  // if rotate was not made by user
//            double correction = calculateHeadingCorrection();
//            turn = correction;         // use PID to correct heading
//        } else { // rotate manually
//            // Update heading
//            DriveConstants.targetHeading = getHeading();
//            headingPID.reset();
//        }

        double robotAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(forward, strafe);
        theta = AngleUnit.normalizeRadians(theta - robotAngle);

        forward = r * Math.sin(theta);
        strafe = r * Math.cos(theta);

        double frontLeftPower = forward + strafe + rotate;
        double frontRightPower = forward- strafe - rotate;
        double backLeftPower = forward - strafe + rotate;
        double backRightPower = forward + strafe - rotate;
        setMotorPower(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }
    public void setMotorPower(double lfP, double lbP, double rfP, double rbP) {
        leftFront.setPower(lfP);
        leftBack.setPower(lbP);
        rightFront.setPower(rfP);
        rightBack.setPower(rbP);
    }

    public double fixedRotate(double rotateRaw, double rotateRate) {
        double rotationDeadzone = 0.05;
        double rotate; // final rotation command sent to drivetrain

        if (Math.abs(rotateRaw) > rotationDeadzone) {
            // Fixed speed: use sign of joystick but fixed magnitude
            rotate = Math.signum(rotateRaw) * rotateRate;
        } else {
            rotate = 0.0;
        }
        return rotate;
    }

//    public double getHeading() {
//        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//    }
//
//    public double headingPID(double targetHeading) {
//        return headingPID.calculate(targetHeading, getHeading());
//    }
//
//    public double calculateHeadingCorrection() {
//        double currentHeading = getHeading();
//
//        double correction = headingPID.calculate(
//                DriveConstants.targetHeading,
//                currentHeading
//        );
//
//        // Clamp to smooth rotation
//        correction = MathUtil.clamp(
//                correction,
//                -DriveConstants.maxHeadingCorrection,
//                DriveConstants.maxHeadingCorrection
//        );
//
//        return correction;
//    }
}