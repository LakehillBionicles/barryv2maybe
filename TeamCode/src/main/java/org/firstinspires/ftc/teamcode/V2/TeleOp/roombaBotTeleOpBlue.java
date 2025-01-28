package org.firstinspires.ftc.teamcode.V2.TeleOp;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V2.hardwareMapV2;
import org.opencv.core.Mat;

import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.intakePortPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.intakeStarPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.outakePortPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.outakeStarPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.suitcasePositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.elbowPositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.wristPositions;

@Config
@TeleOp
public class roombaBotTeleOpBlue extends teleBase {
    public org.firstinspires.ftc.teamcode.V2.hardwareMapV2 robot = new hardwareMapV2();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    //variables
    double drivePower = 1;
    double skibidyOhioRizz = 0;
    double shoulderHeight = 0;
    double portShoulderError = 0;
    double starShoulderError = 0;
    public String suitcasePos = "down";
    public int portSuitcaseError = 0;
    public int starSuitcaseError = 0;
    public String elbowPos = "up";
    public boolean manualMode = false;
    public double manualModeTimer = -20;
    public double backUpTimer = -20;
    public Boolean backUpCheck = false;
    double linearArmPosition = 0;
    double drivePowerConstant = 0.4;
    double runtime = 0;
    double linearFeedForward = 0;
    double armLenght = 0;
    double encoderTicksTillFullExtension = 0;
    double minArmPos = 0;//This is the minimum arm position we can be at before we start tipping
    public static double  thingy = 0.5;
    public static double thingy2 = 0.5;
    public String wristPos = "mid";
    public String mclarenDaddyStatus = "nothing";
    public double autoScoreTimer = -20;
    public double autoDescoreTimer = -20;
    public static double pterm = -1.5;
    public static double pterm2 = -0.5;
    public double intakeArmOnTimer = -20;
    public double elbowUpTimer = -20;
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {//skibidi skibidi hawk tuah hawk
            runtime = getRuntime();//lower taper fade
           /* copyGamepad(previousGamepad1, previousGamepad2, currentGamepad1, currentGamepad2, gamepad1, gamepad2);
            if(suitcasePos.equals("up")){
                linearFeedForward = 0.05;
            }else if(suitcasePos.equals("mid")){
                linearFeedForward = 0.025;
            }else{
                linearFeedForward = 0;
            }*/
            //linearArmPosition = robot.starArm.getCurrentPosition()/(encoderTicksTillFullExtension*armLenght);
            //Error correction for two motors so we don't twist axle  :)
            //portShoulderError = robot.portSuitcase.getCurrentPosition() - findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase);
            //starShoulderError = robot.starSuitcase.getCurrentPosition() - findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase);
            //This is all the different modes
            if (((gamepad1.left_bumper && gamepad1.right_bumper) || (gamepad2.left_bumper && gamepad2.right_bumper)) && manualModeTimer < 0) {
                manualModeTimer = runtime;
            } else if (((previousGamepad1.left_bumper&&!currentGamepad1.left_bumper)||(previousGamepad1.right_bumper&&currentGamepad1.right_bumper))||(previousGamepad2.left_bumper&&!currentGamepad2.left_bumper)||(previousGamepad2.right_bumper&&!currentGamepad2.right_bumper)) {
                manualModeTimer = -20;
            }
            if (manualModeTimer + 1 > runtime && manualModeTimer + 0.5 < runtime) {
                manualMode = true;
                manualModeTimer = -20;
                if (manualMode) {
                    robot.portSuitcase.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.starSuitcase.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                } else {
                    robot.portSuitcase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    robot.starSuitcase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
            //Arm Code
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                suitcasePos = "up";
                if((!previousGamepad1.dpad_up&&currentGamepad1.dpad_up)||(!previousGamepad2.dpad_up&&currentGamepad2.dpad_up)) {
                    elbowUpTimer = getRuntime();
                    elbowPos = "down";
                }
                if((!previousGamepad1.dpad_up&&currentGamepad1.dpad_up)||(!previousGamepad2.dpad_up&&currentGamepad2.dpad_up)){
                    autoScoreTimer = getRuntime();
                }
            }else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                suitcasePos = "down";
                autoScoreTimer = -20;
            }else{
                autoScoreTimer = -20;
            }
            if(timerCheck(elbowUpTimer, 1, 1.5, runtime)){
                elbowPos = "mid";
            }
            if (manualMode) {
                robot.starSuitcase.setPower(gamepad2.left_stick_y);
                robot.portSuitcase.setPower(gamepad2.left_stick_y);
            } else {
                if(suitcasePositions.get(suitcasePos)> robot.starSuitcase.getCurrentPosition()) {
                    robot.starSuitcase.setPower((suitcasePositions.get(suitcasePos) - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get(suitcasePos) - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                }else{
                    robot.starSuitcase.setPower((suitcasePositions.get(suitcasePos) - robot.starSuitcase.getCurrentPosition()) * (pterm2 / 1000));
                    robot.portSuitcase.setPower((suitcasePositions.get(suitcasePos) - robot.starSuitcase.getCurrentPosition()) * (pterm2 / 1000));
                }
                //robot.starSuitcase.setPower(robot.starSuitcase.getTargetPosition()/robot.starSuitcase.getCurrentPosition());
                //robot.portSuitcase.setPower(robot.starSuitcase.getTargetPosition()/robot.starSuitcase.getCurrentPosition());
            }
            //Drive Power Code >:(
            //drivePower = ((drivePowerConstant)*Math.cos(Math.toRadians(((double)findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase)/suitcasePositions.get("up"))*90))/(linearArmPosition))+minArmPos;
            //drivePower = 1-(Math.sin((Math.toRadians(((double)findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase)/suitcasePositions.get("up"))*90)))*linearArmPosition*drivePowerConstant);
            //elbow code
            if (gamepad1.y||gamepad2.y) {
                elbowPos = "up";
            } else if (gamepad1.b||gamepad2.b) {
                wristPos = "mid";
                elbowPos = "mid";
            } else if (gamepad1.a||gamepad2.a) {
                elbowPos = "down";
                wristPos = "mid";
            }
            if(gamepad1.dpad_left||gamepad2.dpad_left){
                wristPos = "left";
            }else if(gamepad1.dpad_right||gamepad2.dpad_right){
                wristPos = "right";
            }
            //Intake Code. Manual Mode
            if(robot.portArm.getPower()!=0&&robot.starArm.getPower()!=0){
                intakeArmOnTimer = getRuntime();}
            if(manualMode) {
                if (gamepad1.left_bumper||gamepad2.left_bumper) {//getting jiggy wit it
                    mclarenDaddyStatus = "intake 145";
                    intake();//Bye Patrick!
                } else if (gamepad1.right_bumper||gamepad2.right_bumper) {//Austin claps funny
                    mclarenDaddyStatus = "outake 148";
                    outake();}//
                //Intake Code. Normal Mode
            }else{
                if(gamepad1.x||(timerCheck(intakeArmOnTimer, -0.1, 1, runtime)&&suitcasePos.equals("down"))){
                    if(!colorSensorDetect(robot.colorSensorHand, 200, 200, 200).equals("blue")&& !(robot.colorSensorHand.green() >150)){
                        mclarenDaddyStatus = "intake 154";
                        intake();
                    }else{
                        mclarenDaddyStatus = "nothing 157";
                        nothingTake();
                        if(!backUpCheck) {
                            backUpTimer = runtime;
                            backUpCheck = true;
                        }

                    }
                    if(!gamepad1.x&&(timerCheck(intakeArmOnTimer, -0.1, 1, runtime)&&suitcasePos.equals("down"))){
                        backUpTimer = -20;
                        backUpCheck = false;
                    }
                }else{
                    backUpTimer = -20;
                    backUpCheck = false;
                    mclarenDaddyStatus = "nothing 167";
                    nothingTake();
                    if (gamepad1.right_bumper||gamepad2.right_bumper) {//Austin claps funny
                        mclarenDaddyStatus = "outake 170";
                        outake();
                        if ((!previousGamepad1.right_bumper&&currentGamepad1.right_bumper)||(!previousGamepad2.right_bumper&&currentGamepad2.right_bumper)){
                            autoDescoreTimer = runtime;
                        }
                    }else{
                        autoDescoreTimer = -20;
                    }
                }
            }
            //Timers :(
            if(timerCheck(backUpTimer,0.2,0.7, runtime)){
                elbowPos = "mid";
                wristPos = "mid";
                setDrivePower();
                armPower(-0.2);
            }else if(timerCheck(backUpTimer, 0.7, 1.3, runtime)){
                armPower(-1);
                wheelPowers(-0.8,-0.8,-0.8,-0.8);
                }else if(timerCheck(backUpTimer, 1.3, 1.6, runtime)) {
                wheelPowers(1,1,-1,-1);
                suitcasePos = "up";
                elbowPos = "mid";
            }else if(timerCheck(autoDescoreTimer, 0.5, 1, runtime)){
                elbowPos = "down";
                armPower(-1);
            }else if(timerCheck(autoDescoreTimer, 1, 1.5, runtime)){
                armPower(-1);
            }else if(timerCheck(autoDescoreTimer, 1.5, 3, runtime)){
                suitcasePos = "down";
                wheelPowers(0.7,0.7, 1,1);
                //setArmPower();
            }else if(timerCheck(autoDescoreTimer, 3, 3.3, runtime)){
                wheelPowers(1, 1, -1, -1);
            }
            else{
                setDrivePower();
                setArmPower();
            }
            telemetry.addData("starHeight",robot.starSuitcase.getCurrentPosition()); //austin is a good teacher
            telemetry.addData("portHeight", robot.portSuitcase.getCurrentPosition());
            telemetry.addData("portArmHeigh", robot.portArm.getCurrentPosition());
            telemetry.addData("starArmHeight", robot.starArm.getCurrentPosition());
            telemetry.addData("manualMode", manualMode);
            telemetry.addData("gamepad1.left_stick_x", gamepad1.left_stick_x);
            telemetry.addData("mclarenDaddyStatus", mclarenDaddyStatus);
            telemetry.addData("armPower", (suitcasePositions.get(suitcasePos)-robot.starSuitcase.getCurrentPosition())*(1/528));
            telemetry.addData("autoDescore", autoDescoreTimer);
            telemetry.update();
            robot.elbowPort.setPosition(elbowPositions.get(elbowPos));
            robot.elbowStar.setPosition(1-elbowPositions.get(elbowPos));
            robot.wrist.setPosition(wristPositions.get(wristPos));
        }
    }//Zesty hands
    private void intake(){
        robot.mcLarenDaddyStar.setPower(intakeStarPower);
        robot.mcLarenDaddyPort.setPower(intakePortPower);}
    private void outake(){
        robot.mcLarenDaddyStar.setPower(outakeStarPower);
        robot.mcLarenDaddyPort.setPower(outakePortPower);}
    private void nothingTake(){
        robot.mcLarenDaddyStar.setPower(0);
        robot.mcLarenDaddyPort.setPower(0);}
    private void setArmPower(){
        robot.portArm.setPower(gamepad1.right_trigger - gamepad1.left_trigger + gamepad2.right_trigger - gamepad2.left_trigger + linearFeedForward);
        robot.starArm.setPower(gamepad1.right_trigger - gamepad1.left_trigger + gamepad2.right_trigger - gamepad2.left_trigger + linearFeedForward);}
    private void setDrivePower(){
        robot.fpd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
        robot.bpd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
        robot.fsd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
        robot.bsd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);}
    private void wheelPowers(double fpd, double bpd, double fsd, double bsd){
        robot.fpd.setPower(fpd);
        robot.bpd.setPower(bpd);
        robot.fsd.setPower(fsd);
        robot.bsd.setPower(bsd);}
    private void armPower(double power){
        robot.portArm.setPower(power);
        robot.starArm.setPower(power);}
}
