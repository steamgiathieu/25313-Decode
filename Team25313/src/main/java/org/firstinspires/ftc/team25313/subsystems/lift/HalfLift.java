package org.firstinspires.ftc.team25313.subsystems.lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.team25313.Constants;

public class HalfLift {
    private DcMotor lifter;
    public HalfLift(HardwareMap hwMap) {
        lifter = hwMap.get(DcMotor.class, Constants.lifter);
        lifter.setZeroPowerBehavior(com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE);
        lifter.setDirection(DcMotor.Direction.REVERSE);
    }
    public String state = "no";
    public void lift() {
        lifter.setPower(Constants.liftPower);
        state = "up";
    }
    public void pull() {
        lifter.setPower(-Constants.liftPower);
        state = "down";
    }
    public void stop() {
        lifter.setPower(0);
        state = "no";
    }
    public String getLiftState() {
        return state;
    }
}
