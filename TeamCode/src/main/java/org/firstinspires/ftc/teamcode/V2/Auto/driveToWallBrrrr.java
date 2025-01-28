package org.firstinspires.ftc.teamcode.V2.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.V2.hardwareMapV2;
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
@Autonomous
public class driveToWallBrrrr extends LinearOpMode {
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
    public static double thingy = 0.5;
    public static double thingy2 = 0.5;
    public String wristPos = "mid";
    public String mclarenDaddyStatus = "nothing";
    public double autoScoreTimer = -20;
    public double autoDescoreTimer = -20;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        if (opModeIsActive()) {
            resetRuntime();
            while (getRuntime() < 3) {
                robot.fpd.setPower(0.8);
                robot.bpd.setPower(0.8);
                robot.fsd.setPower(0.7);
                robot.bsd.setPower(0.7);
            }
            robot.fpd.setPower(0);
            robot.bpd.setPower(0);
            robot.fsd.setPower(0);
            robot.bsd.setPower(0);
        }
    }
}
