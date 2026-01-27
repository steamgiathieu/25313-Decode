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
                                new Pose(88.000, 10.000),
                                new Pose(88.670, 35.833)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(70), Math.toRadians(0))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.670, 35.833),
                                new Pose(132.526, 35.386)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(132.526, 35.386),
                                new Pose(72.237, 15.321)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(62))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.237, 15.321),
                                new Pose(92.763, 61.116)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(62), Math.toRadians(0))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(92.763, 61.116),
                                new Pose(132.669, 60.112)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(124.577, 60.112),
                                new Pose(72.084, 15.112)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(62))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.084, 15.112),
                                new Pose(72.047, 31.460)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.084, 15.112),
                                new Pose(72.047, 31.460)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        Path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.084, 15.112),
                                new Pose(72.047, 31.460)
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
    @Override public PathChain getPath9() { return Path9; }
}