package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
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

    public FarRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 8.000),

                                new Pose(72.428, 22.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(60))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(72.428, 22.940),
                                new Pose(74.586, 41.553),
                                new Pose(126.800, 38.716)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(60), Math.toRadians(0))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.800, 38.716),

                                new Pose(72.135, 23.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(60))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(72.135, 23.060),
                                new Pose(82.763, 64.502),
                                new Pose(126.358, 62.902)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(60), Math.toRadians(0))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.358, 62.902),

                                new Pose(72.205, 23.102)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(60))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.205, 23.102),

                                new Pose(79.284, 43.381)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(60), Math.toRadians(60))

                .build();
    }

    @Override public PathChain getPath1() { return Path1; }
    @Override public PathChain getPath2() { return Path2; }
    @Override public PathChain getPath3() { return Path3; }
    @Override public PathChain getPath4() { return Path4; }
    @Override public PathChain getPath5() { return Path5; }
    @Override public PathChain getPath6() { return Path6; }
}