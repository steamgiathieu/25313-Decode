package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactCollector {
    private final DcMotorEx collector;

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
                break;

            case manualReverse:
                collector.setPower(-(Constants.intakeMotorIn));
                break;

            case outtakeFeed:
                collector.setPower(Constants.intakeMotorIn);
                break;

            case idle:
            default:
                collector.setPower(0);
                break;
        }
    }
}
