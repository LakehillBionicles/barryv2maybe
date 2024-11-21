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
@Config
@TeleOp
public class roombaBotTeleOpRed extends teleBase {
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
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            runtime = getRuntime();
            copyGamepad(previousGamepad1, previousGamepad2, currentGamepad1, currentGamepad2, gamepad1, gamepad2);
            if(suitcasePos.equals("up")){
                linearFeedForward = 0.1;
            }else if(suitcasePos.equals("mid")){
                linearFeedForward = 0.05;
            }else{
                linearFeedForward = 0;
            }
            linearArmPosition = (findAverageEncoderValues(robot.portArm, robot.starArm)/encoderTicksTillFullExtension)*armLenght;
            //Error correction for two motors so we don't twist axle  :)
            portShoulderError = robot.portSuitcase.getCurrentPosition() - findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase);
            starShoulderError = robot.starSuitcase.getCurrentPosition() - findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase);
            //This is all the different modes
            if(((gamepad1.left_bumper&&gamepad1.right_bumper)||(gamepad2.left_bumper&&gamepad2.right_bumper))&&manualModeTimer<0){
                manualModeTimer = runtime;
            }
            else if(!((gamepad1.left_bumper&&gamepad1.right_bumper)||(gamepad2.left_bumper&&gamepad2.right_bumper))){
                manualModeTimer = -20;
            }
            if(manualModeTimer+4>runtime&&manualModeTimer+3<runtime){
                manualMode = !manualMode;
                manualModeTimer = -20;
                if(manualMode){
                    robot.portSuitcase.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.starSuitcase.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }else{
                    robot.portSuitcase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    robot.starSuitcase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
            }
            //Arm Code
            if(gamepad1.dpad_up||gamepad2.dpad_up){
                suitcasePos = "up";
            }else if(gamepad1.dpad_right||gamepad2.dpad_right){
                suitcasePos = "mid";
            }else if(gamepad1.dpad_down||gamepad2.dpad_down){
                suitcasePos = "down";}
            if(manualMode) {
                if(gamepad1.dpad_up||gamepad2.dpad_up){
                    armPower(1);
                }else if(gamepad1.dpad_down||gamepad2.dpad_down){
                    armPower(-1);
                }else{
                    armPower(0);}
            }else {
                if(portSuitcaseError>1000||portSuitcaseError<-1000||starSuitcaseError>1000||starSuitcaseError<-1000){
                    robot.portSuitcase.setTargetPosition(suitcasePositions.get(suitcasePos));
                    robot.starSuitcase.setTargetPosition(suitcasePositions.get(suitcasePos));
                }
                robot.portSuitcase.setTargetPosition(suitcasePositions.get(suitcasePos)+portSuitcaseError);
                robot.starSuitcase.setTargetPosition(suitcasePositions.get(suitcasePos)+starSuitcaseError);
                armPower(1);
            }
            //Drive Power Code >:(
            //drivePower = ((drivePowerConstant)*Math.cos(Math.toRadians(((double)findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase)/suitcasePositions.get("up"))*90))/(linearArmPosition))+minArmPos;
            drivePower = 1-(Math.sin((Math.toRadians(((double)findAverageEncoderValues(robot.portSuitcase, robot.starSuitcase)/suitcasePositions.get("up"))*90)))*linearArmPosition*drivePowerConstant);
            //elbow code
            if(gamepad1.y){
                elbowPos = "up";
            }else if(gamepad1.b){
                elbowPos = "mid";
            }else if(gamepad1.a){
                elbowPos = "down";}
            robot.elbowPort.setPosition(1-elbowPositions.get(elbowPos));
            robot.elbowStar.setPosition(elbowPositions.get(elbowPos));
            //Intake Code. Manual Mode
            if(manualMode) {
                if (gamepad1.left_bumper||gamepad2.left_bumper) {
                    intake();
                } else if (gamepad1.right_bumper||gamepad2.right_bumper) {
                    outake();}
            //Intake Code. Normal Mode
            }else{
                if(gamepad1.x||gamepad2.x){
                    if(!colorSensorDetect(robot.colorSensorHand, 200, 200, 10).equals("red")){
                        intake();
                    }else{
                        nothingTake();
                        if(!backUpCheck) {
                            backUpTimer = runtime;
                            backUpCheck = true;
                        }
                    }
                }else{
                    backUpTimer = -20;
                    nothingTake();
                }
            }
            //Timers :(
            if(timerCheck(backUpTimer,0,0.5, runtime)){
                elbowPos = "mid";
                setDrivePower();
                setArmPower();
            }else if(timerCheck(backUpTimer, 0.5, 1.1, runtime)){
                armPower(-1);
                wheelPowers(-0.8,-0.8,-0.8,-0.8);
            }else if(timerCheck(backUpTimer, 1.1, 1.9, runtime)) {
                wheelPowers(1,1,-1,-1);
                setArmPower();
                suitcasePos = "up";
                elbowPos = "up";
            }else{
                setDrivePower();
                setArmPower();
            }
        }
    }
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
