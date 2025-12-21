package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactCollector {
    private DcMotor intakeMotor;
    private CRServo intakeServo;
    private CRServo leftPusher;
    private CRServo rightPusher;


    public ArtifactCollector(HardwareMap hwmap) {
        intakeMotor = hwmap.get(DcMotor.class, Constants.intakeMotor);
        intakeServo = hwmap.get(CRServo.class, Constants.intakeServo);
        leftPusher = hwmap.get(CRServo.class, Constants.leftPusher);
        rightPusher = hwmap.get(CRServo.class, Constants.rightPusher);
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightPusher.setDirection(CRServo.Direction.REVERSE);
    }

    private boolean running = false;
    public void collect () {

        intakeMotor.setPower(Constants.intakeMotorIn);
        intakeServo.setPower(Constants.intakeServoIn);
        leftPusher.setPower(Constants.intakeServoIn);
        rightPusher.setPower(Constants.intakeServoIn);
        running = true;
    }
//
//    public void reverse () {
//        intakeMotor.setPower(intakeOut);
//    }

    public void stop() {
        intakeMotor.setPower(0);
        intakeServo.setPower(0);
        leftPusher.setPower(0);
        rightPusher.setPower(0);
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
