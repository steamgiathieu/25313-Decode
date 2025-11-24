//use to enable robot run autonomously in 30s at the big triangle for blue alliance
package org.firstinspires.ftc.team25313.opmodes.auto;

import static org.firstinspires.ftc.team25313.Constants.AllianceColor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.team25313.Constants;

@Autonomous(name = "Big Triangle Auto for Blue", group = "Auto")
public class BigTriangleAutoforBlue extends MainAuto {
    @Override
    protected void initStartingCondition() {
        allianceColor = Constants.AllianceColor.BLUE;
        startingPos = StartingPos.BIG_TRIANGLE_BLUE;
    }
}

