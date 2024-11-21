package org.firstinspires.ftc.teamcode.V1.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.V1.hardwareMap;
@TeleOp
public class odoTest extends LinearOpMode {
    public org.firstinspires.ftc.teamcode.V1.hardwareMap robot = new hardwareMap();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.fsd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.bsd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("pow", robot.leftEncoder.getCurrentPosition());
            telemetry.addData("sow", robot.bsd.getCurrentPosition());
            telemetry.addData("bow", robot.fsd.getCurrentPosition());
            telemetry.update();
        }
    }
}
