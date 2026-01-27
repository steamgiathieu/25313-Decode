package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarBluePaths implements AutoPaths {

    private final PathChain Path1;
    private final PathChain Path2;
    private final PathChain Path3;
    private final PathChain Path4;
    private final PathChain Path5;
    private final PathChain Path6;
    private final PathChain Path7;
    private final PathChain Path8;
    private final PathChain Path9;

    public FarBluePaths(Follower follower) {

        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),
                                new Pose(56, 16)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56, 16),
                                new Pose(56, 16)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(110))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56, 16),
                                new Pose(48, 36)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(48.000, 36.000),
                                new Pose(9.000, 36.000)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(9.000, 36.000),
                                new Pose(56.000, 16.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(110))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 16.000),
                                new Pose(48.000, 59.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(48.000, 59.000),
                                new Pose(9.000, 59.000)
                        )
                ).setTangentHeadingInterpolation()
                .build();
        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(9.000, 59.000),
                                new Pose(56.000, 16.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(110))

                .build();

        Path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 16.000),
                                new Pose(15.000, 45.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(-90))

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
    @Override public PathChain getPath9() { return Path9;}

}
