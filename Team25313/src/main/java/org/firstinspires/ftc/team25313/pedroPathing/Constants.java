package org.firstinspires.ftc.team25313.pedroPathing;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .forwardZeroPowerAcceleration(-35.67160028283289)
            .lateralZeroPowerAcceleration(-68.81319335362801)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0.0, 0.0001, 0.025))
            .headingPIDFCoefficients(new PIDFCoefficients(1.0, 0.0, 0.001,0.03))
            .mass(14.2);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.98,
            100,
            2.2,
            0.8);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(0.5)
            .xVelocity(62.45127447056607)
            .yVelocity(48.021126245009285)
            .rightFrontMotorName("front_right_drive")
            .rightRearMotorName("back_right_drive")
            .leftRearMotorName("back_left_drive")
            .leftFrontMotorName("front_left_drive")
            .leftFrontMotorDirection(DcMotor.Direction.FORWARD)
            .leftRearMotorDirection(DcMotor.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotor.Direction.REVERSE)
            .rightRearMotorDirection(DcMotor.Direction.REVERSE);

    public static TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardEncoder_HardwareMapName("front_left_drive")
            .strafeEncoder_HardwareMapName("front_right_drive")
            .forwardPodY(-3.5/2.54)
            .strafePodX(13/2.54)
            .strafeEncoderDirection(Encoder.REVERSE)
            .forwardTicksToInches(-0.003679)
            .strafeTicksToInches(0.003736)
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                    )
            );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .twoWheelLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}