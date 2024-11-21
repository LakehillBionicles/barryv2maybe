package org.firstinspires.ftc.teamcode.V1.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

@TeleOp
public class distanceSensorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AnalogInput distanceSensor = hardwareMap.get(AnalogInput.class, "distanceSensor");
        waitForStart();
        while (opModeIsActive()){
         telemetry.addData("voltage", distanceSensor.getVoltage());
         telemetry.addData("Inch", (distanceSensor.getVoltage()*32.5)-2.6-2.21);
         telemetry.update();
        }
    }
}
