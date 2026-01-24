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

    public FarBluePaths(Follower follower) {

        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 10.000),
                                new Pose(55.330, 35.833)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.330, 35.833),
                                new Pose(11.474, 35.386)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.474, 35.386),
                                new Pose(71.763, 15.321)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(118))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.763, 15.321),
                                new Pose(51.237, 61.116)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(118), Math.toRadians(180))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(51.237, 61.116),
                                new Pose(11.330, 60.112)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(19.423, 60.112),
                                new Pose(71.916, 15.112)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(118))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.916, 15.112),
                                new Pose(71.953, 31.460)
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
}
