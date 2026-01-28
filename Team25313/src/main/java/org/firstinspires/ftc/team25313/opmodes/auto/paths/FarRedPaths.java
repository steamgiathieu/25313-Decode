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
    private final PathChain Path7;
    private final PathChain Path8;

    public FarRedPaths(Follower follower) {

        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.000, 8.000),

                                new Pose(78.288, 15.237)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(78.288, 15.237),
                                new Pose(81.347, 34.616),
                                new Pose(126.563, 31.753)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.563, 31.753),

                                new Pose(80.047, 11.530)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(65))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(80.047, 11.530),
                                new Pose(93.553, 60.984),
                                new Pose(126.679, 56.651)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.679, 56.651),

                                new Pose(78.600, 11.870)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(65))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(78.600, 11.870),
                                new Pose(85.935, 83.677),
                                new Pose(128.079, 81.828)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(128.079, 81.828),

                                new Pose(72.167, 72.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(72.167, 72.000),

                                new Pose(85.279, 59.609)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(40))

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