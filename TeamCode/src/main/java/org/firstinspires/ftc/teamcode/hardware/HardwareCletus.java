package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

/**
 * The class for the LCR 2021-22 robot.
 *
 * If more components are added, please add them to this class
 *
 * @author Jack Weinstein
 * @author Alex Cunningham
 */

public class HardwareCletus extends Robot {

    // These are constants that we have experimentally determined, relating counts (the way an encoder measures movement) to inches or degrees (the way we understand movement)
    private static final int COUNTS_PER_REVOLUTION = 1400;                         // One full revolution of a wheel is 1400 counts
    private static final int COUNTS_PER_FORWARD_INCH = COUNTS_PER_REVOLUTION / 12; // 1 revolution FORWARDS is very close to 1 foot, so to get counts per inch, take counts per revolution and divide it by 12

    private static final int COUNTS_PER_360 = 10000;                               // One full turn 360 degrees is 10000 counts
    private static final int COUNTS_PER_DEGREE = COUNTS_PER_360 / 360;

    private static final int COUNTS_PER_SIDE_FOOT = 2000;                          // The amount of counts per the robot moving to the SIDE 1 foot is 2000, NOTICE this is different than the amount of counts going forward or backwards
    private static final int COUNTS_PER_SIDE_INCH = COUNTS_PER_SIDE_FOOT / 12;

    // Not experimentally determined:
    private static final int COUNTS_PER_45_INCH = (int) Math.hypot(COUNTS_PER_FORWARD_INCH, COUNTS_PER_SIDE_INCH);

    // All of the components we will need (e.g. motors, servos, sensors...) that are attached to the robot are declared here

    public DcMotor motorFrontLeft;

    public DcMotor motorFrontRight;

    public DcMotor motorBackLeft;

    public DcMotor motorBackRight;

    public CRServo arm; //CRServo is a continuous rotation servo

    public Servo grabber;

    public HardwareCletus(OpMode opMode) {
        super(opMode);
    }

