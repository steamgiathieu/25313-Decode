package org.firstinspires.ftc.team25313.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

public class CallForLaunching {

    private final ArtifactLauncher launcher;

    private boolean lastX, lastB, lastRB, lastLB, lastDpadUp, lastDpadDown;

    public CallForLaunching(ArtifactLauncher launcher) {
        this.launcher = launcher;
    }

    public void update(Gamepad gp) {

        // spin up
        if (gp.x && !lastX) {
            launcher.spinUp();
        }

        // stop
        if (gp.b && !lastB) {
            launcher.stop();
        }

        // fire
        if (gp.right_bumper && !lastRB) {
            launcher.requestFire();
        }

        // toggle fire mode
        if (gp.left_bumper && !lastLB) {
            launcher.toggleFireMode();
        }

        // select power
        if (gp.dpad_up && !lastDpadUp) {
            launcher.setGoalPower();
        }

        if (gp.dpad_down && !lastDpadDown) {
            launcher.setBasePower();
        }

        // save states
        lastX = gp.x;
        lastB = gp.b;
        lastRB = gp.right_bumper;
        lastLB = gp.left_bumper;
        lastDpadUp = gp.dpad_up;
        lastDpadDown = gp.dpad_down;
    }
}
