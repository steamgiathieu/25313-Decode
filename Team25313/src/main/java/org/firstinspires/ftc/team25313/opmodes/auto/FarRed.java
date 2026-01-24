package org.firstinspires.ftc.team25313.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled

@Autonomous(name = "Far Red", group = "Auto")
public class FarRed extends MainAuto {
    protected Alliance getAlliance() {
        return Alliance.red;
    }
    @Override
    protected StartPosition getStartPosition() {
        return StartPosition.far;
    }
}