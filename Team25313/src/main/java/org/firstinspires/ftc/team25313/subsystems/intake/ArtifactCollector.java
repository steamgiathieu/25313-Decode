package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArtifactCollector {

    private final DcMotorEx intakeMotor;

    // Tốc độ có thể chỉnh trên dashboard
    public static double INTAKE_POWER = 0.8;
    public static double OUTTAKE_REVERSE_POWER = -0.8;

    public ArtifactCollector(HardwareMap hwMap) {
        intakeMotor = hwMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /** Hút bóng vào robot */
    public void intake() {
        intakeMotor.setPower(INTAKE_POWER);
    }

    /** Alias dùng cho TeleOp — tương đương intake() */
    public void collect() {
        intake();
    }

    /** Đẩy bóng ra ngoài (đảo chiều) */
    public void reverse() {
        intakeMotor.setPower(OUTTAKE_REVERSE_POWER);
    }

    /** Dừng intake */
    public void stop() {
        intakeMotor.setPower(0);
    }
}
