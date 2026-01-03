package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team25313.Constants;

@TeleOp(name = "testdcshooter", group = "TeleOp")
public class testdcshooter extends LinearOpMode {
    DcMotorEx shooter;
    Servo lowPusher;
    Servo highPusher;
    @Override
    public void runOpMode() {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        lowPusher = hardwareMap.get(Servo.class, Constants.lowPusher);
        highPusher = hardwareMap.get(Servo.class, Constants.highPusher);
        highPusher.setDirection(Servo.Direction.REVERSE);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            if (gamepad1.x) {
                shooter.setPower(0.5);
            }
            else if (gamepad1.b) {
                shooter.setPower(0);
            }
            if (gamepad1.right_bumper) {
                lowPusher.setPosition(0.3);
                highPusher.setPosition(0.25);
            }
            else {
                lowPusher.setPosition(0);
                highPusher.setPosition(0);
            }
            telemetry.addData("target rpm", shooter.getVelocity());
            telemetry.update();
        }
    }
}
