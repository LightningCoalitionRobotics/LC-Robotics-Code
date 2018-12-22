package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EncoderAuto;

import static java.lang.Thread.sleep;

/**
 * The class for the LCR 2018-19 robot.
 *
 * If more components are added, please add them to this class!
 *
 * @author Noah Simon
 */
public abstract class HardwareBobAlexanderIII extends Robot {
    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot
    /**
     * ElapsedTime and telemetry
     */
    static final double turnErrorConstant = (1.75);
    private Telemetry telemetry;
    private ElapsedTime runtime = new ElapsedTime();
    /**
     * Left drive motor for the robot.
     */
    public DcMotor motorLeft;
    /**
     * Right drive motor for the robot.
     */
    public DcMotor motorRight;
    /**
     * Left lift motor for the robot.
     */
    public DcMotor leftSpinner;
    /**
     * Right lift motor for the robot.
     */
    public DcMotor rightSpinner;
    /**
     * Arm motor for the robot.
     */
    public DcMotor lifter;

    // Constants
    /**
     *
     */
    public static final double COUNTS_PER_INCH = 3.5;
    /**
     * In milliseconds, how long the robot should wait after each autodrive.
     */
    public static final long SLEEP_AFTER_DRIVE = 2000;



    /**
     * The default drive speed for the robot.
     */
    public double drive_speed = 0.5;

    /**
     *
     */
    public SlowModeType slowModeType = SlowModeType.NORMAL;
    /**
     * Create an instance of Bob Alexander III.
     * @param opMode Use <code>this</code>.
     */
    public HardwareBobAlexanderIII(OpMode opMode) {
        this.opMode = opMode;
    }

