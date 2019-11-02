package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

    private static final int COUNTS_PER_REVOLUTION = 1400;
    private static final int COUNTS_PER_FORWARD_INCH = COUNTS_PER_REVOLUTION / 12; // 1 revolution is very close to 1 foot

    private static final int COUNTS_PER_360 = 10000;
    private static final int COUNTS_PER_DEGREE = COUNTS_PER_360 / 360;

    private static final int COUNTS_PER_SIDE_FOOT = 2000;
    private static final int COUNTS_PER_SIDE_INCH = COUNTS_PER_SIDE_FOOT/12;

    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot

    public DcMotor motorFrontLeft;

    public DcMotor motorFrontRight;

    public DcMotor motorBackLeft;

    public DcMotor motorBackRight;

    public HardwareLilPanini(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        motorFrontLeft = registerMotor("motorFrontLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight = registerMotor("motorFrontRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft = registerMotor("motorRearLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight = registerMotor("motorRearRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    /**
     * Drive the robot forward or backwards.
     * @param speed An integer between -1 and 1, greater distance from origin is greater speed, negative is backwards and positive is forwards
     * @param dist Distance, in inches, that you want the robot to go
     * @param timeout If dist is never reached for whatever reason, how many seconds before stopping where it is
     */
    public void drive(double speed, double dist, double timeout) {
        int distInCounts = (int)(dist * COUNTS_PER_FORWARD_INCH);

        // Target count value for each motor given dist, calulated from current position plus distance in counts
        int topRightTargetForward = motorFrontRight.getCurrentPosition() + distInCounts;
        int topLeftTargetForward = motorFrontLeft.getCurrentPosition() + distInCounts;
        int bottomRightTargetForward = motorBackRight.getCurrentPosition() + distInCounts;
        int bottomLeftTargetForward = motorBackLeft.getCurrentPosition() + distInCounts;
        int topRightTargetBackward = motorFrontRight.getCurrentPosition() - distInCounts;
        int topLeftTargetBackward = motorFrontLeft.getCurrentPosition() - distInCounts;
        int bottomRightTargetBackward = motorBackRight.getCurrentPosition() - distInCounts;
        int bottomLeftTargetBackward = motorBackLeft.getCurrentPosition() - distInCounts;

        if (speed < 0) { // if forwards

            while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) { //while thing going
                if (motorFrontRight.getCurrentPosition() <= topRightTargetForward || motorFrontLeft.getCurrentPosition() <= topLeftTargetForward || motorBackRight.getCurrentPosition() <= bottomRightTargetForward  || motorBackLeft.getCurrentPosition() <= bottomLeftTargetForward) {
                    motorFrontRight.setPower(speed);
                    motorFrontLeft.setPower(speed);
                    motorBackRight.setPower(speed);
                    motorBackLeft.setPower(speed);
                }
            }
        }
        else (speed > 0) {

        }
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);

    }

    /**
     * Drive the robot at a particular angle.
     * @param degrees The angle at which to move the robot. Measured in degrees above the positive X axis.
     * @param speed How fast the robot should move. Number should be in range (0, 1].
     * @param dist How far, in inches, to move the robot.
     * @param timeout If dist is never reached, how many seconds to wait before stopping.
     */
    public void driveAngle(double degrees, double speed, double dist, double timeout) {

    }

    /**
     * Strafe the robot left or right.
     * @param direction The direction in which to move the robot.
     * @param speed How fast the robot should move. Number should be in range (0, 1].
     * @param dist How far, in inches, to move the robot.
     * @param timeout If dist is never reached, how many seconds to wait before stopping.
     */
    public void strafe(HorizontalDirection direction, double speed, double dist, double timeout) {
        int distInCounts = (int)(dist * COUNTS_PER_SIDE_INCH);

        int correctDirection;
        if (direction == HorizontalDirection.LEFT) {
            correctDirection = 1;
        } else {
            correctDirection = -1;
        }

        int topRightTarget = motorFrontRight.getCurrentPosition() + correctDirection * distInCounts;
        int topLeftTarget = motorFrontLeft.getCurrentPosition() - correctDirection * distInCounts;
        int bottomLeftTarget = motorBackLeft.getCurrentPosition() + correctDirection * distInCounts;
        int bottomRightTarget = motorBackRight.getCurrentPosition() - correctDirection * distInCounts;

        motorFrontRight.setPower(speed * correctDirection);
        motorFrontLeft.setPower(-speed * correctDirection);
        motorBackLeft.setPower(speed * correctDirection);
        motorBackRight.setPower(-speed * correctDirection);

        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
            if (direction == HorizontalDirection.LEFT) {
                if (motorFrontRight.getCurrentPosition() >= topRightTarget || motorFrontLeft.getCurrentPosition() <= topLeftTarget || motorBackLeft.getCurrentPosition() >= bottomLeftTarget || motorBackRight.getCurrentPosition() <= bottomRightTarget) {
                    break;
                }
            } else {
                if (motorFrontRight.getCurrentPosition() <= topRightTarget || motorFrontLeft.getCurrentPosition() >= topLeftTarget || motorBackLeft.getCurrentPosition() <= bottomLeftTarget || motorBackRight.getCurrentPosition() >= bottomRightTarget) {
                    break;
                }
            }
            ((LinearOpMode) opMode).idle();
        }

        stop();
    }

    @Override
    public void turn(double speed, double angle, double timeout) {
    int angleInCounts = (int)(angle * COUNTS_PER_DEGREE);
    //changes the angle variable from degrees to counts

    int topRightTarget = motorFrontRight.getCurrentPosition() + angleInCounts;
    int topLeftTarget = motorFrontLeft.getCurrentPosition() - angleInCounts;
    int bottomLeftTarget = motorBackLeft.getCurrentPosition() - angleInCounts;
    int bottomRightTarget = motorBackRight.getCurrentPosition() + angleInCounts;
    //finds target number of counts for each motor

    if (angle > 0) {
        motorFrontRight.setPower(speed);
        motorFrontLeft.setPower(-speed);
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(speed);
    //sets rights motors to positive and left to negtive for counterclockwise turn
    } else {
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(speed);
        motorBackLeft.setPower(speed);
        motorBackRight.setPower(-speed);
    //sets left motors to positive and right to negative for clockwise turn
    }


        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout){
            if (angle > 0){
                if (motorFrontRight.getCurrentPosition() >= topRightTarget || motorFrontLeft.getCurrentPosition() <= topLeftTarget || motorBackLeft.getCurrentPosition() <= bottomLeftTarget || motorBackRight.getCurrentPosition() >= bottomRightTarget) {
                    break;
                }
            } else {
                if (motorFrontRight.getCurrentPosition() <= topRightTarget || motorFrontLeft.getCurrentPosition() >= topLeftTarget || motorBackLeft.getCurrentPosition() >= bottomLeftTarget || motorBackRight.getCurrentPosition() <= bottomRightTarget) {
                    break;
                }
            }
            ((LinearOpMode) opMode).idle();
        }
        stop();
        //tells motors to stop if they've reached target number of counts
    }

    public void stop() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
    }

    public enum HorizontalDirection {
        RIGHT,
        LEFT
    }
}
