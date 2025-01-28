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
public class automatedTeleOpIvy extends teleBase{
    public org.firstinspires.ftc.teamcode.V2.hardwareMapV2 robot = new hardwareMapV2();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    //variables
    double drivePower = 1;
    double forwardsGoTime = -20;
    boolean forwardsGoCheck = false;
    double runtime = 0;

    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.a&&timerCheck(forwardsGoTime, -0.1, 1, runtime)){
                forwardsGoTime = runtime;
                forwardsGoCheck = false;
                setDrivePower();
                if(!forwardsGoCheck){
                    forwardsGoTime = runtime;
                    forwardsGoCheck = true;
                }

            }

        }
    }
            private void setDrivePower(){
                robot.fpd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
                robot.bpd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
                robot.fsd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
                robot.bsd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);}

}
