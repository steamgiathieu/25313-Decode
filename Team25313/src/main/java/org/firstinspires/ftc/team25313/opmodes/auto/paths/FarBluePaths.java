package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarBluePaths implements AutoPaths {

    private final PathChain path1;
    private final PathChain path2;
    private final PathChain path3;
    private final PathChain path4;
    private final PathChain path5;
    private final PathChain path6;
    private final PathChain path7;
    private final PathChain path8;
    private final PathChain path9;

    public FarBluePaths(Follower follower) {

        path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(54.000, 10.000),
                                new Pose(54.414, 33.791)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(105))
                .build();

        path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.065, 35.665),
                                new Pose(71.377, 22.335)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.065, 35.665),
                                new Pose(71.377, 22.335)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.377, 22.335),
                                new Pose(70.730, 23.340)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70.730, 23.340),
                                new Pose(57.823, 59.842)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.823, 59.842),
                                new Pose(19.395, 59.730)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(19.395, 59.730),
                                new Pose(71.209, 22.665)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.209, 22.665),
                                new Pose(69.195, 25.730)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(69.195, 25.730),
                                new Pose(70.442, 47.363)
                        )
                ).setTangentHeadingInterpolation()
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
