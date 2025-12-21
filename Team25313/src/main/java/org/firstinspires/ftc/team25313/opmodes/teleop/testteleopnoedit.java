package org.firstinspires.ftc.team25313.opmodes.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "testteleopnoedit (Blocks to Java)")
class testteleopnoedit extends LinearOpMode {

    private DcMotor front_left_drive;
    private DcMotor front_right_drive;
    private DcMotor back_left_drive;
    private DcMotor back_right_drive;

    /**
     * This function is executed when this Op Mode is selected.
     */
    @Override
    public void runOpMode() {
        int k;
        float forwardBack;
        float strafe;
        float turn;
        float leftFrontPower;
        float rightFrontPower;
        float leftBackPower;
        float rightBackPower;

        front_left_drive = hardwareMap.get(DcMotor.class, "front_left_drive");
        front_right_drive = hardwareMap.get(DcMotor.class, "front_right_drive");
        back_left_drive = hardwareMap.get(DcMotor.class, "back_left_drive");
        back_right_drive = hardwareMap.get(DcMotor.class, "back_right_drive");

        // Put initialization blocks here.
        front_left_drive.setDirection(DcMotor.Direction.REVERSE);
        front_right_drive.setDirection(DcMotor.Direction.FORWARD);
        back_left_drive.setDirection(DcMotor.Direction.REVERSE);
        back_right_drive.setDirection(DcMotor.Direction.FORWARD);
        k = 1;
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                forwardBack = gamepad1.left_stick_y;
                strafe = -1 * gamepad1.left_stick_x;
                turn = -1 * gamepad1.right_stick_x;
                leftFrontPower = k * (forwardBack + strafe + turn);
                rightFrontPower = k * ((forwardBack - strafe) - turn);
                leftBackPower = k * ((forwardBack - strafe) + turn);
                rightBackPower = k * ((forwardBack + strafe) - turn);
                // Setting Motor Power
                front_left_drive.setPower(leftFrontPower);
                front_right_drive.setPower(rightFrontPower);
                back_left_drive.setPower(leftBackPower);
                back_right_drive.setPower(rightBackPower);
            }
        }
    }
}