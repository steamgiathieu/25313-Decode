package org.firstinspires.ftc.team25313.subsystems.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class LoggingUtil {
    private final LinearOpMode opMode;
    private final FtcDashboard dashboard;
    private final boolean debugMode;

    public LoggingUtil(LinearOpMode opMode, boolean debugMode) {
        this.opMode = opMode;
        this.debugMode = debugMode;
        this.dashboard = FtcDashboard.getInstance();
    }

    public void log(String caption, Object value) {
        if (debugMode) {
            opMode.telemetry.addData(caption, value);
            opMode.telemetry.update();
        }
    }

    public void dashboard(String caption, Object value) {
        if (debugMode) {
            dashboard.getTelemetry().addData(caption, value);
            dashboard.getTelemetry().update();
        }
    }

    public void clear() {
        opMode.telemetry.clear();
    }
}
