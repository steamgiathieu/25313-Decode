package org.firstinspires.ftc.team25313.subsystems.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.team25313.FTCObject;
import org.firstinspires.ftc.team25313.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

/**
 * Tự động căn chỉnh robot và tính lực bắn dựa trên khoảng cách đến mục tiêu.
 * - Dữ liệu từ AprilTag (DetectArtifactProcessor)
 * - Di chuyển bằng Drivetrain (PedroPathing hoặc tương đương)
 */
public class AutoAimAssist extends FTCObject {

    private final Drivetrain drivetrain;
    private final DetectArtifactProcessor vision;
    private boolean isActive = false;

    // Hệ số tinh chỉnh cho lực bắn (tùy chỉnh bằng thực nghiệm)
    private static final double SHOOTER_KF = 0.35;
    private static final double MAX_AIM_ERROR_DEG = 2.0;

    public AutoAimAssist(LinearOpMode opMode, Drivetrain drivetrain, DetectArtifactProcessor vision) {
        super(opMode);
        this.drivetrain = drivetrain;
        this.vision = vision;
    }

    /** Kích hoạt hoặc tắt hỗ trợ ngắm */
    public void setActive(boolean active) {
        this.isActive = active;
    }

    /** Gọi trong vòng lặp TeleOp */
    public void update() {
        if (!isActive) return;

        vision.updateDetection();
        AprilTagDetection tag = vision.getLatestDetection();

        if (tag != null) {
            double distance = tag.ftcPose.range; // mét
            double yaw = tag.ftcPose.yaw;         // độ (trái - phải)
            double xError = yaw;                  // góc lệch theo trục ngang

            addData("Target Distance (m)", String.format("%.2f", distance));
            addData("Yaw Error (deg)", String.format("%.2f", xError));

            // Nếu robot đang lệch quá nhiều, xoay lại cho chuẩn
            if (Math.abs(xError) > MAX_AIM_ERROR_DEG) {
                double turnPower = xError * 0.02; // hệ số quay
                drivetrain.setDrivePower(-turnPower, turnPower, -turnPower, turnPower);
            } else {
                drivetrain.stop();
            }

            // Tính toán lực bắn phù hợp với khoảng cách
            double shooterPower = calculateShooterPower(distance);
            addData("Shooter Power", String.format("%.2f", shooterPower));
        } else {
            addData("Target", "Not found");
        }
        update();
    }

    /** Hàm ước lượng lực bắn (có thể tinh chỉnh qua thực nghiệm) */
    private double calculateShooterPower(double distanceMeters) {
        // Ví dụ: khoảng cách 1.0m -> 0.45 power, 2.0m -> 0.7 power
        double basePower = SHOOTER_KF * distanceMeters + 0.1;
        return Math.min(1.0, Math.max(0.2, basePower)); // Giới hạn từ 0.2–1.0
    }

    /** Ngừng hỗ trợ và dừng drivetrain */
    public void stop() {
        drivetrain.stop();
    }
}
