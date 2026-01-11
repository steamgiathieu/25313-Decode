//use to enable robot run autonomously in 30s at the big triangle for red alliance
package org.firstinspires.ftc.team25313.opmodes.auto;

import static org.firstinspires.ftc.team25313.Constants.AllianceColor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.team25313.Constants;

@Autonomous(name = "Big Triangle Auto for Red", group = "Auto")
public class BigTriangleAutoforRed extends MainAuto {
    @Override
    protected void initStartingCondition() {
        allianceColor = Constants.AllianceColor.red;
        startingPos = StartingPos.BIG_TRIANGLE_RED;
    }
}
