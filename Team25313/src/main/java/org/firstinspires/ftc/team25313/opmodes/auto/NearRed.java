package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Near Red", group = "Auto")
public class NearRed extends MainAutoNear {
    @Override
    protected Alliance getAlliance() {
        return Alliance.red;
    }
    @Override
    protected StartPosition getStartPosition() {
        return StartPosition.near;
    }
}
