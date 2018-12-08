package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * The class for the LCR 2018-19 robot.
 *
 * If more components are added, please add them to this class!
 *
 * @author Noah Simon
 */
public class HardwareBobAlexanderIII implements IBotHardware {
    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot
    /**
     * Left drive motor for the robot.
     */
    public DcMotor leftDrive;
    /**
     * Right drive motor for the robot.
     */
    public DcMotor rightDrive;
    /**
     * Left lift motor for the robot.
     */
    public DcMotor leftLift;
    /**
     * Right lift motor for the robot.
     */
    public DcMotor rightLift;

    public HardwareMap hardwareMap;

    private ElapsedTime elapsedTime = new ElapsedTime();

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        leftDrive = register("motorLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive = register("motorRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        leftLift = register("lifterLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLift = register("lifterRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private DcMotor register(String name, DcMotorSimple.Direction direction, DcMotor.RunMode runMode) {
        DcMotor motor = hardwareMap.get(DcMotor.class, name);
        motor.setDirection(direction);
        motor.setPower(0);
        motor.setMode(runMode);
        return motor;
    }
}
