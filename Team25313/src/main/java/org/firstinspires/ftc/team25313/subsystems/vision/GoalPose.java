package org.firstinspires.ftc.team25313.subsystems.vision;

public class GoalPose {
    public double tx, ty, tz;   // meters
    public double yawDeg;

    public GoalPose(double tx, double ty, double tz, double yawDeg) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.yawDeg = yawDeg;
    }

    public double distanceMeters() {
        return Math.sqrt(tx*tx + ty*ty + tz*tz);
    }
}
