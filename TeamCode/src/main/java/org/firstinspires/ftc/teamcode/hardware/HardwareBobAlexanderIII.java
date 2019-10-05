package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.code201819.EncoderAuto;

/**
 * The class for the LCR 2018-19 robot.
 *
 * If more components are added, please add them to this class!
 *
 * @author Noah Simon
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class HardwareBobAlexanderIII extends Robot {

    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot
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
//    /**
//     * Arm motor for the robot.
//     */
//    public DcMotor stronkboi;
    /**
     * Marker servo for the robot.
     */
    public Servo idolArm;

    // Constants
    /**
     * Number of motor counts to move robot one inch.
     */
    public static final double COUNTS_PER_INCH = 3.5;
    /*
     * Number of motor counts to turn robot 360 degrees.
     */
    public static final double COUNTS_PER_REVOLUTION = 215;
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
     * @param drive_speed The initial setting for {@link #drive_speed}.
     */
    public HardwareBobAlexanderIII(OpMode opMode, double drive_speed) {
        super(opMode);
        this.drive_speed = drive_speed;
    }

    public HardwareBobAlexanderIII(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        motorLeft = registerMotor("motorLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight = registerMotor("motorRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        leftSpinner = registerMotor("leftSpinner", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSpinner = registerMotor("rightSpinner", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        stronkboi = registerMotor("stronkBoi", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        idolArm = registerServo("idol");
    }

    /**
     *
     * {@inheritDoc}
     * @see EncoderAuto#encoderDrive(double, double, double)
     */
    @Override
    public void drive(double speed, double dist, double timeout) {
        // Code adapted from org.firstinspires.ftc.teamcode.EncoderAuto#encoderDrive
        int leftPos = motorLeft.getCurrentPosition();
        int rightPos = motorRight.getCurrentPosition();
        if(opMode instanceof LinearOpMode && ((LinearOpMode) opMode).opModeIsActive()) {
            double target = dist * COUNTS_PER_INCH; //Can be calculated without circumference, just CPI
            elapsedTime.reset();
            motorLeft.setPower(speed);
            motorRight.setPower(speed);
            // Add debug information
            telemetry.addData("Path1 Right, Left",  "Running to %7f", target);
            telemetry.addData("Status Right, Left",  "Running at %7d :%7d",
                    motorRight.getCurrentPosition(), motorLeft.getCurrentPosition());
            telemetry.addData("Speed", speed);
            telemetry.addData("Mode Right", motorRight.getMode());
            telemetry.addData("Mode Left", motorLeft.getMode());
            telemetry.addData("Motor Right", motorRight.isBusy());
            telemetry.addData("Motor Left", motorLeft.isBusy());
            telemetry.update();
            // TODO: Simplify logic
            while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
                if (speed > 0) {
                    if (motorLeft.getCurrentPosition() >= leftPos + target || motorRight.getCurrentPosition() >= rightPos + target) {
                        break;
                    }
                } else if (speed < 0) {
                    if (motorLeft.getCurrentPosition() <= leftPos - target || motorRight.getCurrentPosition() <= rightPos - target) {
                        break;
                    }
                }
                ((LinearOpMode) opMode).idle();
            }
            motorLeft.setPower(0);
            motorRight.setPower(0);
            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();
            ((LinearOpMode) opMode).sleep(SLEEP_AFTER_DRIVE);
        } else {
            telemetry.addData("Error", "Attempted to autodrive during teleop or when opmode is closed.");
            telemetry.update();
            opMode.requestOpModeStop();
        }
    }

    /**
     *
     * Drives the robot forward using the default speed.
     *
     * @param dist In inches, the distance the robot should travel.
     * @param timeout In seconds, how long the robot should attempt to reach the target distance.
     * @see #drive(double, double, double)
     */
    public void drive(double dist, double timeout) {
        drive(drive_speed, dist, timeout);
    }

    /**
     * Drives the robot using the default speed in the specified direction.
     * @param direction True if forward, false if backward.
     * @param dist In inches, the distance the robot should travel.
     * @param timeout In seconds, how long the robot should attempt to reach the target distance.
     * @see #drive(double, double, double)
     */
    public void drive(boolean direction, double dist, double timeout) {
        if (direction) {
            drive(drive_speed, dist, timeout);
        } else {
            drive(-drive_speed, dist, timeout);
        }
    }

    @Override
    public void turn(double speed, double angle, double timeout) {
        double leftPos = motorLeft.getCurrentPosition();
        double rightPos = motorRight.getCurrentPosition();
        if (opMode instanceof LinearOpMode && ((LinearOpMode) opMode).opModeIsActive()) {
            double leftTarget;
            double rightTarget;
            elapsedTime.reset();
            if (angle > 0) {
                rightTarget = COUNTS_PER_REVOLUTION / 360 * angle / 2; // Split angle between both motors
                leftTarget = -COUNTS_PER_REVOLUTION / 360 * angle / 2;
                motorRight.setPower(speed);
                motorLeft.setPower(-speed);
            } else {
                rightTarget = -COUNTS_PER_REVOLUTION / 360 * angle / 2;
                leftTarget = COUNTS_PER_REVOLUTION / 360 * angle / 2;
                motorRight.setPower(-speed);
                motorLeft.setPower(speed);
            }

            while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
                if (angle > 0) {
                    if (motorLeft.getCurrentPosition() <= leftPos + leftTarget || motorRight.getCurrentPosition() >= rightPos + rightTarget) {
                        break;
                    }
                } else {
                    if (motorLeft.getCurrentPosition() >= leftPos + leftTarget || motorRight.getCurrentPosition() <= rightPos + rightTarget) {
                        break;
                    }
                }
            }

            motorLeft.setPower(0);
            motorRight.setPower(0);
            telemetry.addData("Final position left:", motorLeft.getCurrentPosition());
            telemetry.addData("Final position right:", motorRight.getCurrentPosition());
            telemetry.update();

            ((LinearOpMode) opMode).sleep(SLEEP_AFTER_DRIVE);
        } else {
            telemetry.addData("Error", "Attempted to autoturn during teleop or when opmode is closed.");
            telemetry.update();
            opMode.requestOpModeStop();
        }
    }

    /**
     *
     * @param angle In degrees, how far the robot should turn. A positive amount is counterclockwise.
     * @param timeout In seconds, how long the robot should attempt to reach the target angle.
     * @see #turn(double, double, double)
     * @see EncoderAuto#encoderTurn(double, double, double)
     */
    public void turn(double angle, double timeout) {
        turn(drive_speed, angle, timeout);
    }

    /**
     *
     * @param direction True if counterclockwise, false if clockwise.
     * @param angle In degrees, how far the robot should turn.
     * @param timeout In seconds, how long the robot should attempt to reach the target angle.
     * @see #turn(double, double, double)
     * @see EncoderAuto#encoderTurn(double, double, double)
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
        if (opMode instanceof LinearOpMode && ((LinearOpMode) opMode).opModeIsActive()) {
            leftSpinner.setPower(speed);
            rightSpinner.setPower(speed);
            ((LinearOpMode) opMode).sleep(time * 1000);
            leftSpinner.setPower(0);
            rightSpinner.setPower(0);
        } else {
            telemetry.addData("Error", "Attempted to autoturn during teleop or when opmode is closed.");
            telemetry.update();
            opMode.requestOpModeStop();
        }
    }

    /**
     * Spin the spinners for a specified amount of time at the default speed.
     * @param time In seconds, how long the motors should spin.
     * @param direction True if inward, false if outward.
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
