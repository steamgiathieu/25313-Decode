package org.firstinspires.ftc.team25313.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArtifactLauncher {

    private final DcMotor leftShooter, rightShooter;
    private final Servo ShooterAssistant;

    private double Power = 0.3;
    private double delta = 0.01;

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
        leftShooter.setPower(Power);
        rightShooter.setPower(Power);
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
    public void rest() {
        ShooterAssistant.setPosition(assistantRest);
    }

    /** Tăng mức lực bắn → nếu vượt mức 3 thì quay về mức 0 */
    /** Áp dụng mức lực hiện tại nếu shooter đang bật */
    public void applyCurrentPowerLevel() {
        double p = Power;
        if (leftShooter.getPower() > 0.05 || rightShooter.getPower() > 0.05) {
            leftShooter.setPower(p);
            rightShooter.setPower(p);
        }
    }
    /** Lấy mức lực hiện tại */
    public double getCurrentPowerLevel() {
        return Power;
    }
    public void increasePowerLevel() {
        Power += delta;
        applyCurrentPowerLevel();
    }

    public void decreasePowerLevel () {
        Power -= delta;
        applyCurrentPowerLevel();
    }
}
