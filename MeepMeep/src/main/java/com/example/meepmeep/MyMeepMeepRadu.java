package com.example.meepmeep;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MyMeepMeepRadu {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);
        Pose2d startPose = new Pose2d(-10,-60,Math.toRadians(-90));
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(13,18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .waitSeconds(1)
                        .back(28)//approaching bar
                        .lineToLinearHeading(startPose.plus(new Pose2d(-15, 20, Math.toRadians(0))))//leaving bar
                        .lineToLinearHeading(startPose.plus(new Pose2d(-28,30,Math.toRadians(-120))))//picking up first specimen
                        .lineToLinearHeading(startPose.plus(new Pose2d(-45,5,Math.toRadians(-45))))//dropping first specimen
                        .lineToLinearHeading(startPose.plus(new Pose2d(-50,20, Math.toRadians(-180))))//picking up second specimen
                        .lineToLinearHeading(startPose.plus(new Pose2d(-45,5,Math.toRadians(-45))))//dropping second specimen
                        .lineToLinearHeading(startPose.plus(new Pose2d(-48,20, Math.toRadians(-160))))//picking up third specimen
                        .lineToLinearHeading(startPose.plus(new Pose2d(-45,5,Math.toRadians(-45))))//dropping third specimen
                        .lineToLinearHeading(startPose.plus(new Pose2d(-40,60,Math.toRadians(90))))//readying to charge into park position
                        .forward(25)//parking
                        .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