    @Override  // Since this class extends the class Robot, these @Overrides let the code know that this will super cede any conflicting properties of init present in class Robot
    public void init(HardwareMap hardwareMap) { //This section registers the motors to the encoders and sets their default direction
        this.hardwareMap = hardwareMap;
        motorFrontLeft = registerMotor("motorFrontLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight = registerMotor("motorFrontRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER); //this direction is reverse because the motor is backward, so to make it go forwards you (if you had this forwards) would have to set a negative speed
        motorBackLeft = registerMotor("motorBackLeft", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight = registerMotor("motorBackRight", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER); // Same problem as above with this motor
        grabber = registerServo("grabber", 0.0f);
        arm = hardwareMap.crservo.get("arm"); //continuous rotation servos don't have a registerServo() method like normal servos do

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

    public void driveIndefinite(double speed){
        motorFrontRight.setPower(speed); //set motors to speed
        motorFrontLeft.setPower(speed);
        motorBackRight.setPower(speed);
        motorBackLeft.setPower(speed);
    }

    public void turnIndefinite(double speed){
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(speed);
        motorBackLeft.setPower(speed);
        motorBackRight.setPower(-speed);
    }

    public void strafeIndefinite(double speed){
        motorFrontRight.setPower(-speed); //set motors to speed
        motorFrontLeft.setPower(speed);
        motorBackRight.setPower(speed);
        motorBackLeft.setPower(-speed);
    }

    public void driveAngleIndefinite(double degrees, double speed, int quadrant){
        double a; // larger movement vector
        double b; // smaller movement vector

        double tangent = Math.tan(degrees);

        a = 1;
        // tan ø = a+b / a-b
        // tangent ø = 1+b/1-b
        // tangent ø - tangent*b = 1+b
        // tangent ø = (tangent+1)b + 1
        // (tangent ø - 1)/(tangent ø + 1) = b
        b = (tangent - 1)/(tangent + 1);

        // Find targets for motors
        if (degrees != 90) { //if degrees are not equal to 90, continue with driveAngle, if they are equal to 90, just use drive with speed and dist
            if (quadrant == 1){
                motorFrontRight.setPower(b * speed);
                motorFrontLeft.setPower(a * speed);
                motorBackLeft.setPower(b * speed);
                motorBackRight.setPower(a * speed);

            } else if (quadrant == 2){
                motorFrontRight.setPower(a * speed);
                motorFrontLeft.setPower(b * speed);
                motorBackLeft.setPower(a * speed);
                motorBackRight.setPower(b * speed);

            } else if (quadrant == 3){
                motorFrontRight.setPower(-b * speed);
                motorFrontLeft.setPower(-a * speed);
                motorBackLeft.setPower(-b * speed);
                motorBackRight.setPower(-a * speed);

            } else if (quadrant == 4){
                motorFrontRight.setPower(-a * speed);
                motorFrontLeft.setPower(-b * speed);
                motorBackLeft.setPower(-a * speed);
                motorBackRight.setPower(-b * speed);

            }
        } else { // if degrees == 90
            driveIndefinite(speed); // this is because plugging 90 into driveAngle returns an angle (imagine a triangle with two 90 degree angles, obviously not possible), so we just use drive
        }
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
        // tan ø = a+b / a-b
        // tangent ø = 1+b/1-b
        // tangent ø - tangent*b = 1+b
        // tangent ø = (tangent+1)b + 1
        // (tangent ø - 1)/(tangent ø + 1) = b
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

    public void stop() {

    }

    public void extend(){
        grabber.setPosition(1.0);

    }

    public void unextend(){
        grabber.setPosition(0.0);
    }

    public void liftArm(){

    }

    public void lowerArm(){

    }


    /**
     * Strafe the robot left or right.
     * @param speed How fast the robot should move. Number should be in range (0, 1]. Positive for moving right, negative for moving left.
     * @param dist How far, in inches, to move the robot.
     * @param timeout If dist is never reached, how many seconds to wait before stopping.
     */
   public void strafe(double speed, double dist, double timeout) {
        int distInCounts = (int)(dist * COUNTS_PER_SIDE_INCH);  // Once again, converting from things we understand to the language the motor understands

        int correctDirection;
        if (speed > 0) {
            correctDirection = 1;
        } else {
            correctDirection = -1;
        }

        int topRightTarget = motorFrontRight.getCurrentPosition() - (correctDirection * distInCounts);
        int topLeftTarget = motorFrontLeft.getCurrentPosition() + (correctDirection * distInCounts);
        int bottomLeftTarget = motorBackLeft.getCurrentPosition() - (correctDirection * distInCounts);
        int bottomRightTarget = motorBackRight.getCurrentPosition() + (correctDirection * distInCounts);

        motorFrontRight.setPower(-speed * correctDirection);
        motorFrontLeft.setPower(speed * correctDirection);
        motorBackLeft.setPower(-speed * correctDirection);
        motorBackRight.setPower(speed * correctDirection);

        while (((LinearOpMode) opMode).opModeIsActive() && elapsedTime.seconds() < timeout) {
            if (speed > 0) {
                if (motorFrontRight.getCurrentPosition() <= topRightTarget || motorFrontLeft.getCurrentPosition() >= topLeftTarget || motorBackLeft.getCurrentPosition() <= bottomLeftTarget || motorBackRight.getCurrentPosition() >= bottomRightTarget) {
                    break;
                }
            } else {
                if (motorFrontRight.getCurrentPosition() >= topRightTarget || motorFrontLeft.getCurrentPosition() <= topLeftTarget || motorBackLeft.getCurrentPosition() >= bottomLeftTarget || motorBackRight.getCurrentPosition() <= bottomRightTarget) {
                    break;
                }
            }
            ((LinearOpMode) opMode).idle();
        }

        stop();
    }
}
