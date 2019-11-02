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
    public void drive(double speed, double dist, double timeout) {

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