    /**
     * Create an instance of Bob Alexander III.
     * @param opMode Use <code>this</code>.
     * @param drive_speed The initial setting for {@link #drive_speed}.
     */
    public HardwareBobAlexanderIII(OpMode opMode, double drive_speed) {
        this(opMode);
        this.drive_speed = drive_speed;

        telemetry = EncoderAuto.getOpModeInstance().telemetry;
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        motorLeft = register("motorLeft", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight = register("motorRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        leftSpinner = register("spinner1", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSpinner = register("spinner1", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifter = register("stronkBoi", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * A convenience function to configure a {@link DcMotor} in order to use it in an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}.
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

    /**
     *
     * {@inheritDoc}
     * @see org.firstinspires.ftc.teamcode.EncoderAuto#encoderDrive(double, double, double, double)
     */


    /*
     *   This function needs to be able to account for prior movements
     *   TODO(Noah Simon) ^
     */


    public void drive(double speed,
                      double leftInches, double rightInches,
                      double timeoutS) {

        this.motorLeft = motorLeft;
        this.motorRight = motorRight;

        double newLeftTarget;
        double newRightTarget;

        boolean leftStop = false;
        boolean rightStop = false;


            // Determine new target position, and pass to motor controller
            newLeftTarget = motorLeft.getCurrentPosition() + (leftInches * COUNTS_PER_INCH);
            newRightTarget = motorRight.getCurrentPosition() + (rightInches * COUNTS_PER_INCH);

            // reset the timeout time and start motion.
            runtime.reset();
            motorRight.setPower(Math.abs(0.5));
            motorLeft.setPower(Math.abs(0.5));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.*/
            while ((runtime.seconds() < timeoutS) &&
                    (!leftStop || !rightStop)) {

                //Stop right motor if it's finished.
                if (motorRight.getCurrentPosition() >= newRightTarget) {

                    motorRight.setPower(0);
                    rightStop = true;

                }

                //Stop left motor if it's finished.
                if ((motorLeft.getCurrentPosition()) >= newLeftTarget) {

                    motorLeft.setPower(0);
                    leftStop = true;

                }


                // Display it for the driver.
                telemetry.addData("Path1 Right, Left", "Running to %7d :%7d", ((int) newRightTarget), ((int) newLeftTarget));
                telemetry.addData("Status Right, Left", "Running at %7d :%7d",

                        motorRight.getCurrentPosition(),
                        (Math.abs((motorLeft.getCurrentPosition()))));

                telemetry.addData("Mode Right", motorRight.getMode());
                telemetry.addData("Mode Left", motorLeft.getMode());
                telemetry.addData("Motor Right", motorRight.isBusy());
                telemetry.addData("Motor Left", motorLeft.isBusy());
                telemetry.update();
            }

            // Stop all motion;
            motorRight.setPower(0);
            motorLeft.setPower(0);

            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();

            try {
                sleep(2000);   // pause after each move
            } catch(InterruptedException e) { telemetry.addData("InterruptedException",e); telemetry.update();}

        }

    /**
     * Drives the robot using the default speed in the specified direction.
     * @param direction True if forward, false if backward.
     * @param dist In inches, the distance the robot should travel.
     * @param timeout In seconds, how long the robot should attempt to reach the target distance.
     * @see #drive(double, double, double)
     * @see org.firstinspires.ftc.teamcode.EncoderAuto#encoderDrive(double, double, double, double)
     */


    /**
     * {@inheritDoc}
     * @see org.firstinspires.ftc.teamcode.EncoderAuto#encoderTurn(double, double, double)
     */

    /**
     *
     * @param angle In degrees, how far the robot should turn. A positive amount is counterclockwise.
     * @param timeout In seconds, how long the robot should attempt to reach the target angle.
     * @see #turn(double, double, double)
     * @see org.firstinspires.ftc.teamcode.EncoderAuto#encoderTurn(double, double, double)
     */

    public void turn(double angle, double timeout) {
        double newLeftTarget;
        double newRightTarget;

        boolean leftStop = false;
        boolean rightStop = false;

        double angleToInches = ((((angle)*(Math.PI)/180.0)*(5.66)*turnErrorConstant)); // Needs to be calibrated (angle -> radians * radius * constant.


            // Determine new target position, and pass to motor controller
            newLeftTarget = (motorLeft.getCurrentPosition() - (angleToInches*COUNTS_PER_INCH));
            newRightTarget = (motorRight.getCurrentPosition()) + (angleToInches*COUNTS_PER_INCH);


            // reset the timeout time and start motion.
            runtime.reset();

            if(angle > 0) {

                motorRight.setPower(Math.abs(0.5));
                motorLeft.setPower((-0.5));

            } else {

                motorRight.setPower((-0.5));
                motorLeft.setPower(Math.abs(0.5));

            }

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.*/
            while ((runtime.seconds() < timeout) &&
                    (!leftStop || !rightStop)) {

                if(angle > 0) {

                    //Stop right motor if it's finished.
                    if (motorRight.getCurrentPosition() >= newRightTarget) {

                        motorRight.setPower(0);
                        rightStop = true;

                    }

                    //Stop left motor if it's finished.
                    if ((motorLeft.getCurrentPosition()) <= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                } else {

                    //Stop right motor if it's finished.
                    if (motorRight.getCurrentPosition() <= newRightTarget) {

                        motorRight.setPower(0);
                        rightStop = true;

                    }

                    //Stop left motor if it's finished.
                    if (((motorLeft.getCurrentPosition())) >= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                }

                // Display it for the driver.
                telemetry.addData("Turn Right, Left",  "Running to %7d :%7d", ((int)newRightTarget),  ((int)newLeftTarget));
                telemetry.addData("Status Right, Left",  "Running at %7d :%7d",

                        motorRight.getCurrentPosition(),
                        (Math.abs((motorLeft.getCurrentPosition()))));

                telemetry.addData("Mode Right", motorRight.getMode());
                telemetry.addData("Mode Left", motorLeft.getMode());
                telemetry.addData("Motor Right", motorRight.isBusy());
                telemetry.addData("Motor Left", motorLeft.isBusy());
                telemetry.update();
            }


            // Stop all motion;
            motorRight.setPower(0);
            motorLeft.setPower(0);

            try {
                sleep(500);
            } catch(InterruptedException e) { telemetry.addData("InterruptedException",e); telemetry.update();}

            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();
        }

    /**
     *
     * @param direction True if counterclockwise, false if clockwise.
     * @param angle In degrees, how far the robot should turn.
     * @param timeout In seconds, how long the robot should attempt to reach the target angle.
     * @see #turn(double, double, double)
     * @see org.firstinspires.ftc.teamcode.EncoderAuto#encoderTurn(double, double, double)
     */
    public void turn(boolean direction, double angle, double timeout) {
        if (direction) {
            turn(angle, timeout);
        } else {
            turn(-angle, timeout);
        }
    }

    /**
     * Spin the spinners for a specified amount of time.
     * @param speed A value from -1 to 1, a higher absolute value meaning a higher speed.
     * @param time In seconds, how long the motors should spin.
     */
    public void autoSpin(double speed, long time) {
        if (opMode instanceof LinearOpMode) {
            leftSpinner.setPower(speed);
            rightSpinner.setPower(speed);
            ((LinearOpMode) opMode).sleep(time * 1000);
            leftSpinner.setPower(0);
            rightSpinner.setPower(0);
        } else {
            throw new UnsupportedOperationException("Attempted to autospin during teleop.");
        }
    }

    /**
     * Spin the spinners for a specified amount of time at the default speed.
     * @param time In seconds, how long the motors should spin.
     * @param direction True if forward, false if backward.
     */
    public void autoSpin(boolean direction, long time) {
        autoSpin(direction ? drive_speed : -drive_speed, time);
    }

    /**
     * Start the spinners in front of the robot.
     * @param speed A value from -1 to 1, a higher absolute value meaning a higher speed.
     */
    public void startSpin(double speed) {
        leftSpinner.setPower(speed);
        rightSpinner.setPower(speed);
    }

    /**
     * Start the spinners in front of the robot at the default speed.
     * @param direction True if forward, false if backward.
     */
    public void startSpin(boolean direction) {
        if (direction) {
            leftSpinner.setPower(drive_speed);
            rightSpinner.setPower(drive_speed);
        } else {
            leftSpinner.setPower(-drive_speed);
            rightSpinner.setPower(-drive_speed);
        }
    }

    /**
     * Stop the spinners in front of the robot.
     */
    public void stopSpin() {
        leftSpinner.setPower(0);
        rightSpinner.setPower(0);
    }

    /**
     * Checks the gamepads to see if A, B, Y, or X have been pressed, If so, it changes the slow mode type accordingly.
     */
    public void checkSlowMode() {
        if (opMode.gamepad1.a || opMode.gamepad2.a) {
            slowModeType = SlowModeType.NORMAL;
        } else if (opMode.gamepad1.b || opMode.gamepad2.b) {
            slowModeType = SlowModeType.HALF;
        } else if (opMode.gamepad1.y || opMode.gamepad2.y) {
            slowModeType = SlowModeType.QUARTER;
        } else if (opMode.gamepad1.x || opMode.gamepad2.x){
            slowModeType = SlowModeType.EIGHTH;
        }
    }
}
