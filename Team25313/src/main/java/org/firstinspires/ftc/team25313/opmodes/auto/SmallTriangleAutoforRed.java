package org.firstinspires.ftc.team25313.opmodes.auto;

import static org.firstinspires.ftc.team25313.Constants.AllianceColor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.team25313.Constants;
//import org.firstinspires.ftc.team25313.commands;

@Autonomous(name = "Small Triangle Auto for Red", group = "Auto")
public class SmallTriangleAutoforRed extends MainAuto {
    @Override
    protected void initStartingCondition() {
        allianceColor = Constants.AllianceColor.RED;
        startingPos = StartingPos.SMALL_TRIANGLE_RED;
    }
}