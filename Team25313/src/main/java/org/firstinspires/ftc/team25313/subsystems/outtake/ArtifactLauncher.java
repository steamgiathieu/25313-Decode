package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArtifactLauncher {

    private final DcMotor leftShooter, rightShooter;
    private final Servo ShooterAssistant;

    // 4 mức lực bắn
    private final double[] SHOOTER_LEVELS = {0.1, 0.2, 0.3, 0.4};
    private int currentLevelIndex = 0;

    public static double assistantRest = 0.0;
    public static double assistantPush = 0.35;


    public ArtifactLauncher(HardwareMap hwMap) {
        leftShooter = hwMap.get(DcMotor.class, "leftShooter");
        rightShooter = hwMap.get(DcMotor.class, "rightShooter");
        ShooterAssistant = hwMap.get(Servo.class, "ShooterAssistant");

        leftShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        rightShooter.setDirection(DcMotor.Direction.REVERSE);
        ShooterAssistant.setPosition(assistantRest);
    }

    /** Bật shooter với mức lực hiện tại */
    public void startShooter() {
        double power = SHOOTER_LEVELS[currentLevelIndex];
        leftShooter.setPower(power);
        rightShooter.setPower(power);
    }

    /** Tắt shooter */
    public void stopShooter() {
        leftShooter.setPower(0);
        rightShooter.setPower(0);
    }

    /** Đẩy bóng vào shooter */
    public void push() {
        ShooterAssistant.setPosition(assistantPush);
    }

    /** Thu servo về vị trí nghỉ */
    public void rest() {
        ShooterAssistant.setPosition(assistantRest);
    }

    /** Tăng mức lực bắn → nếu vượt mức 3 thì quay về mức 0 */
    /** Áp dụng mức lực hiện tại nếu shooter đang bật */
    public void applyCurrentPowerLevel() {
        double p = SHOOTER_LEVELS[currentLevelIndex];
        if (leftShooter.getPower() > 0.05 || rightShooter.getPower() > 0.05) {
            leftShooter.setPower(p);
            rightShooter.setPower(p);
        }
    }

    /** Lấy mức lực hiện tại */
    public double getCurrentPowerLevel() {
        return SHOOTER_LEVELS[currentLevelIndex];
    }

    /** Lấy index mức lực (0–3) */
    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }
    public void increasePowerLevel() {
        currentLevelIndex++;
        if (currentLevelIndex > 3) currentLevelIndex = 3;
        // Nếu shooter đang chạy → cập nhật luôn power
        applyCurrentPowerLevel();
    }

    public void decreasePowerLevel () {
        currentLevelIndex--;
        if (currentLevelIndex < 0) currentLevelIndex = 0;
        applyCurrentPowerLevel();
    }
}
