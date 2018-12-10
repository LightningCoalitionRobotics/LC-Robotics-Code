package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
    /**
     * Arm motor for the robot.
     */
    public DcMotor arm;

    // Constants
    public static final double WHEEL_DIAMETER = 6;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public HardwareMap hardwareMap;
    private OpMode opMode;

    private ElapsedTime elapsedTime = new ElapsedTime();

    /**
     * Create an instance of Bob Alexander III.
     * @author Noah Simon
     * @param opMode Use <code>this</code>.
     */
    public HardwareBobAlexanderIII(OpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        leftDrive = register("motorLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive = register("motorRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        leftLift = register("lifterLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLift = register("lifterRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm = register("arm", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * A convenience function to configure a {@link DcMotor} in order to use it in an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}.
     * @author Noah Simon
     * @param name The name of the motor configured on the Android devices.
     * @param direction The motor direction that should be set as positive.
     * @param runMode How the motor should interpret commands from the OpMode.
     * @return The motor created.
     */
    private DcMotor register(String name, DcMotorSimple.Direction direction, DcMotor.RunMode runMode) {
        DcMotor motor = hardwareMap.get(DcMotor.class, name);
        motor.setDirection(direction);
        motor.setPower(0);
        if (runMode == DcMotor.RunMode.RUN_USING_ENCODER) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Make sure encoders are reset
        }
        motor.setMode(runMode);
        return motor;
    }

    @Override
    @Deprecated
    public void drive(double speed, double dist, double timeout) {
        // FIXME: Not done yet.
        // Code adapted from org.firstinspires.ftc.teamcode.EncoderAuto#encoderDrive
        boolean stop = false;
        if(opMode instanceof LinearOpMode && ((LinearOpMode) opMode).opModeIsActive()) {
            double target = dist * 30 / WHEEL_CIRCUMFERENCE;
            elapsedTime.reset();
            leftDrive.setPower(0.5);
            rightDrive.setPower(0.5);
            while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout
                    && !stop) {
//                if (leftDrive.getCurrentPosition() >= )
            }
        } else {
            opMode.telemetry.addData("Error", "Attempted to autodrive during teleop.");
        }
    }

    @Override
    public void turn(double speed, double angle, double timeout) {
        // FIXME: Not done yet
    }
}
