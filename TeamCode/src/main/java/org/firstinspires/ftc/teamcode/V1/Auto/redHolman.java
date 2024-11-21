package org.firstinspires.ftc.teamcode.V1.Auto;


import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarDown;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V1.hardwareMap;

@Autonomous
public class redHolman extends teleBase {
    public hardwareMap robot = new hardwareMap();
    public double extendyBoiPower = -1;
    Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
    //10, -62
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.shoulderStar.setPosition(shoulderStarDown);
        robot.shoulderPort.setPosition(shoulderPortDown);
        robot.extendyBoi.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.extendyBoi.setTargetPosition(0);
        robot.extendyBoi.setPower(extendyBoiPower);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence thingy =drive.trajectorySequenceBuilder(startPose)
                //.back(24)
                .lineToLinearHeading((startPose).plus(new Pose2d(-5, 24, Math.toRadians(0))))
                //.strafeRight(3) //goobers//
                .build();
        TrajectorySequence back =drive.trajectorySequenceBuilder(thingy.end())
                .back(6.5)
                .build();
        TrajectorySequence thingy2 =drive.trajectorySequenceBuilder(back.end())
                .forward(16)
                .lineToLinearHeading((startPose).plus(new Pose2d(23, 23, Math.toRadians(0))))
                .lineToLinearHeading((startPose).plus(new Pose2d(26, 45, Math.toRadians(0))))
                .lineToLinearHeading((startPose).plus(new Pose2d(40, 52, Math.toRadians(0))))
                .turn(Math.toRadians(90))
                .strafeRight(5)
                .lineToLinearHeading((startPose).plus(new Pose2d(40, 15, Math.toRadians(90))))
                .waitSeconds(20)
                .back(5)
                .strafeLeft(33)
                .lineToLinearHeading((startPose).plus(new Pose2d(47, 48, Math.toRadians(90))))
                .strafeRight(5)
                .lineToLinearHeading((startPose).plus(new Pose2d(47, 15, Math.toRadians(90))))
                .back(3)
                .strafeLeft(43)
                .lineToLinearHeading((startPose).plus(new Pose2d(38, 50, Math.toRadians(90))))
                .strafeRight(40)
                .forward(5)
                .build();
        waitForStart();
        drive.setPoseEstimate(startPose);
        robot.shoulderStar.setPosition(shoulderStarBar);
        robot.shoulderPort.setPosition(shoulderPortBar);
        drive.followTrajectorySequence(thingy);
        telemetry.addData("Pos",String.valueOf(drive.getPoseEstimate()));
        telemetry.addData("Pos",String.valueOf(startPose.plus((drive.getPoseEstimate()))));
        telemetry.update();
        sleep(40000);
        resetRuntime();
        while(getRuntime()<2) {
            robot.portArm.setPower(1);
            robot.starArm.setPower(1);
        }
        robot.portArm.setPower(0);
        robot.starArm.setPower(0);
        drive.setPoseEstimate(thingy.end());
        drive.followTrajectorySequence(back);
        resetRuntime();
        while(getRuntime()<2) {
            robot.starArm.setPower(-1);
            robot.portArm.setPower(-1);
        }
        robot.starArm.setPower(0);
        robot.portArm.setPower(0);
        drive.setPoseEstimate(back.end());
        drive.followTrajectorySequence(thingy2);
    }
}
