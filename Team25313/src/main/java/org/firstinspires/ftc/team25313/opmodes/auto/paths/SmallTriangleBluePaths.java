package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class SmallTriangleBluePaths implements AutoPaths {

    private final PathChain path1;
    private final PathChain path2;
    private final PathChain path3;
    private final PathChain path4;
    private final PathChain path5;
    private final PathChain path6;

    public SmallTriangleBluePaths(Follower follower) {

        path1 = follower.pathBuilder()
                .addPath(new BezierLine(
                        new Pose(56.000, 8.000),
                        new Pose(64.540, 16.577)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(115))
                .build();

        path2 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(64.540, 16.577),
                        new Pose(57.456, 36.691),
                        new Pose(14.865, 36.005)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(180))
                .build();

        path3 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(14.865, 36.005),
                        new Pose(65.126, 42.551),
                        new Pose(68.316, 75.349)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        path4 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(68.316, 75.349),
                        new Pose(84.630, 56.181),
                        new Pose(16.367, 57.405)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        path5 = follower.pathBuilder()
                .addPath(new BezierLine(
                        new Pose(16.367, 57.405),
                        new Pose(68.223, 75.837)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        path6 = follower.pathBuilder()
                .addPath(new BezierLine(
                        new Pose(68.223, 75.837),
                        new Pose(19.507, 84.247)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();
    }

    @Override public PathChain getPath1() { return path1; }
    @Override public PathChain getPath2() { return path2; }
    @Override public PathChain getPath3() { return path3; }
    @Override public PathChain getPath4() { return path4; }
    @Override public PathChain getPath5() { return path5; }
    @Override public PathChain getPath6() { return path6; }
}
