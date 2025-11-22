package org.firstinspires.ftc.team25313.subsystems.drivetrain;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.pedropathing.Drivetrain;
import com.pedropathing.geometry.Pose;
import com.pedropathing.localization.Localizer;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.util.ThreeWheelLocalizer;
import org.firstinspires.ftc.team25313.Constants;

@Config
public class DriveSubsystem {

    private Drivetrain drive;
    private Localizer localizer;

    public DriveSubsystem(HardwareMap hardwareMap) {

        // Tạo drivetrain từ PedroPathing
        drive = new Drivetrain(
                hardwareMap,
                DriveConstants.WHEEL_DIAMETER,
                DriveConstants.TICKS_PER_REV,
                DriveConstants.GEAR_RATIO,
                DriveConstants.TRACK_WIDTH
        );

        // Gán localizer 3 bánh của bạn
        localizer = new ThreeWheelLocalizer(hardwareMap);
        drive.setLocalizer(localizer);

        // Đặt pose bắt đầu
        drive.setPoseEstimate(new Pose(0, 0, 0));
    }

    public void update() {
        drive.update();
    }

    public Pose getPose() {
        return drive.getPose();
    }

    public void setWeightedDrivePower(double x, double y, double turn) {
        drive.setWeightedDrivePower(x, y, turn);
    }

    public Drivetrain getDrive() {
        return drive;
    }
}
