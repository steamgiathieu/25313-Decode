package org.firstinspires.ftc.team25313.opmodes.auto;

import static org.firstinspires.ftc.team25313.Constants.AllianceColor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.team25313.Constants;
//import org.firstinspires.ftc.team25313.commands;

@Autonomous(name = "Big Triangle Auto for Blue", group = "Auto")
public class BigTriangleAutoforBlue extends MainAuto {
    @Override
    protected void initStartingCondition() {
        allianceColor = Constants.AllianceColor.BLUE;
        startingPos = StartingPos.BIG_TRIANGLE_BLUE;
    }
}

