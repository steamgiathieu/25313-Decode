package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
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

    public NearRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(123.000, 123.000),
                                new Pose(86.326, 101.972)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(35))
                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(86.326, 101.972),
                                new Pose(91.228, 84.433)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(0))
                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(91.228, 84.433),
                                new Pose(124.651, 83.949)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(124.651, 83.949),
                                new Pose(86.549, 101.981)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35))
                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(86.549, 101.981),
                                new Pose(95.721, 60.814)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(0))
                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(95.721, 60.814),
                                new Pose(128.633, 60.033)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(128.633, 60.033),
                                new Pose(86.116, 101.298)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35))
                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(86.116, 101.298),
                                new Pose(87.433, 65.902)
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
}