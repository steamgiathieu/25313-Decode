package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
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

    public FarBluePaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(71.572, 22.940)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(120))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(71.572, 22.940),
                                new Pose(68.577, 38.037),
                                new Pose(19.544, 37.209)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(180))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(19.544, 37.209),

                                new Pose(71.865, 23.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(71.865, 23.060),
                                new Pose(66.930, 62.995),
                                new Pose(19.484, 61.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(180))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(19.484, 61.060),

                                new Pose(71.795, 23.102)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.795, 23.102),

                                new Pose(64.716, 43.381)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(120))

                .build();
    }

    @Override public PathChain getPath1() { return Path1; }
    @Override public PathChain getPath2() { return Path2; }
    @Override public PathChain getPath3() { return Path3; }
    @Override public PathChain getPath4() { return Path4; }
    @Override public PathChain getPath5() { return Path5; }
    @Override public PathChain getPath6() { return Path6; }

}
