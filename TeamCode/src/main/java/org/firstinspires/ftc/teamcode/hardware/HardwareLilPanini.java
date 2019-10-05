package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * The class for the LCR 2019-20 robot.
 *
 * If more components are added, please add them to this class!
 *
 * @author Noah Simon
 */
public class HardwareLilPanini extends Robot {

    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot

    public DcMotorController frontController;

    public DcMotorController rearController;

//    public DcMotor motorFrontLeft;
//
//    public DcMotor motorFrontRight;
//
//    public DcMotor motorBackLeft;
//
//    public DcMotor motorBackRight;

    public HardwareLilPanini(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
//        motorFrontLeft = registerMotor("motorFrontLeft", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
//        motorFrontRight = registerMotor("motorFrontRight", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
//        motorBackLeft = registerMotor("motorBackLeft", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
//        motorBackRight = registerMotor("motorBackRight", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        frontController = hardwareMap.get(DcMotorController.class, "frontController");
        rearController = hardwareMap.get(DcMotorController.class, "rearController");
    }

    @Override
    public void drive(double speed, double dist, double timeout) {
        int
    }

    public void crabDrive(HorizontalDirection direction, double speed, double dist, double timeout) {

    }

    @Override
    public void turn(double speed, double dist, double timeout) {

    }

    public enum HorizontalDirection {
        RIGHT,
        LEFT;
    }
}
