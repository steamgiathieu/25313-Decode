package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarRedPaths implements AutoPaths {

    private final PathChain Path1;
    private final PathChain Path2;
    private final PathChain Path3;
    private final PathChain Path4;
    private final PathChain Path5;
    private final PathChain Path6;
    private final PathChain Path7;
    private final PathChain Path8;
    private final PathChain Path9;

    public FarRedPaths(Follower follower) {

        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 8.000),

                                new Pose(88.000, 16.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 16.000),

                                new Pose(88.000, 16.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(70))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 16.000),

                                new Pose(100.000, 35.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(70), Math.toRadians(0))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(100.000, 35.000),

                                new Pose(133.000, 35.000)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(133.000, 35.000),

                                new Pose(88.000, 16.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(70))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 16.000),

                                new Pose(98.000, 59.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(70), Math.toRadians(0))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(98.000, 59.000),

                                new Pose(132.000, 59.000)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(132.000, 59.000),

                                new Pose(88.000, 16.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(70))
                .build();
        Path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 16.000),

                                new Pose(130, 57)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(70), Math.toRadians(60))
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
    @Override public PathChain getPath9() { return Path9; }
}