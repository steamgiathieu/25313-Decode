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

    public NearRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(123.000, 123.000),

                                new Pose(106.084, 108.670)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(45))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(106.084, 108.670),
                                new Pose(84.165, 83.198),
                                new Pose(125.902, 84.209)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(125.902, 84.209),

                                new Pose(106.381, 109.033)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(106.381, 109.033),
                                new Pose(55.121, 55.172),
                                new Pose(126.419, 60.279)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.419, 60.279),

                                new Pose(106.293, 109.093)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(106.293, 109.093),

                                new Pose(119.688, 89.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(45))

                .build();
    }
    @Override public PathChain getPath1() { return Path1; }
    @Override public PathChain getPath2() { return Path2; }
    @Override public PathChain getPath3() { return Path3; }
    @Override public PathChain getPath4() { return Path4; }
    @Override public PathChain getPath5() { return Path5; }
    @Override public PathChain getPath6() { return Path6; }
}