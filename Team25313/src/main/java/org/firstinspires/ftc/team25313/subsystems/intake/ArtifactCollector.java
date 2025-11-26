package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArtifactCollector {

    private final DcMotor intakeMotor;

    public static double INTAKE_POWER = 0.8;
    public static double INTAKE_REVERSE_POWER = -0.8;

    public ArtifactCollector(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    }

    /** Hút bóng vào robot */
    public void collect() {
        intakeMotor.setPower(INTAKE_POWER);
    }

    /** Đẩy bóng ra ngoài (đảo chiều) */
    public void reverse() {
        intakeMotor.setPower(INTAKE_REVERSE_POWER);
    }

    /** Dừng intake */
    public void stop() {
        intakeMotor.setPower(0);
    }
}
