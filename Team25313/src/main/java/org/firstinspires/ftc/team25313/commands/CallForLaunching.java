package org.firstinspires.ftc.team25313.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

public class CallForLaunching {

    private final ArtifactLauncher outtake;
    private final Gamepad gamepad;

    // prev states (edge detect)
    private boolean prevShoot = false;
    private boolean prevToggleMode = false;
    private boolean prevEnable = false;
    private boolean prevDisable = false;
    private Runnable intakeOn = null;
    private Runnable intakeOff = null;
    public void setIntakeCallbacks(Runnable on, Runnable off) {
        intakeOn = on;
        intakeOff = off;
    }
    public CallForLaunching(ArtifactLauncher outtake, Gamepad gamepad) {
        this.outtake = outtake;
        this.gamepad = gamepad;
    }

    public void update() {
        if (gamepad.x && !prevEnable) {
            outtake.enableLauncher();
        }

        if (gamepad.b && !prevDisable) {
            outtake.disableLauncher();
        }

        if (gamepad.left_bumper && !prevToggleMode) {
            outtake.toggleMode();
        }

        if (gamepad.dpad_down) {
            outtake.setBasePower();
        }

        if (gamepad.dpad_up) {
            outtake.setGoalPower();
        }

        if (gamepad.right_bumper && !prevShoot) {
            outtake.shoot();
        }

        if (gamepad.a) {
            outtake.stopShooter();
        }

        prevShoot = gamepad.right_bumper;
        prevToggleMode = gamepad.left_bumper;
        prevEnable = gamepad.x;
        prevDisable = gamepad.b;
    }
}
