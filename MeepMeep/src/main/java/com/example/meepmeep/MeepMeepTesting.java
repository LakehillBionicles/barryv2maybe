package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);
        Pose2d startPose = new Pose2d(10,-60,Math.toRadians(-90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(13,18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .waitSeconds(1)
                        .lineToLinearHeading(startPose.plus(new Pose2d(-2, 27, Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(13,13,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(20,13,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(30,50,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(37,50,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(40,0,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(37,50,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(48,50,Math.toRadians(0))))
                        .lineToLinearHeading(startPose.plus(new Pose2d(48,0,Math.toRadians(0))))
                        .splineToLinearHeading(startPose.plus(new Pose2d(48, 50, Math.toRadians(0))), Math.toRadians(90))
                        .splineToLinearHeading(startPose.plus(new Pose2d(53, 50, Math.toRadians(0))), Math.toRadians(-90))
                        .splineToLinearHeading(startPose.plus(new Pose2d(53, 10, Math.toRadians(0))), Math.toRadians(-90))
                        .splineToLinearHeading(startPose.plus(new Pose2d(35, 50, Math.toRadians(0))), Math.toRadians(180))
                        .splineToLinearHeading(startPose.plus(new Pose2d(20, 50, Math.toRadians(0))), Math.toRadians(180))
                        .turn(Math.toRadians(-90))
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
