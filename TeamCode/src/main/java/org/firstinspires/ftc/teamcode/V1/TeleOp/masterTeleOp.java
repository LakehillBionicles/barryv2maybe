package org.firstinspires.ftc.teamcode.V1.TeleOp;

import static org.firstinspires.ftc.teamcode.V1.hardwareMap.extendyBoiExtended;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.extendyBoiRetracted;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.intakePower;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.outakePower;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderPortUp;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarBar;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarDown;
import static org.firstinspires.ftc.teamcode.V1.hardwareMap.shoulderStarUp;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.V1.hardwareMap;

import java.util.Objects;


@Config
@TeleOp
public class masterTeleOp extends teleBase {
    public hardwareMap robot = new hardwareMap();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    public static double shoulderPortPos = 0.5;
    public static double shoulderStarPos = 0.5;
    public static double extendyBoiPos = 0.1;
    public static double slowMode = 4;
    public double leftHigh = -10000;
    public double armFeedForward = 0;
    public double drivePower = 1;
    public double extendyBoiPower = -1;
    public double grabTime = -2000;
    public double dropTime = -2000;
    public double skibbidyOhioRizz = -2000;
    public double maxCurrent = 0;
    public double accelerationExtendyBoi = 0;
    public double prevVelo = 0;
    public double counter = 0;
    public double prevRuntime = 0;
    public static double runtime = 0;
    public String lastArmPress= "down";
    public double extenderTimer = 0;
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        robot.extendyBoi.setTargetPosition(0);
        robot.shoulderStar.setPosition(shoulderStarDown);
        robot.shoulderPort.setPosition(shoulderPortDown);
        robot.extendyBoi.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.portArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.portArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.portArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.extendyBoi.setTargetPosition(0);
        robot.extendyBoi.setPower(extendyBoiPower);
        waitForStart();
        while (opModeIsActive()) {
            copyGamepad(previousGamepad1, previousGamepad2, currentGamepad1, currentGamepad2, gamepad1, gamepad2);
            runtime = getRuntime();
            if ((robot.extendyBoi.getTargetPosition() == extendyBoiExtended) && robot.shoulderPort.getPosition() != shoulderPortBar) {
                drivePower = (double) 1 / 3;
            } else {
                drivePower = 1;
            }
            if (!previousGamepad1.x && currentGamepad1.x) {
                if (robot.mcLarenDaddy.getPower() > 0) {
                    robot.mcLarenDaddy.setPower(0);
                } else {
                    robot.mcLarenDaddy.setPower(outakePower);
                }
            } else if (!previousGamepad1.b && currentGamepad1.b) {
                if (robot.mcLarenDaddy.getPower() < 0) {
                    robot.mcLarenDaddy.setPower(0);
                } else {
                    robot.mcLarenDaddy.setPower(intakePower);
                }
            }
            if(gamepad1.left_trigger>0.1||gamepad2.left_trigger>0.1){
                lastArmPress = "down";
            }else if(gamepad1.right_trigger>0.1||gamepad2.right_trigger>0.1){
                lastArmPress = "up";
            }
            //This part allows the user to stop moving the robot automatically if something goes wrong
            //Does this by sending the time back to a negative number if the dpad has stopped being pressed
            //Next part checks whether the dpad was pressed beforehand and then after and then resets timer
            //success and glory
            if ((previousGamepad1.dpad_left && !currentGamepad1.dpad_left)||(previousGamepad2.dpad_left&&!currentGamepad2.dpad_left)){
                grabTime = -2000;
            } else if ((!previousGamepad1.dpad_left && currentGamepad1.dpad_left)||(!previousGamepad2.dpad_left&&currentGamepad2.dpad_left)){
                grabTime = runtime;
            }
            if      ((previousGamepad1.y && !currentGamepad1.y)||(previousGamepad2.y&&!currentGamepad2.y)||
                    ((gamepad1.left_stick_y>0.1||gamepad1.left_stick_y<-0.1)||(gamepad1.right_stick_x>0.1|| gamepad1.right_stick_x<-0.1)||(gamepad1.left_stick_x>0.1||gamepad1.left_stick_x<-0.1))) {
                dropTime = -2000;
            }else if ((!previousGamepad1.y && currentGamepad1.y)||(!previousGamepad2.y&&currentGamepad2.y)){ //apples are tremendous!!!! Teeheehee. i agree//
                dropTime = runtime;
            }
            if ((previousGamepad1.a && !currentGamepad1.a)||(previousGamepad2.a&&!currentGamepad2.a)) {
                skibbidyOhioRizz = -2000;
            } else if (!previousGamepad1.a && currentGamepad1.a) {
                skibbidyOhioRizz = getRuntime();
            }
            if (timerCheck(dropTime, 0, 0.5, runtime)) { //dropTime equals ten, runTime equals 10.2//
                robot.mcLarenDaddy.setPower(outakePower);
            } else if (timerCheck(dropTime, 0.5, 0.8, runtime)) { //Kerbal space program is mid
                robot.mcLarenDaddy.setPower(0);
                robot.starArm.setPower(1);
                robot.portArm.setPower(1);//why does Radu yap so much//
                robot.fpd.setPower(-0.2);
                robot.fsd.setPower(-0.2);
                robot.bpd.setPower(-0.2);
                robot.bsd.setPower(-0.2);
            } else if (timerCheck(dropTime, 0.8, 1.5, runtime)) {
                robot.extendyBoi.setTargetPosition(extendyBoiRetracted); //radu was also here//
            } else if (timerCheck(dropTime, 1.5, 3, runtime)) { //I did that code teeheeheehehehe//
                robot.portArm.setPower(-1); //Radu was here//
                robot.starArm.setPower(-1);
            }
            //robot hang your self pls :)//
            //ngl austin is very zesty and fruity with his movements//
            //omg yes king Austin slayyyyyyy//
            //After the timer is set the runtime goes up but grabtime doesn't
            //the code does you know
            else if(timerCheck(grabTime, 0, 0.3, runtime)) {
                robot.fpd.setPower(0.5);
                robot.fsd.setPower(0.5);
                robot.bpd.setPower(0.5);
                robot.bsd.setPower(0.5);
                robot.shoulderPort.setPosition(shoulderPortBar-0.05);
                robot.shoulderStar.setPosition(shoulderStarBar+0.05);
            }else if(timerCheck(grabTime, 0.3, 0.5, runtime)){
                robot.fpd.setPower(0);
                robot.fsd.setPower(0);
                robot.bpd.setPower(0);
                robot.bsd.setPower(0);
                robot.shoulderPort.setPosition(shoulderPortBar);
                robot.shoulderStar.setPosition(shoulderStarBar);
            }else if(timerCheck(grabTime, 0.5, 1, runtime)){
                robot.fpd.setPower(-0.5);
                robot.fsd.setPower(-0.5);
                robot.bpd.setPower(-0.5);
                robot.bsd.setPower(-0.5);
            }else if(timerCheck(grabTime, 1, 1.5, runtime)){
                robot.fpd.setPower(0.5);
                robot.fsd.setPower(-0.5);
                robot.bpd.setPower(0.5);
                robot.bsd.setPower(-0.5);
            }else{
                robot.fpd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
                robot.bpd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
                robot.fsd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
                robot.bsd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
                if(Objects.equals(lastArmPress, "down")){
                    robot.portArm.setPower((gamepad1.right_trigger * 1.15) - gamepad1.left_trigger + (gamepad2.right_trigger * 1.15) - gamepad2.left_trigger);
                    robot.starArm.setPower((gamepad1.right_trigger * 1.15) - gamepad1.left_trigger + (gamepad2.right_trigger * 1.15) - gamepad2.left_trigger);
                }else{
                    robot.portArm.setPower((gamepad1.right_trigger * 1.15) - gamepad1.left_trigger + armFeedForward + (gamepad2.right_trigger * 1.15) - gamepad2.left_trigger);
                    robot.starArm.setPower((gamepad1.right_trigger * 1.15) - gamepad1.left_trigger + armFeedForward + (gamepad2.right_trigger * 1.15) - gamepad2.left_trigger);
                }
            }


            /*
            if(robot.portArm.getCurrentPosition()<leftHigh){
                robot.portArm.setPower(-0.1);
                robot.starArm.setPower(-0.1);
            }else{

             */
            //}
            if (gamepad1.dpad_up && gamepad2.dpad_up){
                robot.shoulderPort.setPosition(shoulderPortUp);
                robot.shoulderStar.setPosition(shoulderStarUp);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                robot.extendyBoi.setTargetPosition(extendyBoiRetracted);
                robot.shoulderPort.setPosition(shoulderPortDown);
                robot.shoulderStar.setPosition(shoulderStarDown);
            } else if(gamepad1.dpad_right || gamepad2.dpad_right){
                robot.shoulderPort.setPosition(shoulderPortBar);
                robot.shoulderStar.setPosition(shoulderStarBar);
            }
            /*if (gamepad1.right_bumper){
               robot.extendyBoi.setTargetPosition(extendyBoiExtended);
               robot.extendyBoi.setPower(extendyBoiPower);
            } else if (gamepad1.left_bumper) {
                robot.extendyBoi.setTargetPosition(extendyBoiRetracted);
                robot.extendyBoi.setPower(extendyBoiPower);
            }

             */
            //toggle for extendy boy
            robot.extendyBoi.setTargetPosition((int) toggle(robot.extendyBoi.getTargetPosition(), extendyBoiRetracted, extendyBoiExtended, previousGamepad1.right_bumper, currentGamepad1.right_bumper));
            if ((!previousGamepad1.right_bumper&&currentGamepad1.right_bumper)||(!previousGamepad2.right_bumper&&currentGamepad2.right_bumper)){
                if (robot.extendyBoi.getTargetPosition() == extendyBoiRetracted||robot.extendyBoi.getTargetPosition()==0) {
                    robot.extendyBoi.setTargetPosition(extendyBoiExtended);
                }
                else {
                    extenderTimer = getRuntime();
                    robot.extendyBoi.setTargetPosition(0);
                }
            }
            if(!gamepad1.right_bumper&&!gamepad2.right_bumper){
                extenderTimer = -2000;
            }else if(getRuntime()-extenderTimer>1.5&&(robot.extendyBoi.getTargetPosition()==0)){
                robot.extendyBoi.setTargetPosition(extendyBoiRetracted);
            }
            if(Math.abs(robot.extendyBoi.getCurrent(CurrentUnit.AMPS))>maxCurrent){
                maxCurrent = robot.extendyBoi.getCurrent(CurrentUnit.AMPS);
            }
            if(counter%100 == 0){
                accelerationExtendyBoi = (robot.extendyBoi.getVelocity()-prevVelo)/(runtime-prevRuntime);
                prevVelo = robot.extendyBoi.getVelocity();
                prevRuntime = runtime;
            }
            counter++;
            robot.extendyBoi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("portArm", robot.portArm.getCurrentPosition());
            telemetry.addData("starArm", robot.starArm.getCurrentPosition());
            telemetry.addData("extender", robot.extendyBoi.getCurrentPosition());
            telemetry.addData("armDownOrUp", lastArmPress);
            telemetry.addData("grabTime", grabTime);
            //Hopefully I can use this to prevent breaks
            telemetry.addData("current to ExtendyBoi", robot.extendyBoi.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("speed of ExtendyBoi", robot.extendyBoi.getVelocity());
            telemetry.addData("Max current to ExtendyBoi", maxCurrent);
            telemetry.addData("accelerationExtendyBoi", accelerationExtendyBoi);
            telemetry.update();
        }
    }
}
