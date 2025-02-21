package org.firstinspires.ftc.teamcode.V2.Auto;

import static org.firstinspires.ftc.teamcode.V2.TeleOp.roombaBotTeleOpRed.pterm;
import static org.firstinspires.ftc.teamcode.V2.TeleOp.roombaBotTeleOpRed.pterm2;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.elbowPositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.suitcasePositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.wristPositions;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V2.hardwareMapV2;

@Autonomous
public class BrackenAutoPath extends teleBase {
    public org.firstinspires.ftc.teamcode.V2.hardwareMapV2 robot = new hardwareMapV2();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    public Pose2d startPose = new Pose2d(0,0,Math.toRadians(-90));
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.elbowPort.setPosition(elbowPositions.get("up"));
        robot.elbowStar.setPosition(1 - elbowPositions.get("up"));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence goingUpToBar = drive.trajectorySequenceBuilder(startPose)
                .waitSeconds(1)
                .splineToLinearHeading(startPose.plus(new Pose2d(0, 28, Math.toRadians(0))), Math.toRadians(30))//splining towards the bar
                .build();
        TrajectorySequence goingUpToSpecimens1 = drive.trajectorySequenceBuilder(goingUpToBar.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-28, 30, Math.toRadians(-120))), Math.toRadians(150))//getting first specimen
                .build();
        TrajectorySequence depositingSpecimens1 = drive.trajectorySequenceBuilder(goingUpToSpecimens1.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-45, 5, Math.toRadians(-45))), Math.toRadians(150))//dropping first
                .build();
        TrajectorySequence goingUpToSpecimens2 = drive.trajectorySequenceBuilder(depositingSpecimens1.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-48, 20, Math.toRadians(-180))), Math.toRadians(150))//getting second
                .build();
        TrajectorySequence depositingSpecimens2 = drive.trajectorySequenceBuilder(goingUpToSpecimens2.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-45, 5, Math.toRadians(-45))), Math.toRadians(150))//dropping second
                .build();
        TrajectorySequence goingUpToSpecimens3 = drive.trajectorySequenceBuilder(depositingSpecimens2.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-48, 20, Math.toRadians(-150))), Math.toRadians(150))//getting third
                .build();
        TrajectorySequence depositingSpecimens3 = drive.trajectorySequenceBuilder(goingUpToSpecimens3.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-45, 5, Math.toRadians(-45))), Math.toRadians(150))//dropping third
                .build();
        TrajectorySequence Parking = drive.trajectorySequenceBuilder(depositingSpecimens3.end())
                .splineToLinearHeading(startPose.plus(new Pose2d(-15, 50, Math.toRadians(90))), Math.toRadians(-90))//parking
                .build();
        waitForStart();
        if(opModeIsActive()){
            drive.setPoseEstimate(startPose);
        drive.followTrajectorySequence(goingUpToBar);
        drive.followTrajectorySequence(goingUpToSpecimens1);
        drive.followTrajectorySequence(depositingSpecimens1);
        drive.followTrajectorySequence(goingUpToSpecimens2);
        drive.followTrajectorySequence(depositingSpecimens2);
        drive.followTrajectorySequence(goingUpToSpecimens3);
        drive.followTrajectorySequence(depositingSpecimens3);
        drive.followTrajectorySequence(Parking);

    }
    }
}



