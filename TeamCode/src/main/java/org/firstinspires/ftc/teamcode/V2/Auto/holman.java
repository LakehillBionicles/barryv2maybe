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
public class holman extends teleBase {
    public org.firstinspires.ftc.teamcode.V2.hardwareMapV2 robot = new hardwareMapV2();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    public Pose2d startPose = new Pose2d(0,0,Math.toRadians(-90));

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        robot.elbowPort.setPosition(elbowPositions.get("up"));
        robot.elbowStar.setPosition(1-elbowPositions.get("up"));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence thingy3 =drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(startPose.plus(new Pose2d(2, 18, Math.toRadians(0))))
                .build();
        TrajectorySequence thingy1 = drive.trajectorySequenceBuilder(thingy3.end())
                .back(10.5)
                .build();
        TrajectorySequence thingy2 =drive.trajectorySequenceBuilder(thingy3.end())
                .lineToLinearHeading(startPose.plus(new Pose2d(13,13,Math.toRadians(0))))
                .lineToLinearHeading(startPose.plus(new Pose2d(24,13,Math.toRadians(0))))
                .lineToLinearHeading(startPose.plus(new Pose2d(30,50,Math.toRadians(0))))
                .splineToLinearHeading(startPose.plus(new Pose2d(42,50,Math.toRadians(0))), Math.toRadians(0))
                .splineToLinearHeading(startPose.plus(new Pose2d(42,8,Math.toRadians(0))), Math.toRadians(0))
                .splineToLinearHeading(startPose.plus(new Pose2d(37,50,Math.toRadians(0))), Math.toRadians(90))
                .splineToLinearHeading(startPose.plus(new Pose2d(48, 50, Math.toRadians(0))), Math.toRadians(0))
                .splineToLinearHeading(startPose.plus(new Pose2d(48, 12, Math.toRadians(0))), Math.toRadians(90))
                .splineToLinearHeading(startPose.plus(new Pose2d(48, 50, Math.toRadians(0))), Math.toRadians(90))
                .splineToLinearHeading(startPose.plus(new Pose2d(59, 50, Math.toRadians(0))), Math.toRadians(-90))
                .splineToLinearHeading(startPose.plus(new Pose2d(59, 8, Math.toRadians(0))), Math.toRadians(-90))
                .strafeRight(9)
                .turn(Math.PI)
                .build();
        waitForStart();
        if(opModeIsActive()){
            drive.setPoseEstimate(startPose);
            robot.wrist.setPosition(wristPositions.get("mid"));
            robot.elbowPort.setPosition(elbowPositions.get("down"));
            robot.elbowStar.setPosition(1-elbowPositions.get("down"));
            drive.followTrajectorySequence(thingy3);
            resetRuntime();
            while (getRuntime()<1) {
                robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
            }
            while(getRuntime()<2){
                robot.portArm.setPower((double) (2150 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                robot.starArm.setPower((double) (2150 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
            }
            drive.followTrajectorySequence(thingy1);
            resetRuntime();
            while(getRuntime()<1){
                robot.portArm.setPower((double) (-200 - robot.portArm.getCurrentPosition()) /(((6000*2.548)*0.7) /4));
                robot.starArm.setPower((double) (-200 - robot.portArm.getCurrentPosition()) /(((6000*2.548)*0.7) /4));
            }
            while (getRuntime()<2) {
                robot.starSuitcase.setPower((suitcasePositions.get("down") - robot.starSuitcase.getCurrentPosition()) * (pterm2 / 950));
                robot.portSuitcase.setPower((suitcasePositions.get("down") - robot.starSuitcase.getCurrentPosition()) * (pterm2 / 950));
                robot.portArm.setPower((double) (-100 - robot.portArm.getCurrentPosition()) /(((6000*2.548)*0.7) /4));
                robot.starArm.setPower((double) (-100 - robot.portArm.getCurrentPosition()) /(((6000*2.548)*0.7) /4));
                robot.elbowPort.setPosition(elbowPositions.get("up"));
                robot.elbowStar.setPosition(1-elbowPositions.get("up"));
            }
            drive.followTrajectorySequence(thingy2);
        }
    }
}
