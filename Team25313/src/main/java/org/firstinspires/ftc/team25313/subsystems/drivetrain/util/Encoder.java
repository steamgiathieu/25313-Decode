package org.firstinspires.ftc.team25313.subsystems.util;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Encoder {
    private final DcMotorEx motor;
    private double ticksToInches;
    private DcMotorSimple.Direction direction = DcMotorSimple.Direction.FORWARD;

    public Encoder(DcMotorEx motor, double ticksToInches) {
        this.motor = motor;
        this.ticksToInches = ticksToInches;
    }

    public double getCurrentPosition() {
        int pos = motor.getCurrentPosition();
        return (direction == DcMotorSimple.Direction.REVERSE ? -pos : pos) * ticksToInches;
    }

    public double getVelocity() {
        double vel = motor.getVelocity();
        return (direction == DcMotorSimple.Direction.REVERSE ? -vel : vel) * ticksToInches;
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        this.direction = direction;
    }

    public void reset() {
        motor.setMode(com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
