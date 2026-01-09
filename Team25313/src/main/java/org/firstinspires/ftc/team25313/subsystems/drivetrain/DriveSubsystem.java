package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.util.MathUtil;
import org.firstinspires.ftc.team25313.Constants;

public class DriveSubsystem {

    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private IMU imu;

    public DriveSubsystem(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, Constants.frontLeft);
        leftBack = hardwareMap.get(DcMotor.class, Constants.backLeft);
        rightFront = hardwareMap.get(DcMotor.class, Constants.frontRight);
        rightBack = hardwareMap.get(DcMotor.class, Constants.backRight);

        // Hầu hết robot FTC cần đảo chiều các motor bên trái
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
     * @param y Tới/Lui (Forward/Back)
     * @param x Sang trái/phải (Strafe)
     * @param rx Xoay (Rotate)
     */
    public void drive(double y, double x, double rx) {
        // Áp dụng deadzone để tránh trôi joystick
        y = MathUtil.applyDeadzone(y);
        x = MathUtil.applyDeadzone(x);
        rx = MathUtil.applyDeadzone(rx);

        // Áp dụng strafeMultiplier để bù đắp ma sát.
        // Giải thích: Bánh Mecanum khi đi ngang (strafe) thường gặp lực cản/ma sát lớn hơn so với đi thẳng hoặc lùi.
        // Điều này làm robot đi ngang chậm hơn dự kiến. Việc nhân với strafeMultiplier (thường > 1.0, ví dụ 1.1) 
        // giúp bù đắp lực này để robot di chuyển theo phương ngang chính xác hơn.
        x = x * DriveConstants.strafeMultiplier;

        // Chuyển đổi sang Field Centric (nếu muốn robot luôn đi theo hướng của người lái)
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Xoay vector đầu vào theo hướng robot
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        // Công thức chuẩn cho Mecanum Drive (Field Centric)
        // frontLeft = y + x + rx
        // backLeft = y - x + rx
        // frontRight = y - x - rx
        // backRight = y + x - rx
        
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        setMotorPower(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    public void setMotorPower(double lfP, double lbP, double rfP, double rbP) {
        // Nhân với multiplier để giới hạn tốc độ nếu cần (hiện tại là 1.0)
        double multi = DriveConstants.normalModeMultiplier;
        leftFront.setPower(lfP * multi);
        leftBack.setPower(lbP * multi);
        rightFront.setPower(rfP * multi);
        rightBack.setPower(rbP * multi);
    }
}
