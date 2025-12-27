package org.firstinspires.ftc.team25313.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.Constants;

public class ArtifactCollector {
    private DcMotor collector;


    public ArtifactCollector(HardwareMap hwmap) {
        collector = hwmap.get(DcMotor.class, Constants.collector);
        collector.setDirection(DcMotor.Direction.REVERSE);
    }

    private boolean running = false;
    public void collect () {
        collector.setPower(Constants.intakeMotorIn);
        running = true;
    }

    public void reverse () {
        collector.setPower(-Constants.intakeMotorIn);
    }

    public void stop() {
        collector.setPower(0);
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
