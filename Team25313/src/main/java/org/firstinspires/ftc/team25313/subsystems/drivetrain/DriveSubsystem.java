package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem {

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    public DriveSubsystem(HardwareMap hardwareMap) {
        frontLeft  = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRight = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeft   = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRight  = hardwareMap.get(DcMotor.class, "back_right_drive");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
//        frontRight.setDirection(DcMotor.Direction.FORWARD);
//        backRight.setDirection(DcMotor.Direction.FORWARD);
    }

    private static final double deadzone = 0.05;
    private double applyDeadzone(double value) {
        return Math.abs(value) < 0.05 ? 0 : value;
    }

    private double smooth (double x) {
        return x * x * x;
    }

    public void drive(double forward, double strafe, double rotate) {
        forward = smooth(applyDeadzone(forward));
        strafe = smooth(applyDeadzone(strafe));
        rotate = smooth(applyDeadzone(rotate));

        double frontLeftPower = forward + strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;
        double backLeftPower = forward - strafe + rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));

        frontLeft.setPower((maxSpeed * (frontLeftPower / maxPower)) * 0.5);
        frontRight.setPower((maxSpeed * (frontRightPower / maxPower)) * 0.5);
        backLeft.setPower((maxSpeed * (backLeftPower / maxPower)) * 0.5);
        backRight.setPower((maxSpeed * (backRightPower / maxPower)) * 0.5);
    }

    /** Dừng robot */
    public void stop() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }
}