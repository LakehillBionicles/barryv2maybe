package org.firstinspires.ftc.teamcode.V2.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V2.hardwareMapV2;

import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.intakePortPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.intakeStarPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.outakePortPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.outakeStarPower;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.suitcasePositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.elbowPositions;
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
    public int suitcasePos = 0;
    public boolean ManualMode = false;
    public double manualModeTimer = -20;
    double runtime = 0;
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            runtime = getRuntime();
            copyGamepad(previousGamepad1, previousGamepad2, currentGamepad1, currentGamepad2, gamepad1, gamepad2);
            robot.fpd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
            robot.bpd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
            robot.fsd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
            robot.bsd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
            robot.portArm.setPower((gamepad1.right_trigger) - gamepad1.left_trigger + (gamepad2.right_trigger) - gamepad2.left_trigger);
            robot.starArm.setPower((gamepad1.right_trigger) - gamepad1.left_trigger + (gamepad2.right_trigger) - gamepad2.left_trigger);
            //This is all the different modes
            if(((gamepad1.left_bumper&&gamepad1.right_bumper)||(gamepad2.left_bumper&&gamepad2.right_bumper))&&manualModeTimer<0){
                manualModeTimer = runtime;
            }
            else if(!((gamepad1.left_bumper&&gamepad1.right_bumper)||(gamepad2.left_bumper&&gamepad2.right_bumper))){
                manualModeTimer = -20;
            }
            if(manualModeTimer+4>runtime&&manualModeTimer+3<runtime){
                ManualMode = !ManualMode;
                manualModeTimer = -20;
            }
            //Arm Code
            if(gamepad1.dpad_up||gamepad2.dpad_up){
                suitcasePos = suitcasePositions.get("up");
            }else if(gamepad1.dpad_right||gamepad2.dpad_right){
                suitcasePos = suitcasePositions.get("right");
            }else if(gamepad1.dpad_down||gamepad2.dpad_down){
                suitcasePos = suitcasePositions.get("down");}

            robot.portSuitcase.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
            robot.starSuitcase.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
            //elbow code
            if(gamepad1.y){
                robot.elbowStar.setPosition(elbowPositions.get("up"));
            }else if(gamepad1.b){
                robot.elbowStar.setPosition(elbowPositions.get("mid"));
            }else if(gamepad1.a){
                robot.elbowStar.setPosition(elbowPositions.get("down"));}
            //Intake Code. Manual Mode
            if(ManualMode) {
                if (gamepad1.left_bumper||gamepad2.left_bumper) {
                    intake();
                } else if (gamepad1.right_bumper||gamepad2.right_bumper) {
                    outake();}
                //Intake Code. Normal Mode
            }else{
                if(gamepad1.x||gamepad2.x){
                    if(colorSensorDetect(robot.colorSensorHand, 200, 200, 10).equals("red")){
                        intake();
                    }
                }
            }
        }
    }
    private void intake(){
        robot.mcLarenDaddyStar.setPower(intakeStarPower);
        robot.mcLarenDaddyPort.setPower(intakePortPower);}
    private void outake(){
        robot.mcLarenDaddyStar.setPower(outakeStarPower);
        robot.mcLarenDaddyPort.setPower(outakePortPower);}
}
