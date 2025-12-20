package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactCollector {
    private DcMotor intakeMotor;
    private CRServo intakeServo;

    public ArtifactCollector(HardwareMap hwmap) {
        intakeMotor = hwmap.get(DcMotor.class, Constants.intakeMotor);
        intakeServo = hwmap.get(CRServo.class, Constants.intakeServo);
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private boolean running = false;
    public void collect () {

        intakeMotor.setPower(Constants.intakeMotorIn);
        intakeServo.setPower(Constants.intakeServoIn);
        running = true;
    }
//
//    public void reverse () {
//        intakeMotor.setPower(intakeOut);
//    }

    public void stop() {
        intakeMotor.setPower(0);
        intakeServo.setPower(0);
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
