package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team25313.subsystems.drivetrain.DriveSubsystem;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.sensor.REVDistanceSensor;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {
    // Subsystems
    private DriveSubsystem driveSubsystem;
//    private ArtifactCollector intake;
//    private ArtifactLauncher outtake;
//    private REVDistanceSensor sensorDistance;

    // button edge detection

    private boolean UpPrev = false;
    private boolean DownPrev = false;

    @Override
    public void runOpMode() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
//        intake = new ArtifactCollector(hardwareMap);
//        outtake = new ArtifactLauncher(hardwareMap);
//        sensorDistance = new REVDistanceSensor(hardwareMap);

        telemetry.addLine("Initialized Main TeleOp!");
        waitForStart();

        // =============================================
        // DRIVETRAIN CONTROL (Robot-Centric)
        // =============================================
        while (opModeIsActive()) {
            double forward = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double rotate = -gamepad1.right_stick_x;

            driveSubsystem.drive(forward, strafe, rotate);


            // =============================================
            // INTAKE CONTROL
            // =============================================
//            if (gamepad1.y) intake.collect();
//            else if (gamepad1.a) intake.reverse();
//            else intake.stop();

            // =============================================
            // LAUNCHER CONTROL
            // =============================================

//            if (gamepad1.x) outtake.startShooter();
//            else if (gamepad1.b) outtake.stopShooter();
//
//            boolean Up = gamepad1.dpad_up;
//            boolean Down = gamepad1.dpad_down;
//            if (Up && !UpPrev) {
//                outtake.increasePowerLevel();
//            }
//            else if (Down && !DownPrev) {
//                outtake.decreasePowerLevel();
//            }
//            UpPrev = Up;
//            DownPrev = Down;
//
//            // Servo
//            if (gamepad1.left_bumper) outtake.push();
//            else outtake.rest();
//
//            // Sensor
//            double Data = sensorDistance.GetData();

            // =============================================
            // TELEMETRY
            // =============================================
            telemetry.addLine("=== Posterboy's Telemetry ===");
            telemetry.addData("Y", forward);
            telemetry.addData("X", strafe);
            telemetry.addData("Z", rotate);
//            telemetry.addData("Shooter Power", outtake.getCurrentPowerLevel());
//            telemetry.addData("Intake Mode",
//                    gamepad1.right_bumper ? "COLLECT" :
//                            gamepad1.left_bumper ? "REVERSE" : "STOP");
//            telemetry.addData("Distance", "%.1f", Data);
            telemetry.update();
        }
    }
}