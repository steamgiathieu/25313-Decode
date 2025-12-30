package org.firstinspires.ftc.team25313.commands.outtake;

import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;

public class CallForLaunching {
    private final ArtifactLauncher outtake;
    private boolean started = false;

    public CallForLaunching(ArtifactLauncher outtake) {
        this.outtake = outtake;
    }

    public void init() {
        started = false;
    }

    public void execute() {
        if (!started) {
            if (!outtake.isBusy()) {
                outtake.launch();
                started = true;
            }
        }
    }

    public boolean isFinished() {
        return started && !outtake.isBusy();
    }
}
