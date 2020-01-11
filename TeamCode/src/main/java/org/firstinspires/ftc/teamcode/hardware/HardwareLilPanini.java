package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    // These are constants that we have experimentally determined, relating counts (the way an encoder measures movement) to inches or degrees (the way we understand movement)
    private static final int COUNTS_PER_REVOLUTION = 1400;                         // One full revolution of a wheel is 1400 counts
    private static final int COUNTS_PER_FORWARD_INCH = COUNTS_PER_REVOLUTION / 12; // 1 revolution FORWARDS is very close to 1 foot, so to get counts per inch, take counts per revolution and divide it by 12

    private static final int COUNTS_PER_360 = 10000;                               // One full turn 360 degrees is 10000 counts
    private static final int COUNTS_PER_DEGREE = COUNTS_PER_360 / 360;

    private static final int COUNTS_PER_SIDE_FOOT = 2000;                          // The amount of counts per the robot moving to the SIDE 1 foot is 2000, NOTICE this is different than the amount of counts going forward or backwards
    private static final int COUNTS_PER_SIDE_INCH = COUNTS_PER_SIDE_FOOT / 12;

    // mm per inch: 25.4
    public static final float CAMERA_FORWARD_DISPLACEMENT_MM = 160;
    public static final float CAMERA_VERTICAL_DISPLACEMENT_MM = 180;
    public static final float CAMERA_LEFT_DISPLACEMENT_MM = 110;

    public static final double EXTENSION_TIME = 2; // Not yet determined (Will be inches per a full extension of drawer slide), public so other people can plug this in as distance

    public static final int DRAWER_SLIDE_TOP_POSITION = 0; //not determined
    public static final int DRAWER_SLIDE_BOTTOM_POSITION = 0; //not determined

    public static final double GRABBER_TIME = 2;

    // Not experimentally determined:
    private static final int COUNTS_PER_45_INCH = (int) Math.hypot(COUNTS_PER_FORWARD_INCH, COUNTS_PER_SIDE_INCH);

    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot are declared here

    public DcMotor motorFrontLeft;

    public DcMotor motorFrontRight;

    public DcMotor motorBackLeft;

    public DcMotor motorBackRight;

    public DcMotor motorDrawerSlide;

    public DcMotor grabber;

    public HardwareLilPanini(OpMode opMode) {
        super(opMode);
    }

    @Override  // Since this class extends the class Robot, these @Overrides let the code know that this will supercede any conflicting properties of init present in class Robot
    public void init(HardwareMap hardwareMap) { //This section registers the motors to the encoders and sets their default direction
        this.hardwareMap = hardwareMap;
        motorFrontLeft = registerMotor("motorFrontLeft", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight = registerMotor("motorFrontRight", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER); //this direction is reverse because the motor is backward, so to make it go forwards you (if you had this forwards) would have to set a negative speed
        motorBackLeft = registerMotor("motorRearLeft", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight = registerMotor("motorRearRight", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER); // Same problem as above with this motor
        motorDrawerSlide = registerMotor("motorDrawerSlide", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        grabber = registerMotor("grabber", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    /**
     * Drive the robot forward or backwards.
     * @param speed An integer between -1 and 1, greater distance from origin is greater speed, negative is backwards and positive is forwards
     * @param dist Distance, in inches, that you want the robot to go (always positive)
     * @param timeout How many seconds before stopping wherever it is
     */
    @Override
    public void drive(double speed, double dist, double timeout) {
        int distInCounts = (int) (dist * COUNTS_PER_FORWARD_INCH); //convert distance from human inches to motor counts


        // Target count value for each motor given dist, calculated from current position in counts plus (or minus if going backwards) distance in counts
        int topRightTarget = motorFrontRight.getCurrentPosition() + distInCounts;
        int topLeftTarget = motorFrontLeft.getCurrentPosition() + distInCounts;
        int bottomRightTarget = motorBackRight.getCurrentPosition() + distInCounts;
        int bottomLeftTarget = motorBackLeft.getCurrentPosition() + distInCounts;

        motorFrontRight.setPower(speed); //set motors to speed
        motorFrontLeft.setPower(speed);
        motorBackRight.setPower(speed);
        motorBackLeft.setPower(speed);

        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) { //while opmode active and timeout not reached
            if (speed > 0) { // if you want the robot to go forwards (positive speed)
                if (motorFrontRight.getCurrentPosition() >= topRightTarget || motorFrontLeft.getCurrentPosition() >= topLeftTarget || motorBackRight.getCurrentPosition() >= bottomRightTarget || motorBackLeft.getCurrentPosition() >= bottomLeftTarget) { //if at or beyond target
                    break; //break from while loop and move on to stop()
                } else {
                    ((LinearOpMode)opMode).idle();
                }
            }
            else if (speed < 0) { // if you want the robot to go backwards (negative speed)
                if (motorFrontRight.getCurrentPosition() <= topRightTarget || motorBackRight.getCurrentPosition() <= topLeftTarget || motorBackRight.getCurrentPosition() <= bottomRightTarget || motorBackLeft.getCurrentPosition() <= bottomLeftTarget) { //if at or beyond target
                    break; //break from while loop and move on to stop()
                } else {
                    ((LinearOpMode)opMode).idle();
                }
            }
            ((LinearOpMode) opMode).idle();
        }
        stop();
    }

    /**
     * Drive the robot at a particular angle.
     * @param degrees The angle at which to move the robot. Measured in degrees above the positive X axis. Do not type 90.
     * @param speed How fast the robot should move. Number should be in range (0, 1].
     * @param dist How far, in inches, to move the robot.
     * @param timeout If dist is never reached, how many seconds to wait before stopping.
     */
    public void driveAngle(double degrees, double speed, double dist, double timeout) {
        double a; // top left and bottom right
        double b; // top right and bottom left

        double tangent = Math.tan(degrees);

        a = 1;
        // tan theta = a+b / a-b
        // tangent = 1+b/1-b
        // tangent - tangent*b = 1+b
        // tangent = (tangent+1)b + 1
        // (tangent - 1)/(tangent + 1) = b
        b = (tangent - 1)/(tangent + 1);

        if (Math.abs(b) > 1) {
            a /= Math.abs(b);
            b /= Math.abs(b);
        }

        // Find targets for motors

        int aDistInCounts = (int)(dist * Math.sin(45 + degrees) * COUNTS_PER_45_INCH);
        int bDistInCounts = (int)(dist * Math.cos(45 + degrees) * COUNTS_PER_45_INCH);

        int topRightTarget = motorFrontRight.getCurrentPosition() + bDistInCounts;
        int topLeftTarget = motorFrontLeft.getCurrentPosition() + aDistInCounts;
        int bottomLeftTarget = motorBackLeft.getCurrentPosition() + bDistInCounts;
        int bottomRightTarget = motorBackRight.getCurrentPosition() + aDistInCounts;

        if (degrees != 90) { //if degrees are not equal to 90, continue with driveAngle, if they are equal to 90, just use drive with speed and dist
            motorFrontRight.setPower(b * speed);
            motorFrontLeft.setPower(a * speed);
            motorBackLeft.setPower(b * speed);
            motorBackRight.setPower(a * speed);

            while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
                if (a > 0 && b > 0) {
                    if (motorFrontRight.getCurrentPosition() >= topRightTarget || motorFrontLeft.getCurrentPosition() >= topLeftTarget || motorBackLeft.getCurrentPosition() >= bottomLeftTarget || motorBackRight.getCurrentPosition() >= bottomRightTarget) {
                        break;
                    }
                } else if (a > 0 && b < 0) {
                    if (motorFrontRight.getCurrentPosition() <= topRightTarget || motorFrontLeft.getCurrentPosition() >= topLeftTarget || motorBackLeft.getCurrentPosition() <= bottomLeftTarget || motorBackRight.getCurrentPosition() >= bottomRightTarget) {
                        break;
                    }
                } else if (a < 0 && b > 0) {
                    if (motorFrontRight.getCurrentPosition() >= topRightTarget || motorFrontLeft.getCurrentPosition() <= topLeftTarget || motorBackLeft.getCurrentPosition() >= bottomLeftTarget || motorBackRight.getCurrentPosition() <= bottomRightTarget) {
                        break;
                    }
                } else {
                    if (motorFrontRight.getCurrentPosition() <= topRightTarget || motorFrontLeft.getCurrentPosition() <= topLeftTarget || motorBackLeft.getCurrentPosition() <= bottomLeftTarget || motorBackRight.getCurrentPosition() <= bottomRightTarget) {
                        break;
                    }
                }
            }

            stop();
        } else { // if degrees == 90
            drive(speed, dist, timeout); // this is because plugging 90 into driveAngle returns an angle (imagine a triangle with two 90 degree angles, obviously not possible), so we just use drive
        }
    }

    /**
     * Strafe the robot left or right.
     * @param direction The direction in which to move the robot.
     * @param speed How fast the robot should move. Number should be in range (0, 1].
     * @param dist How far, in inches, to move the robot.
     * @param timeout If dist is never reached, how many seconds to wait before stopping.
     */
    public void strafe(HorizontalDirection direction, double speed, double dist, double timeout) {
        int distInCounts = (int)(dist * COUNTS_PER_SIDE_INCH);  // Once again, converting from things we understand to the language the motor understands

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
        //sets rights motors to positive and left to negative for counterclockwise turn
        } else {
            motorFrontRight.setPower(-speed);
            motorFrontLeft.setPower(speed);
            motorBackLeft.setPower(speed);
            motorBackRight.setPower(-speed);
        //sets left motors to positive and right to negative for clockwise turn
        }
        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout){ //while opmode active and timenout not reached
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
    /**
     * Extend or retract the drawer slide
     * @param dist How far, in inches, to extend/retract the slide OR input EXTENSION_INCHES or INCHES_PER_HALF_EXTENSION
     * @param timeout If dist is never reached, how many seconds to wait before stopping.
     */
    public void extend(double dist, double timeout) {
//        int distInCounts = (int)(dist * COUNTS_PER_INCH_EXTENDED);
//        double drawerSlideTarget = motorDrawerSlide.getCurrentPosition() + distInCounts;
//
//        motorDrawerSlide.setPower(.5);
//
//        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
//            if (dist < 0) {
//                if (motorDrawerSlide.getCurrentPosition() <= drawerSlideTarget) {
//                    break;
//                }
//            } else {
//                if (motorDrawerSlide.getCurrentPosition() >= drawerSlideTarget) {
//                    break;
//                }
//            }
//        }
//        motorDrawerSlide.setPower(0);
    }

    public void extend(double dist, double timeout, double speed) { //Overloaded the function extend so you can choose whether or not you put in a speed and it will do the function that matches your input
//        int distInCounts = (int)(dist * COUNTS_PER_INCH_EXTENDED);
//        double drawerSlideTarget = motorDrawerSlide.getCurrentPosition() + distInCounts;
//
//        motorDrawerSlide.setPower(speed);
//
//        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
//            if (dist < 0) {
//                if (motorDrawerSlide.getCurrentPosition() <= drawerSlideTarget) {
//                    break;
//                }
//            } else {
//                if (motorDrawerSlide.getCurrentPosition() >= drawerSlideTarget) {
//                    break;
//                }
//            }
//        }
//        motorDrawerSlide.setPower(0);
    }

    public void stop() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
    }

    public enum HorizontalDirection { //Enumerator declared for strafe function
        RIGHT,
        LEFT
    }

    public void grab(double distance) {
        grabber.setPower(-0.5);
        ((LinearOpMode)opMode).sleep((int)(distance * GRABBER_TIME * 1000));
        grabber.setPower(0);
    }

    public void release(double distance) {
        grabber.setPower(0.5);
        ((LinearOpMode)opMode).sleep((int)(distance * GRABBER_TIME * 1000));
        grabber.setPower(0);
    }
}

