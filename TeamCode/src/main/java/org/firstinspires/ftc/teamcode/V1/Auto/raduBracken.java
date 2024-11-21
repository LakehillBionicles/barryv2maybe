package org.firstinspires.ftc.teamcode.V1.Auto;

import static org.firstinspires.ftc.teamcode.V1.hardwareMap.extendyBoiExtended;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.extendyBoiRetracted;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.intakePower;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.twoArmBar;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V1.hardwareMap;
@Autonomous
public class raduBracken extends teleBase {
    public org.firstinspires.ftc.teamcode.V1.hardwareMap robot = new hardwareMap();
    public double extendyBoiPower = -1;
    Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.shoulderStar.setPosition(shoulderStarDown);
        robot.shoulderPort.setPosition(shoulderPortDown);
        robot.extendyBoi.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.extendyBoi.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.portArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.portArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.portArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.extendyBoi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.extendyBoi.setTargetPosition(0);
        robot.extendyBoi.setPower(extendyBoiPower);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
TrajectorySequence goingUpToBar=drive.trajectorySequenceBuilder(startPose)
        .lineToLinearHeading(new Pose2d(0, -10, Math.toRadians(0)))
        .build();
TrajectorySequence goingUpToSpecimens= drive.trajectorySequenceBuilder(goingUpToBar.end())
        .forward(5)//pulling away from bar//
        .turn(Math.toRadians(-90))//turning to specimens//
        .forward(40)//driving to specimens//
        .build();
TrajectorySequence depositingSpecimens= drive.trajectorySequenceBuilder(goingUpToSpecimens.end())
        .lineToLinearHeading(new Pose2d(-44,-34,Math.toRadians(120)))//picking up first specimen//
        .lineToLinearHeading(new Pose2d(-58,-54, Math.toRadians(-125)))//depositing first specimen//
        .build();
TrajectorySequence depositingSecondSpecimen= drive.trajectorySequenceBuilder(depositingSpecimens.end())
        .lineToLinearHeading(new Pose2d(-50,-34,Math.toRadians(120)))//picking up second specimen//
        .lineToLinearHeading(new Pose2d(-58, -54,Math.toRadians(-125)))//scoring second specimen//
        .build();
TrajectorySequence lowLevelAscent= drive.trajectorySequenceBuilder(depositingSecondSpecimen.end())
        .lineToLinearHeading(new Pose2d(-34,0,Math.toRadians(180)))//low level ascent//
        .back(10)//low level ascent completed//
        .build();


        waitForStart();
        if (isStopRequested()) return;
        drive.setPoseEstimate(startPose);
       drive.followTrajectorySequence(goingUpToBar);
        resetRuntime();
        while(getRuntime()<3) {
            robot.portArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /((twoArmBar*0.5) /4));
            robot.starArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /((twoArmBar*0.5) /4));
        }
        drive.followTrajectorySequence(goingUpToSpecimens); //I lied, this is going up to the first specimen//
        resetRuntime();
        while(getRuntime()<1){
            robot.portArm.setPower(-0.5);
            robot.starArm.setPower(-0.5);
        }
        robot.portArm.setPower(0);
        robot.starArm.setPower(0);
        drive.followTrajectorySequence(depositingSpecimens); //this is the second specimen//
        resetRuntime();
        while(getRuntime()<1) {
            robot.portArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /(((500*2.548)*0.7) /4));
            robot.starArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /(((500*2.548)*0.7) /4));
        }
        robot.starArm.setPower(0);
        robot.portArm.setPower(0);
        robot.mcLarenDaddy.setPower(intakePower);
        resetRuntime();
        while(getRuntime()<1.5) {
            robot.extendyBoi.setTargetPosition(extendyBoiExtended);
            robot.extendyBoi.setPower(extendyBoiPower);
        }
        resetRuntime();
        while(getRuntime()<1){
            robot.portArm.setPower(-0.5);
            robot.starArm.setPower(-0.5);
        }
        robot.portArm.setPower(0);
        robot.starArm.setPower(0);
        resetRuntime();
        while(getRuntime()<1.5) {
            robot.extendyBoi.setTargetPosition(extendyBoiRetracted);
        }
        resetRuntime();
        while(getRuntime()<0.5) {
            robot.portArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /(((200*2.548)*0.7) /4));
            robot.starArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /(((200*2.548)*0.7) /4));
        }
        robot.portArm.setPower(0);
        robot.starArm.setPower(0);
        drive.followTrajectorySequence(depositingSecondSpecimen);
        resetRuntime();
        while(getRuntime()<3) {
            robot.shoulderStar.setPosition(shoulderStarBar);
            robot.shoulderPort.setPosition(shoulderPortBar);
            robot.portArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
            robot.starArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
        }
        resetRuntime();
        while(getRuntime()<1) {
            robot.extendyBoi.setTargetPosition(extendyBoiExtended);
        }drive.followTrajectorySequence(lowLevelAscent);
            sleep(1000);
        }

    }

