package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);
        Pose2d startPose = new Pose2d(2,-60,Math.toRadians(-90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(13,18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .waitSeconds(1)
                        .lineToLinearHeading(startPose.plus(new Pose2d(2, 18, Math.toRadians(0))))
                        .waitSeconds(1)
                        .back(10.5)
                        .waitSeconds(1)
                        .splineToLinearHeading(startPose.plus(new Pose2d(13,13,Math.toRadians(0))), Math.toRadians(-90))
                        .splineToLinearHeading(startPose.plus(new Pose2d(24,13,Math.toRadians(0))), Math.toRadians(45))
                        .splineToLinearHeading(startPose.plus(new Pose2d(34,50,Math.toRadians(0))),Math.toRadians(0))
                        .splineToLinearHeading(startPose.plus(new Pose2d(42,50,Math.toRadians(0))), Math.toRadians(-90))
                        .back(5)
                        .splineToSplineHeading(startPose.plus(new Pose2d(39,20,Math.toRadians(180))), Math.toRadians(90))
                        .splineToSplineHeading(startPose.plus(new Pose2d(39,-10,Math.toRadians(180))), Math.toRadians(-90))
                        .forward(4)
                        .splineToSplineHeading(startPose.plus(new Pose2d(-8,24,Math.toRadians(180))), Math.toRadians(90))
                        .splineToSplineHeading(startPose.plus(new Pose2d(-8,29,Math.toRadians(0))), Math.toRadians(90))
                        .strafeRight(9)
                        .turn(Math.PI)
                        .build());
                        //.lineToLinearHeading(startPose.plus(new Pose2d(-5,5,Math.toRadians(-90))))
                        //.lineToLinearHeading(startPose.plus(new Pose2d(-18,5,Math.toRadians(-45))))


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
