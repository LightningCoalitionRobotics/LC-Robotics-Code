package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * The class for the LCR 2018-19 robot.
 *
 * If more components are added, please add them to this class!
 *
 * @author Noah Simon
 */
public class HardwareBobAlexanderIII {
    public static final String VUFORIA_KEY = "ASSk3sX/////AAABmZWE717QekkdmC87AEyc0L6Na1AyUrqBEaj7NYyPpaf18ZBSjn+obOdkmGLU1q/w7LLf7qJc5azO7JMsOnmafRv5PeDR2pE7SHMjmW00ym0OesQecU0IsHE5LscvJT891aKTdEj+ED8rJLk8WXtotM4trkZvgt7X6bHp35nf1cuCJn7E4niNNdE1FiYADOTNSPTgCMUn1e2U/0x7SEUpMn+uFj/CQTmUMW+UurQi+OV/4FWHCK9Q6HyWmQsgY3BYs7BsEzk5D6PEVOilwvyk9tA7Rni+DjbwJcN/pOkMCucWYgLhIkQEL4/Wg6Aog8uqkRPi4mAB5n+2R+7PXpJzQ12xNBZbAJKJkvn7sPtEelId";
    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot
    public DcMotor leftDrive;
    public DcMotor rightDrive;

    public HardwareMap hardwareMap;

    private ElapsedTime elapsedTime = new ElapsedTime();
    /**
     * Before using the robot, always call this function with the argument <b>hardwareMap</b>.
     * (It's already defined by a superclass.)
     *
     * This function configures the robot to assign specific hardware elements to fields of this class.
     */
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        // Define left motor
        leftDrive = hardwareMap.get(DcMotor.class, "motorLeft");
        leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive.setPower(0);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Define right motor
        rightDrive = hardwareMap.get(DcMotor.class, "motorRight");
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setPower(0);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
