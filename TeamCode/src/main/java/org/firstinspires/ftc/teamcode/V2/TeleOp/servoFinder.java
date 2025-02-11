package org.firstinspires.ftc.teamcode.V2.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.V2.hardwareMapV2;

@Config
@TeleOp
public class servoFinder extends LinearOpMode{
    public org.firstinspires.ftc.teamcode.V2.hardwareMapV2 robot = new hardwareMapV2();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    public static double pos = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            if(gamepad1.a){
                robot.elbowPort.setPosition(pos);
            }else if(gamepad1.b){
                robot.elbowStar.setPosition(pos);
            }else if(gamepad1.x){
                robot.wrist.setPosition(pos);
            }else if(gamepad1.y){
                robot.mcLarenDaddyPort.setPower(1);
            } else if (gamepad1.dpad_up) {
                robot.mcLarenDaddyStar.setPower(1);
            }else{
                robot.mcLarenDaddyPort.setPower(0);
                robot.mcLarenDaddyPort.setPower(0);
            }
            telemetry.addData("colorSensor", robot.colorSensorHand.blue());
            telemetry.addData("distanceSensor", robot.disanceSensor.getVoltage());
            telemetry.update();
        }
    }
}
