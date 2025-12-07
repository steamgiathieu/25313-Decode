package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactCollector {
    private DcMotor intake;
    private static final double intakeIn = Constants.intakeIn;
    private static final double intakeOut = Constants.intakeOut;

    public ArtifactCollector(HardwareMap hwmap) {
        intake = hwmap.get(DcMotor.class, Constants.intake);
    }
    public void collect () {
        intake.setPower(intakeIn);
    }

    public void reverse () {
        intake.setPower(intakeOut);
    }

    public void stop() {
        intake.setPower(0);
    }
}
