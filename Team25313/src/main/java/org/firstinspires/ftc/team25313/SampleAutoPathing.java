package org.firstinspires.ftc.team25313;

import com.google.firebase.perf.util.Timer;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class SampleAutoPathing extends  OpMode {
    private Follower follower;

    private Timer pathTimer, opModeTimer;

    public enum PathState {
        // START POSITION END POSITION
        // DRIVE > MOVEMOVENT STATE
        // SHOOT > ATTEMPT TO SCORE THE ARTIFACT

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
