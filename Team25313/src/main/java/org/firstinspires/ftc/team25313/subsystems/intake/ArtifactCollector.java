package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactCollector {
    private CRServo intake;
    private static final double intakeIn = Constants.intakeIn;
    private static final double intakeOut = Constants.intakeOut;

    public ArtifactCollector(HardwareMap hwmap) {
        intake = hwmap.get(CRServo.class, "intakeServo");
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
