package org.firstinspires.ftc.team25313.commands;

import org.firstinspires.ftc.team25313.subsystems.outtake.ArtifactLauncher;
import org.firstinspires.ftc.team25313.subsystems.intake.ArtifactCollector;

public class CallForLaunching {

    private final ArtifactLauncher launcher;
    private final ArtifactCollector intake;

    private boolean firingRequested = false;

    public CallForLaunching(ArtifactLauncher launcher,
                            ArtifactCollector intake) {
        this.launcher = launcher;
        this.intake = intake;
    }

    public void fire() {
        if (!launcher.isBusy()) {
            firingRequested = true;
            launcher.fire();
        }
    }

    public void toggleMode() {
        launcher.toggleMode();
    }

    public void stop() {
        firingRequested = false;
        launcher.stop();
        intake.stop();
    }

    public void update() {
        if (launcher.isBusy()) {
            intake.collect();
        } else {
            intake.stop();
            firingRequested = false;
        }

        launcher.update();
    }

    public boolean isBusy() {
        return launcher.isBusy();
    }

//    public ArtifactLauncher.Mode getMode() {
//        return launcher.getMode();
//    }
//
//    public int getShotsFired() {
//        return launcher.getShotsFired();
//    }
//
//    public String getLauncherState() {
//        return launcher.getActionState();
//    }
}
