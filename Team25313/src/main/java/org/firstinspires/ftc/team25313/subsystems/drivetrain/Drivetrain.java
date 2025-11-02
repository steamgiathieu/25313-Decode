package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import pedropathing.localization.Pose;
import pedropathing.localization.StandardTrackingWheelLocalizer;
import pedropathing.motion.PedroMecanumDrive;

/**
 * Hệ thống truyền động Mecanum 4 bánh, sử dụng PedroPathing
 * Có hỗ trợ điều khiển field-centric, robot-centric và tự hành bằng Pose.
 */
@Config
public class Drivetrain {
    private final DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private final IMU imu;
    private final PedroMecanumDrive drive;

    public Drivetrain(HardwareMap hwMap) {
        frontLeft  = hwMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        backLeft   = hwMap.get(DcMotorEx.class, "backLeft");
        backRight  = hwMap.get(DcMotorEx.class, "backRight");

        imu = hwMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        ));

        // Đặt chiều động cơ
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Chế độ phanh khi thả tay
        for (DcMotorEx motor : new DcMotorEx[]{frontLeft, frontRight, backLeft, backRight}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // Tạo đối tượng PedroMecanumDrive
        drive = new PedroMecanumDrive(
                frontLeft, frontRight, backLeft, backRight,
                new StandardTrackingWheelLocalizer(hwMap),
                imu
        );
    }

    public void driveFieldCentric(double x, double y, double turn) {
        drive.driveFieldCentric(x, y, turn);
    }

    public void driveRobotCentric(double x, double y, double turn) {
        drive.driveRobotCentric(x, y, turn);
    }

    // ============================================================
    // ==  CÁC CHỨC NĂNG ĐIỀU KHIỂN TỰ ĐỘNG
    // ============================================================
    /** Di chuyển đến một pose tuyệt đối (toạ độ toàn cục) */
    public void driveToPose(Pose targetPose, double maxPower) {
        drive.goToPose(targetPose, maxPower);
    }

    /** Xoay đến góc mong muốn (radian) */
    public void turnToAngle(double targetHeading) {
        drive.turnTo(targetHeading);
    }

    /** Di chuyển theo hướng tương đối hiện tại */
    public void moveRelative(double xInches, double yInches, double headingRad) {
        Pose current = drive.getPose();
        Pose target = new Pose(
                current.getX() + xInches,
                current.getY() + yInches,
                current.getHeading() + headingRad
        );
        drive.goToPose(target);
    }

    // ============================================================
    // ==  POSE & IMU
    // ============================================================
    public Pose getPose() {
        return drive.getPose();
    }

    public void setPose(Pose pose) {
        drive.setPose(pose);
    }

    public double getHeading() {
        return drive.getPose().getHeading();
    }

    public double getHeadingDeg() {
        return Math.toDegrees(drive.getPose().getHeading());
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    // ============================================================
    // ==  VÒNG LẶP CẬP NHẬT
    // ============================================================
    public void update() {
        drive.update();
    }
}
