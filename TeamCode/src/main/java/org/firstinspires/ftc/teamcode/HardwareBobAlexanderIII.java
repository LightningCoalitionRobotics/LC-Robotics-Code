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
