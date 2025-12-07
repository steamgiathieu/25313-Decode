package org.firstinspires.ftc.team25313.subsystems.sensor;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class REVDistanceSensor {
    private DistanceSensor sensorDistance;
    // you can use this as a regular DistanceSensor.
    public REVDistanceSensor (HardwareMap hwMap) {
        sensorDistance = hwMap.get(DistanceSensor.class, "sensor_distance");
        // you can also cast this to a Rev2mDistanceSensor if you want to use added
        // methods associated with the Rev2mDistanceSensor class.
        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorDistance;
    }

    public double GetData() {
        double botToGoal = sensorDistance.getDistance(DistanceUnit.METER);
        return botToGoal;
    }
}