package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.lift.Lift;
import org.firstinspires.ftc.team25313.subsystems.vision.Camera;
import org.firstinspires.ftc.team25313.Constants;
import org.firstinspires.ftc.team25313.Ultility;

@TeleOp(name = "MainTeleOp", group = "Main")
public class MainTeleOp extends LinearOpMode {

    // --- Subsystems ---
    private Drivetrain drivetrain;
    private ArtifactCollector intake;
    private ArtifactLauncher outtake;
    private Lift lift;
    private Camera camera; // nếu có vision cho TeleOp

    @Override
    public void runOpMode() throws InterruptedException {

        // =============== INIT ===============
        drivetrain = new Drivetrain(hardwareMap);
        intake = new ArtifactCollector(hardwareMap);
        outtake = new ArtifactLauncher(hardwareMap);
        lift = new Lift(hardwareMap);

        if (Constants.USE_VISION_IN_TELEOP) {
            camera = new Camera();
            camera.init(hardwareMap);
        }

        telemetry.addLine("TeleOp Ready");
        telemetry.update();

        waitForStart();

        // =============== MAIN LOOP ===============
        while (opModeIsActive()) {

            // ====================
            // 1) DRIVETRAIN CONTROL
            // ====================
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            // xử lý deadband
            drive = Ultility.applyDeadband(drive, Constants.DEADBAND);
            strafe = Ultility.applyDeadband(strafe, Constants.DEADBAND);
            turn = Ultility.applyDeadband(turn, Constants.DEADBAND);

            // scale speed
            if (gamepad1.left_bumper) {
                drivetrain.setSlowMode(true);
            } else if (gamepad1.right_bumper) {
                drivetrain.setTurboMode(true);
            } else {
                drivetrain.setNormalMode();
            }

            drivetrain.drive(drive, strafe, turn);


            // ====================
            // 2) INTAKE CONTROL
            // ====================
            if (gamepad2.right_trigger > 0.3) {
                intake.collect();
            } else if (gamepad2.left_trigger > 0.3) {
                intake.reverse();
            } else {
                intake.stop();
            }


            // ====================
            // 3) LIFT CONTROL
            // ====================
            if (gamepad2.dpad_up) {
                lift.up();
            } else if (gamepad2.dpad_down) {
                lift.down();
            } else {
                lift.hold();
            }


            // ====================
            // 4) OUTTAKE CONTROL
            // ====================
            if (gamepad2.a) {
                outtake.load();
            }
            if (gamepad2.b) {
                outtake.fire();
            }
            if (gamepad2.x) {
                outtake.reset();
            }


            // ====================
            // 5) TELEMETRY
            // ====================
            telemetry.addLine("=== DRIVETRAIN ===");
            telemetry.addData("Drive", drive);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Turn", turn);

            telemetry.addLine("=== LIFT ===");
            telemetry.addData("Pos", lift.getPosition());

            telemetry.addLine("=== INTAKE ===");
            telemetry.addData("Status", intake.getState());

            telemetry.addLine("=== OUTTAKE ===");
            telemetry.addData("Load State", outtake.getState());

            telemetry.update();

        } // end while
    } // end runOpMode
}
