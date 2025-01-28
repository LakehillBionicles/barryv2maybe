package org.firstinspires.ftc.teamcode.V1.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class teleBase extends LinearOpMode {
    protected final Object lock = new Object();

    @Override
    public void runOpMode() throws InterruptedException {

    }

    /**
     * This method is for automating TeleOp
     * It returns a boolean which you can you instead of a while loop
     * Time 1 is the time after the button is pressed for the method to activate.
     * If it happens imediately after button then put in 0
     * Time 2 is the time after the button is pressed for the method to turn off.
     * The comparison is equal to a variable which you set to runtime
     * if(!previousGamepad1.dpad_left && currentGamepad1.dpad_left){
     * grabTime = getRuntime();}
     * else if(previousGamepad1.dpad_left && !currentGamepad1.dpad_left){
     * grabTime = -2000;}
     * This is a good method for setting a variable to runtime.
     * For runtime put in getRuntime(); or a varible which equals getRuntime();
     * I generally use the variable method as it's more efficient if you're calling getRuntime() alot
     */

    public Boolean timerCheck(double comparison, double time1, double time2, double runtime) {
        synchronized (lock) {
            if (time2 < time1) throw new IllegalArgumentException("time 1 is greater than time 2");
            return comparison <= (runtime - time1) && comparison > (runtime - time2);
        }
    }

    //
    public void armPower(DcMotorEx arm1, DcMotorEx arm2, double power) {
        arm1.setPower(power);
        arm2.setPower(power);
    }

    /**
     * WARNING WARNING!!!feedforward is not the power to keep ArmUp!!!WARNING WARNING!!!
     *
     * @param arm1
     * @param arm2
     * @param feedForward1 This is an overshoot for your arm to stay up, basically the weight of the arm will pull it down so we need to set a higher target
     * @param feedForward2
     * @param arm1Tar
     * @param arm2Tar
     */
    public void armToPosition(DcMotorEx arm1, DcMotorEx arm2, Integer feedForward1, Integer feedForward2, Integer arm1Tar, Integer arm2Tar, double power1, double power2) {
        arm1Tar = arm1Tar + feedForward1;
        arm2Tar = arm2Tar + feedForward2;
        arm1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm1.setTargetPosition(arm1Tar);
        arm2.setTargetPosition(arm2Tar);
        arm1.setPower(power1);
        arm2.setPower(power2);
    }

    /**
     * @param motor1 this is just a DcMotor Ex: robot.motor1
     * @param motor2 this is just a DcMotor Ex: robot.motor1
     * @param target this is the position you want it to go to =
     * @return
     */
    public int swingyArmToPosition(DcMotorEx motor1, DcMotorEx motor2, int target) {
        int difference1 = target - motor1.getCurrentPosition();
        int difference2 = target - motor2.getCurrentPosition();
        int averageDifference = (difference1 + difference2) / 2;
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setTargetPosition(averageDifference);
        motor1.setTargetPosition(averageDifference);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return averageDifference;
    }

    public int posOfSwingyArm(int targetPos, DcMotorEx motor1, DcMotorEx motor2) {
        return (targetPos - motor1.getCurrentPosition()) + (targetPos - motor2.getCurrentPosition()) / 2;
    }


    /**
     * Returns position of positon which the motor or servo isn't at and acts as toggle as well
     * Ex: robot.extendyBoi.setTargetPosition((int)toggle(robot.extendyBoi.getTargetPosition(),extendyBoiRetracted,extendyBoiExtended,previousGamepad1.right_bumper,currentGamepad1.right_bumper));
     *
     * @param currentPosition  just put the target pos of servo or motor Ex: robot.extendyBoi.getTargetPosition()
     * @param firstPosition    just put one of the positions for you motor or servo
     * @param secondPosition   just put the other position for you motor or servo
     * @param previousGamepad1 put in the button which toggles it Ex: previousGamepad1.right_bumper
     * @param curentGamepad1   put in the button which toggles it Ex: currentGamepad1.right_bumper
     * @return returns the position the servo or motor should go to
     */
    public double toggle(double currentPosition, double firstPosition, double secondPosition, Boolean previousGamepad1, Boolean curentGamepad1) {
        if (singlePress(previousGamepad1, curentGamepad1)) {
            if (currentPosition == firstPosition) {
                return secondPosition;
            } else {
                return firstPosition;
            }
        } else {
            return currentPosition;
        }
    }

    /**
     * Returns position of positon which the motor or servo isn't at and acts as toggle as well
     * Ex: robot.extendyBoi.setTargetPosition((int)toggle(robot.extendyBoi.getTargetPosition(),extendyBoiRetracted,extendyBoiExtended,previousGamepad1.right_bumper,currentGamepad1.right_bumper));
     *
     * @param currentPosition  just put the target pos of servo or motor Ex: robot.extendyBoi.getTargetPosition()
     * @param firstPosition    just put one of the positions for you motor or servo
     * @param secondPosition   just put the other position for you motor or servo
     * @param previousGamepad1 put in the button which toggles it Ex: previousGamepad1.right_bumper
     * @param curentGamepad1   put in the button which toggles it Ex: currentGamepad1.right_bumper
     * @param previousGamepad2 put in the second previous gamepad's button same thing as the previous one
     * @param currentGamepad2  put in the second gamepad's button same thing as the previous one
     * @return returns the position the servo or motor should go to
     */
    public double toggle(double currentPosition, double firstPosition, double secondPosition, Boolean previousGamepad1, Boolean curentGamepad1, Boolean previousGamepad2, Boolean currentGamepad2) {
        if (singlePress(previousGamepad1, curentGamepad1) || singlePress(previousGamepad2, currentGamepad2) && (curentGamepad1 ^ currentGamepad2)) {
            if (currentPosition == firstPosition) {
                return secondPosition;
            } else {
                return firstPosition;
            }
        } else {
            return currentPosition;
        }
    }

    /**
     * Will only return true if button has just been pressed but not when it was pressed before
     *
     * @param previousGamepad1 Ex: previousGamepad1.right_bumper
     * @param curentGamepad1   Ex: currentGamepad1.right_bumper
     * @return returns true when the button was just pressed and false when the button was not just pressed
     */
    public Boolean singlePress(Boolean previousGamepad1, Boolean curentGamepad1) {
        return !previousGamepad1 && curentGamepad1;
    }

    /**
     * @param previousGamepad1 This is equal to your 1st previous gamepad. Insanity I know.
     * @param previousGamepad2 This is equal to your 2nd previous gamepad. This is code getting even crazier
     * @param currentGamepad1  This is equal to your 1st gampad controls active at the current moment
     * @param currentGamepad2  This is equal to your 2nd gampad controls active at the current moment
     * @param gamepad1         This is the variable which interacts with the driver hub no touchy
     * @param gamepad2         This is the variable which interacts with the driver hub no touchy
     *                         This method is needed to be able to call other methods regarding controllers
     *                         Only call this once at the begining of your whille
     *                         Just steal the parameters from my previous code its easier that way
     */
    public void copyGamepad(Gamepad previousGamepad1, Gamepad previousGamepad2, Gamepad currentGamepad1, Gamepad currentGamepad2, Gamepad gamepad1, Gamepad gamepad2) {
        previousGamepad1.copy(currentGamepad1);//makes previousGamepad equal to the gamepad from the last opmode
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);//makes currentGamepad equal to the actual current gamepad controls
        currentGamepad2.copy(gamepad2);
    }

    public int findAverageEncoderValues(DcMotorEx motor1, DcMotorEx motor2) {
        return (motor1.getCurrentPosition() + motor2.getCurrentPosition()) / 2;
    }

    public String colorSensorDetect(ColorSensor colorSensor, double red, double blue, double tolerance) {
        if (colorSensor.blue() > tolerance||colorSensor.red()>tolerance) {
            if (colorSensor.red() > colorSensor.blue()) {
                return "red";
            } else {
                return "blue";
            }
        } else {
            return "yellow";
        }
    }
    public void oneEncoderTwoMotor(DcMotorEx masterMotor, DcMotorEx slaveMotor, int target){
        masterMotor.setTargetPosition(target);
        slaveMotor.setTargetPosition(masterMotor.getTargetPosition()-masterMotor.getCurrentPosition());
    }
}
