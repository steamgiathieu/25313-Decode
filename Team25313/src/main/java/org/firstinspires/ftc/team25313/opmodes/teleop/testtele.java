package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "testtele")
public class testtele extends LinearOpMode {

    private ServoController ControlHub_ServoController;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                ControlHub_ServoController.pwmEnable();
                telemetry.addData("servo bruh bruh lmao", ControlHub_ServoController.getPwmStatus());
                telemetry.update();
                sleep(5000);
                ControlHub_ServoController.pwmDisable();
                sleep(5000);
            }
        }
    }
}
