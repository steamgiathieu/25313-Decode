package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.control.HeadingPID;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.control.SlewRateLimiter;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.util.MathUtil;

public class DriveSubsystem {

    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private IMU imu;

    private final SlewRateLimiter xLimiter;
    private final SlewRateLimiter yLimiter;
    private final SlewRateLimiter turnLimiter;

    private final HeadingPID headingPID;

    public DriveSubsystem(HardwareMap hardwareMap) {

        leftFront = hardwareMap.get(DcMotor.class, "front_left_drive");
        leftBack = hardwareMap.get(DcMotor.class, "back_left_drive");
        rightFront = hardwareMap.get(DcMotor.class, "front_right_drive");
        rightBack = hardwareMap.get(DcMotor.class, "back_right_drive");

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters myIMUparameters;
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(myIMUparameters);

        // Control classes
        xLimiter = new SlewRateLimiter(DriveConstants.maxAccelStrafe);
        yLimiter = new SlewRateLimiter(DriveConstants.maxAccelForward);
        turnLimiter = new SlewRateLimiter(DriveConstants.maxAccelTurn);

        headingPID = new HeadingPID(
                DriveConstants.headingP,
                DriveConstants.headingI,
                DriveConstants.headingD
        );
    }

    public void drive(double x, double y, double turn) {
        // Apply deadzone
        x = MathUtil.applyDeadzone(x);
        y = MathUtil.applyDeadzone(y);
        turn = MathUtil.applyDeadzone(turn);

        // Smooth movement with slew rate limiters
        x = xLimiter.calculate(x);
        y = yLimiter.calculate(y);
        turn = turnLimiter.calculate(turn);

        if (Math.abs(turn) < 0.02) {  // if rotate was not made by user
            double correction = calculateHeadingCorrection();
            turn = correction;         // use PID to correct heading
        } else { // rotate manually
            // Update heading
            DriveConstants.targetHeading = getHeading();
            headingPID.reset();
        }

        double theta = Math.atan2(y, x);
        double power = Math.hypot(x, y);

        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFrontPower = power * cos/max + turn;
        double rightFrontPower = power * sin/max - turn;
        double leftBackPower = power * sin/max + turn;
        double rightBackPower = power * cos/max - turn;

        if ((power + Math.abs(turn)) > 1) {
            leftFrontPower /= power + turn;
            rightFrontPower /= power + turn;
            leftBackPower /= power + turn;
            rightBackPower /= power + turn;
        }

        setMotorPower(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
    }

    public void setMotorPower(double lfP, double lbP, double rfP, double rbP) {
        leftFront.setPower(lfP);
        leftBack.setPower(lbP);
        rightFront.setPower(rfP);
        rightBack.setPower(rbP);
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public double headingPID(double targetHeading) {
        return headingPID.calculate(targetHeading, getHeading());
    }

    public double calculateHeadingCorrection() {
        double currentHeading = getHeading();

        double correction = headingPID.calculate(
                DriveConstants.targetHeading,
                currentHeading
        );

        // Clamp to smooth rotation
        correction = MathUtil.clamp(
                correction,
                -DriveConstants.maxHeadingCorrection,
                DriveConstants.maxHeadingCorrection
        );

        return correction;
    }
}
