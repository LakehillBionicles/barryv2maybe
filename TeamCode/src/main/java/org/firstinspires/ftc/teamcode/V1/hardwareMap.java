package org.firstinspires.ftc.teamcode.V1;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

@Config
public class hardwareMap {
    public DcMotorEx fpd = null, bpd = null, fsd = null, bsd = null, portArm = null, starArm = null, extendyBoi, leftEncoder = null;
    public Servo shoulderPort = null, shoulderStar = null;
    public CRServo mcLarenDaddy=null;
    HardwareMap hwMap = null;
    public static double shoulderPortDown = 0.475;
    public static double shoulderPortBar = 0.52;
    public static double shoulderPortUp = 0.62;
    public static double shoulderStarDown = 0.525;
    public static double shoulderStarBar = 0.47;
    public static double shoulderStarUp = 0.38;
    public static int extendyBoiExtended = -800;
    public static int extendyBoiRetracted = 80;
    public static double intakePower = -1;
    public static double outakePower = 1;
    public static Integer twoArmBar = (int) (1561*2.548+500);
    public hardwareMap() {}
    public void runOpMode() {}

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;
        fpd = hwMap.get(DcMotorEx.class, "fpd" );
        bpd = hwMap.get(DcMotorEx.class, "bpd");
        fsd = hwMap.get(DcMotorEx.class, "fsd");
        bsd = hwMap.get(DcMotorEx.class, "bsd");
        portArm = hwMap.get(DcMotorEx.class, "pa");
        starArm = hwMap.get(DcMotorEx.class,"sa");
        extendyBoi = hwMap.get(DcMotorEx.class, "extendyBoi");
        shoulderPort = hwMap.get(Servo.class, "portElbow");
        shoulderStar = hwMap.get(Servo.class, "starElbow");
        mcLarenDaddy=hwMap.get(CRServo.class,"hand");
        leftEncoder = hwMap.get(DcMotorEx.class, "pow");
        fpd.setDirection(DcMotorSimple.Direction.REVERSE);
        bpd.setDirection(DcMotorSimple.Direction.REVERSE);
        fsd.setDirection(DcMotorSimple.Direction.FORWARD);
        bsd.setDirection(DcMotorSimple.Direction.FORWARD);
        extendyBoi.setDirection(DcMotorSimple.Direction.FORWARD);

        portArm.setDirection(DcMotorSimple.Direction.FORWARD);
        starArm.setDirection(DcMotorSimple.Direction.REVERSE);

        fpd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bpd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fsd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bsd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        portArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        starArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendyBoi.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fpd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bpd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fsd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bsd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendyBoi.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        portArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        starArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
