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
    private final PathChain Path9;

    public NearRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(117.000, 131.000),

                                new Pose(93.526, 101.637)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(40))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(93.526, 101.637),
                                new Pose(84.165, 82.528),
                                new Pose(125.902, 83.037)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(125.902, 83.037),

                                new Pose(93.488, 101.498)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(93.488, 101.498),
                                new Pose(47.753, 55.005),
                                new Pose(127.256, 58.605)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(127.256, 58.605),

                                new Pose(93.400, 101.223)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(93.400, 101.223),

                                new Pose(119.688, 89.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(40))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(106.293, 109.093),

                                new Pose(120.688, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(35))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(106.293, 109.093),

                                new Pose(120.688, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(35))

                .build();

        Path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(106.293, 109.093),

                                new Pose(120.688, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(35))

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