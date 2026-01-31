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
    private final PathChain Path9;

    public NearBluePaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.000, 123.000),

                                new Pose(37.916, 108.670)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(37.916, 108.670),
                                new Pose(59.835, 83.198),
                                new Pose(18.098, 84.209)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(18.098, 84.209),

                                new Pose(37.619, 109.033)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(37.619, 109.033),
                                new Pose(88.879, 55.172),
                                new Pose(17.581, 60.279)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.581, 60.279),

                                new Pose(37.707, 109.093)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(37.707, 109.093),

                                new Pose(23.312, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))

                .build();
        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(37.707, 109.093),

                                new Pose(23.312, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))

                .build();
        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(37.707, 109.093),

                                new Pose(23.312, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))

                .build();
        Path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(37.707, 109.093),

                                new Pose(23.312, 88.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))

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