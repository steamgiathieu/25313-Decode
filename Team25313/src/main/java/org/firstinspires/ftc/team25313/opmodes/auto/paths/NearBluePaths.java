package org.firstinspires.ftc.team25313.opmodes.auto.paths;

import com.pedropathing.follower.Follower;
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

    public NearBluePaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.000, 123.000),
                                new Pose(57.674, 101.972)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(145))
                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.674, 101.972),
                                new Pose(52.772, 84.433)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))
                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(52.772, 84.433),
                                new Pose(19.349, 83.949)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(19.349, 83.949),
                                new Pose(57.451, 101.981)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))
                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.451, 101.981),
                                new Pose(48.279, 60.814)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))
                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(48.279, 60.814),
                                new Pose(15.367, 60.033)
                        )
                ).setTangentHeadingInterpolation()
                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.367, 60.033),
                                new Pose(57.884, 101.298)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))
                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.884, 101.298),
                                new Pose(56.567, 65.902)
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
}