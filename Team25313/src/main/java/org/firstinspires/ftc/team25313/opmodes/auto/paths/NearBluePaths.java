package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class NearBluePaths implements AutoPaths {

    private final PathChain Path1;
    private final PathChain Path2;
    private final PathChain Path3;
    private final PathChain Path4;
    private final PathChain Path5;
    private final PathChain Path6;
    private final PathChain Path7;
    private final PathChain Path8;

    public NearBluePaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.000, 123.000),

                                new Pose(54.419, 105.153)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(54.419, 105.153),
                                new Pose(75.516, 84.223),
                                new Pose(18.009, 83.340)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(18.009, 83.340),

                                new Pose(54.316, 104.372)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(54.316, 104.372),
                                new Pose(66.642, 58.270),
                                new Pose(20.442, 57.763)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.442, 57.763),

                                new Pose(54.409, 104.884)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(54.409, 104.884),
                                new Pose(69.360, 34.895),
                                new Pose(19.530, 34.405)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(19.530, 34.405),

                                new Pose(70.414, 12.730)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70.414, 12.730),

                                new Pose(63.256, 30.419)
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