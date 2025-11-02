package org.firstinspires.ftc.team25313;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class FTCObject {
    protected final LinearOpMode opMode;
    protected final HardwareMap hardwareMap;
    protected final Telemetry telemetry;
    public boolean isDebugMode = Constants.DEBUG_MODE;

    public FTCObject(LinearOpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;
    }

    protected void addData(String caption, Object value) {
        if (isDebugMode) {
            telemetry.addData(caption, value);
        }
    }

    protected void update() {
        if (isDebugMode) {
            telemetry.update();
        }
    }

    protected void log(String caption, Object value) {
        if (isDebugMode) {
            Ultility.log("FTCObject", caption + ": " + value);
        }
    }

    /** Dừng an toàn để tránh crash khi đang chạy opmode */
    protected void safeSleep(long millis) {
        Ultility.sleep(millis);
    }
}
