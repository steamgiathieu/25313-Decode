package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

public class ArtifactCollector {
    private final DcMotorEx collector;
    private final CRServo leftCollector;
    private final CRServo rightCollector;


    public enum IntakeMode {
        idle,
        manualCollect,
        manualReverse,
        outtakeFeed
    }

    private IntakeMode mode = IntakeMode.idle;

    public ArtifactCollector(HardwareMap hw) {
        collector = hw.get(DcMotorEx.class, Constants.collector);
        collector.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftCollector = hw.get(CRServo.class, Constants.leftCollector);
        rightCollector = hw.get(CRServo.class, Constants.rightCollector);
        leftCollector.setDirection(CRServo.Direction.REVERSE);
    }

    public void setManualCollect() {
        mode = IntakeMode.manualCollect;
    }

    public void setManualReverse() {
        mode = IntakeMode.manualReverse;
    }

    public void setOuttakeFeed() {
        mode = IntakeMode.outtakeFeed;
    }

    public void stop() {
        mode = IntakeMode.idle;
    }

    public boolean isOuttakeFeeding() {
        return mode == IntakeMode.outtakeFeed;
    }

    public void update() {
        switch (mode) {

            case manualCollect:
                collector.setPower(Constants.intakeMotorIn);
                leftCollector.setPower(Constants.intakeServoIn);
                rightCollector.setPower(Constants.intakeServoIn);
                break;

            case manualReverse:
                collector.setPower(-(Constants.intakeMotorIn));
                leftCollector.setPower(-Constants.intakeServoIn);
                rightCollector.setPower(-Constants.intakeServoIn);
                break;

            case outtakeFeed:
                collector.setPower(Constants.intakeMotorShoot);
                break;

            case idle:
            default:
                collector.setPower(0);
                leftCollector.setPower(0);
                rightCollector.setPower(0);
                break;
        }
    }
}
