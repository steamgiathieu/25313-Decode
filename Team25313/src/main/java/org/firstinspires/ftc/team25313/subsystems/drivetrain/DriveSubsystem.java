package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.Utility;

public class DriveSubsystem {

    public DcMotor leftFront, leftBack, rightFront, rightBack;
    private IMU imu;

    public DriveSubsystem(HardwareMap hardwareMap) {

        leftFront = hardwareMap.get(DcMotor.class, Constants.frontLeft);
        leftBack = hardwareMap.get(DcMotor.class, Constants.backLeft);
        rightFront = hardwareMap.get(DcMotor.class, Constants.frontRight);
        rightBack = hardwareMap.get(DcMotor.class, Constants.backRight);

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
    }

    public double frontLeftPower;
    public double frontRightPower;
    public double backLeftPower;
    public double backRightPower;

    public void driveRobotRelated(double forward, double strafe, double rotate) {
        forward = Utility.applyDeadzone(forward);
        strafe = Utility.applyDeadzone(strafe);
        rotate = Utility.applyDeadzone(rotate);

        frontLeftPower = forward + strafe + rotate;
        frontRightPower = forward - strafe - rotate;
        backLeftPower = forward - strafe + rotate;
        backRightPower = forward + strafe - rotate;

        double min = -0.5;
        double max = 0.5;

        frontLeftPower = Utility.clamp(frontLeftPower, min, max);
        frontRightPower = Utility.clamp(frontRightPower, min, max);
        backLeftPower = Utility.clamp(backLeftPower, min, max);
        backRightPower = Utility.clamp(backRightPower, min, max);

        setMotorPower(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    public void driveFieldRelated(double forward, double strafe, double rotate) {
        forward = Utility.applyDeadzone(forward);
        strafe = Utility.applyDeadzone(strafe);
        rotate = Utility.applyDeadzone(rotate);

        double robotAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(forward, strafe);
        theta = AngleUnit.normalizeRadians(theta - robotAngle);

        forward = r * Math.sin(theta);
        strafe = r * Math.cos(theta);

        double frontLeftPower = forward + strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
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
}