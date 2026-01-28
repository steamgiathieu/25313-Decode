package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class NearRedPaths implements AutoPaths {

    private final PathChain Path1;
    private final PathChain Path2;
    private final PathChain Path3;
    private final PathChain Path4;
    private final PathChain Path5;
    private final PathChain Path6;
    private final PathChain Path7;
    private final PathChain Path8;

    public NearRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(123.000, 123.000),

                                new Pose(89.581, 105.153)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(35))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(89.581, 105.153),
                                new Pose(68.484, 84.223),
                                new Pose(125.991, 83.340)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(125.991, 83.340),

                                new Pose(89.684, 104.372)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(89.684, 104.372),
                                new Pose(77.358, 58.270),
                                new Pose(123.558, 57.763)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(123.558, 57.763),

                                new Pose(89.591, 104.884)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(89.591, 104.884),
                                new Pose(74.640, 34.895),
                                new Pose(124.470, 34.405)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(124.470, 34.405),

                                new Pose(73.586, 12.730)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(65))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(73.586, 12.730),

                                new Pose(80.744, 30.419)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }
    @Override public PathChain getPath1() { return Path1; }
    @Override public PathChain getPath2() { return Path2; }
    @Override public PathChain getPath3() { return Path3; }
    @Override public PathChain getPath4() { return Path4; }
    @Override public PathChain getPath5() { return Path5; }
    @Override public PathChain getPath6() { return Path6; }
    @Override public PathChain getPath7() { return Path7; }
    @Override public PathChain getPath8() { return Path8; }
}