package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled

@Autonomous(name = "Far Blue", group = "Auto")
public class FarBlue extends MainAuto {
    @Override
    protected Alliance getAlliance() {
        return Alliance.blue;
    }
    @Override
    protected StartPosition getStartPosition() {
        return StartPosition.far;
    }
}
