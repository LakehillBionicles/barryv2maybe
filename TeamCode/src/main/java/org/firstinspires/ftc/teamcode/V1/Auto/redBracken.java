package org.firstinspires.ftc.teamcode.V1.Auto;

import static org.firstinspires.ftc.teamcode.V1.hardwareMap.extendyBoiExtended;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.extendyBoiRetracted;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.intakePower;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.outakePower;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.twoArmBar;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V1.hardwareMap;
@Disabled
@Autonomous
public class redBracken extends teleBase {
    public org.firstinspires.ftc.teamcode.V1.hardwareMap robot = new hardwareMap();
    public double extendyBoiPower = -1;
    Pose2d startPose = new Pose2d(-9, -62, Math.toRadians(-90));
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
        TrajectorySequence thingy =drive.trajectorySequenceBuilder(startPose)
                .back(12)
                .strafeLeft(22)
                .back(12)
                .build();
        TrajectorySequence back =drive.trajectorySequenceBuilder(thingy.end())
                .back(6.5)
                .build();
        TrajectorySequence thingy3 = drive.trajectorySequenceBuilder(back.end())
                .forward(9.5)
                .turn(Math.toRadians(-90) )
                .waitSeconds(0)
                .splineToLinearHeading((startPose).plus(new Pose2d(-14, 36, Math.toRadians(-90))), Math.toRadians(90))
                .build();
        TrajectorySequence thingy4 = drive.trajectorySequenceBuilder(thingy3.end())
                .strafeLeft(5)
                .turn(Math.toRadians(45))
                .forward(7.5)
                .build();
        TrajectorySequence thingy5 =drive.trajectorySequenceBuilder(thingy4.end())
                .forward(3)
                .build();
        TrajectorySequence thingy6 = drive.trajectorySequenceBuilder(thingy5.end())
                .back(3)
                .build();
        TrajectorySequence thingy7 = drive.trajectorySequenceBuilder(thingy6.end())
                .back(7.5)
                .turn(Math.toRadians(-45))
                .strafeRight(27)
                .build();
        TrajectorySequence thingy8 = drive.trajectorySequenceBuilder(thingy7.end())
                .back(10)
                .build();
        waitForStart();
        drive.setPoseEstimate(startPose);
        drive.followTrajectorySequence(thingy);
        resetRuntime();
        while(getRuntime()<3) {
            robot.portArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /((twoArmBar*0.5) /4));
            robot.starArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /((twoArmBar*0.5) /4));
        }
        drive.followTrajectorySequence(back);
        resetRuntime();
        while(getRuntime()<1){
            robot.portArm.setPower(-0.5);
            robot.starArm.setPower(-0.5);
        }
        robot.portArm.setPower(0);
        robot.starArm.setPower(0);
        drive.followTrajectorySequence(thingy3);
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
        drive.followTrajectorySequence(thingy4);
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
        }
        drive.followTrajectorySequence(thingy5);
        robot.mcLarenDaddy.setPower(outakePower);
        sleep(300);
        drive.followTrajectorySequence(thingy6);
        robot.extendyBoi.setTargetPosition(0);
        robot.shoulderStar.setPosition(shoulderStarDown);
        robot.shoulderPort.setPosition(shoulderPortDown);
        while(getRuntime()<1.5) {
            robot.portArm.setPower(-1);
            robot.starArm.setPower(-1);
        }
        drive.followTrajectorySequence(thingy7);
        while(getRuntime()<1.5) {
            robot.shoulderStar.setPosition(shoulderStarBar);
            robot.shoulderPort.setPosition(shoulderPortBar);
            robot.portArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /((twoArmBar*0.7) /4));
            robot.starArm.setPower((double) (twoArmBar - robot.portArm.getCurrentPosition()) /((twoArmBar*0.7) /4));
        }
        drive.followTrajectorySequence(thingy8);
        robot.portArm.setPower(-0.3);
        robot.starArm.setPower(-0.3);
        sleep(6000);
    }
}
