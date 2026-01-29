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
    private final PathChain Path7;
    private final PathChain Path8;

    public FarBluePaths(Follower follower) {

        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(65.712, 15.237)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(115))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(65.712, 15.237),
                                new Pose(62.653, 34.616),
                                new Pose(18.107, 37.279)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(18.107, 37.279),

                                new Pose(63.953, 11.530)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(63.953, 11.530),
                                new Pose(56.307, 64.500),
                                new Pose(17.488, 60.837)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.488, 60.837),

                                new Pose(65.400, 11.870)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(65.400, 11.870),
                                new Pose(67.609, 87.695),
                                new Pose(17.763, 85.177)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.763, 85.177),

                                new Pose(71.833, 72.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.833, 72.000),

                                new Pose(58.721, 59.609)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(140))

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
