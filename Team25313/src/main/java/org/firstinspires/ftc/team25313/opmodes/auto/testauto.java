package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team25313.subsystems.vision.VisionSubsystem;

@Autonomous(name = "testauto", group = "Autonomous")
public class testauto extends LinearOpMode {

    private VisionSubsystem visionSubsystem;

    @Override
    public void runOpMode() {
        // Get the webcam from the hardware map
        WebcamName webcam = hardwareMap.get(WebcamName.class, "Webcam 1");

        // Initialize the vision subsystem
        visionSubsystem = new VisionSubsystem(webcam, telemetry); // Pass telemetry here

        telemetry.addData("Status", "Initializing Vision Subsystem...");
        telemetry.update();

        waitForStart();

        // Initialize the camera and vision system
        if (!visionSubsystem.initialize()) {
            telemetry.addData("Error", "Camera initialization failed!");
            telemetry.update();
            return; // Stop the OpMode if initialization fails
        }

        telemetry.addData("Status", "Camera Initialized!");
        telemetry.update();

        // Loop until the OpMode is stopped
        while (opModeIsActive()) {
            visionSubsystem.processFrame();
            telemetry.addData("Vision Status", "Processing frame...");
            telemetry.update();
        }

        visionSubsystem.close();
        telemetry.addData("Status", "Vision subsystem stopped.");
        telemetry.update();
    }
}
