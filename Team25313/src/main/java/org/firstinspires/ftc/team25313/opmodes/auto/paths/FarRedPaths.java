package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarRedPaths implements AutoPaths {

    private final PathChain path1;
    private final PathChain path2;
    private final PathChain path3;
    private final PathChain path4;
    private final PathChain path5;
    private final PathChain path6;
    private final PathChain path7;
    private final PathChain path8;
    private final PathChain path9;

    public FarRedPaths(Follower follower) {

        path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(90.000, 10.000),

                                new Pose(89.586, 33.791)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(75))
                .build();

        path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(89.586, 33.791),
                                new Pose(129.935, 35.665)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(129.935, 35.665),
                                new Pose(72.623, 22.335)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.623, 22.335),
                                new Pose(73.270, 23.340)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(73.270, 23.340),
                                new Pose(86.177, 59.842)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(86.177, 59.842),
                                new Pose(124.605, 59.730)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(124.605, 59.730),
                                new Pose(72.791, 22.665)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.791, 22.665),
                                new Pose(74.805, 25.730)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(74.805, 25.730),
                                new Pose(73.558, 47.363)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }
    @Override public PathChain getPath1() { return path1; }
    @Override public PathChain getPath2() { return path2; }
    @Override public PathChain getPath3() { return path3; }
    @Override public PathChain getPath4() { return path4; }
    @Override public PathChain getPath5() { return path5; }
    @Override public PathChain getPath6() { return path6; }
    @Override public PathChain getPath7() { return path7; }
    @Override public PathChain getPath8() { return path8; }
    @Override public PathChain getPath9() { return path9; }
}