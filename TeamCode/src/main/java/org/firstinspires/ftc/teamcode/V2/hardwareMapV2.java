package org.firstinspires.ftc.teamcode.V2;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

@Config
public class hardwareMapV2 {
    public DcMotorEx fpd = null, bpd = null, fsd = null, bsd = null, portSuitcase = null, starSuitcase = null, portArm = null, starArm = null;
    public Servo elbowPort = null, elbowStar = null, wrist;
    public CRServo mcLarenDaddyPort=null, mcLarenDaddyStar = null;
    public ColorSensor colorSensorHand = null;
    HardwareMap hwMap = null;
    public static double elbowPortDown = 0.475;
    public static double elbowPortUp = 0.62;
    public static double elbowStarDown = 0.525;
    public static double elbowStarBar = 0.47;
    public static double elbowStarUp = 0.38;
    public static HashMap<String, Integer> suitcasePositions = new HashMap<>();
    public static HashMap<String, Double> elbowPositions = new HashMap<>();
    public static double intakeStarPower = -1;
    public static double intakePortPower = 1;
    public static double outakeStarPower = 1;
    public static double outakePortPower = -1;

    public hardwareMapV2() {}
    public void runOpMode() {}

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;
        suitcasePositions.put("up", 1500);
        suitcasePositions.put("mid", 750);
        suitcasePositions.put("down", 0);
        elbowPositions.put("up", 0.6);
        elbowPositions.put("mid", 0.5);
        elbowPositions.put("down", 0.4);
        fpd = hwMap.get(DcMotorEx.class, "fpd" );
        bpd = hwMap.get(DcMotorEx.class, "bpd");
        fsd = hwMap.get(DcMotorEx.class, "fsd");
        bsd = hwMap.get(DcMotorEx.class, "bsd");
        portArm = hwMap.get(DcMotorEx.class, "pa");
        starArm = hwMap.get(DcMotorEx.class,"sa");
        portSuitcase = hwMap.get(DcMotorEx.class, "portSuitcase");
        starSuitcase = hwMap.get(DcMotorEx.class, "starSuitcase");
        elbowPort = hwMap.get(Servo.class, "portElbow");
        elbowStar = hwMap.get(Servo.class, "starElbow");
        mcLarenDaddyPort = hwMap.get(CRServo.class,"portFinger");
        mcLarenDaddyStar = hwMap.get(CRServo.class, "starFinger");
        colorSensorHand = hwMap.get(ColorSensor.class, "colorSensorHand");
        mcLarenDaddyPort.setDirection(DcMotorSimple.Direction.FORWARD);
        mcLarenDaddyStar.setDirection(DcMotorSimple.Direction.REVERSE);
        fpd.setDirection(DcMotorSimple.Direction.FORWARD);
        bpd.setDirection(DcMotorSimple.Direction.FORWARD);
        fsd.setDirection(DcMotorSimple.Direction.REVERSE);
        bsd.setDirection(DcMotorSimple.Direction.REVERSE);
        portSuitcase.setDirection(DcMotorSimple.Direction.FORWARD);
        starSuitcase.setDirection(DcMotorSimple.Direction.REVERSE);
        portArm.setDirection(DcMotorSimple.Direction.FORWARD);
        starArm.setDirection(DcMotorSimple.Direction.REVERSE);
        fpd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bpd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fsd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bsd.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        portArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        starArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        portSuitcase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        starSuitcase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fpd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bpd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fsd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bsd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        portArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        starArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        portSuitcase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        starSuitcase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
