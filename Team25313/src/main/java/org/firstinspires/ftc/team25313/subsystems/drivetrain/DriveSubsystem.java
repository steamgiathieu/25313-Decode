package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.util.MathUtil;
import org.firstinspires.ftc.team25313.Constants;

public class DriveSubsystem {

    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    private final IMU imu;

    public DriveSubsystem(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, Constants.frontLeft);
        leftBack = hardwareMap.get(DcMotor.class, Constants.backLeft);
        rightFront = hardwareMap.get(DcMotor.class, Constants.frontRight);
        rightBack = hardwareMap.get(DcMotor.class, Constants.backRight);

        // Đảo chiều motor bên trái - Cấu hình chuẩn cho Mecanum
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(myIMUparameters);
    }

    /**
     * @param y Tiến (+), Lùi (-)
     * @param x Sang phải (+), Sang trái (-)
     * @param rx Xoay phải (+), Xoay trái (-)
     */
    public void drive(double y, double x, double rx) {
        y = MathUtil.applyDeadzone(y);
        x = MathUtil.applyDeadzone(x);
        rx = MathUtil.applyDeadzone(rx);

        // Tạm thời bỏ Field Centric để kiểm tra Robot Centric
        // Nếu robot đi đúng hướng ở đây, nghĩa là IMU đang bị ngược
        double strafe = x * DriveConstants.strafeMultiplier;
        double forward = y;

        // Công thức Mecanum chuẩn
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rx), 1.0);
        double frontLeftPower = (forward + strafe + rx) / denominator;
        double backLeftPower = (forward - strafe + rx) / denominator;
        double frontRightPower = (forward - strafe - rx) / denominator;
        double backRightPower = (forward + strafe - rx) / denominator;

        setMotorPower(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    public void setMotorPower(double lfP, double lbP, double rfP, double rbP) {
        double multi = DriveConstants.normalModeMultiplier;
        leftFront.setPower(lfP * multi);
        leftBack.setPower(lbP * multi);
        rightFront.setPower(rfP * multi);
        rightBack.setPower(rbP * multi);
    }
}
