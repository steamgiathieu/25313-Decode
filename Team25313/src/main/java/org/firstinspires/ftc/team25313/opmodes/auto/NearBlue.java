package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Near Blue", group = "Auto")
public class NearBlue extends MainAutoNear {
    @Override
    protected Alliance getAlliance() {
        return Alliance.blue;
    }
    @Override
    protected StartPosition getStartPosition() {
        return StartPosition.near;
    }
}
