package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;

// Use the OneTeleOpToRuleThemAll for the 2022-23 Robotics Season; Ignore this one!

@TeleOp(name = "TeleOpCompetition", group = "TeleOp")
public class TeleOpCompetition extends OpMode {

    private HardwareGoobus robot = new HardwareGoobus(this);

    public void init() {
        robot.init(hardwareMap);
    }

    double speed = 0.5; // a double that stores a speed that can be increased or decreased using the dpad
    double speedArm = 0.0;

    /*  a double that stores the speed used when moving the arm with the y or a buttons
        used to make sure that the arm doesn't stop halfway up
        if (gamepad2.atRest()) would stop it halfway through, as the button is only pressed once
        changing it to if(gamepad2.atRest() && speedArm == 0) will make sure that if the arm is moving because of y or a buttons, it will finish the movement before stopping
    */

    public void loop() {
        // Main processing loop for the robot. The loop processes gamepad inputs for robot actions.

        //arm gamepad: y/a to move arm to max/min height, b/x to open and close claw, dpad for more precise height changes
        double startTime = getRuntime();

        // Robot motion variables. We use linear control of the motors driving the wheels, based on displacement of
        // joystick controllers in gamepad1. Variable declarations and usage:
        //      joyR_X = right joystick, X axis. Used for strafe left/right motion input.
        //      joyR_L = right joystick, Y axis. Used for forward/reverse motion input.
        //      joyL_X = left joystick, X axis. Used for turn clockwise/counterclockwise motion input.
        //      joyL_Y = (currently unused)
        // Joystick position is read and converted to an absolute value for use as speed magnitude for motors.

        //variables for drive
        float joyR_X = Math.abs(gamepad1.right_stick_x);
        float joyR_Y = Math.abs(gamepad1.right_stick_y);
        float joyL_X = Math.abs(gamepad1.left_stick_x);
        float joyL_Y = Math.abs(gamepad1.left_stick_y);

        //variables for lift
        float liftjoyL_X = Math.abs(gamepad2.left_stick_x);
        float liftjoyL_Y = Math.abs(gamepad2.left_stick_y);
        float extendArm = Math.abs(gamepad2.right_stick_y);

        //variables for grab
        float clawjoyR_X = Math.abs(gamepad2.right_stick_x);
        float clawjoyR_Y = Math.abs(gamepad2.right_stick_x);

        // LEGACY CODE TO BE CLEANED UP
        telemetry.addLine("double startTime = " + startTime);
        telemetry.addLine("double speedArm = " + speedArm);


        // ROBOT MOTION CODE
        // Input = gamepad1 joysticks (both right and left)
        // Output = power commands to the robot's wheel motors (back left, back right, front left, front right)
        //joyR_Y is the absolute value of the position of the joy stick when moved forward or backward
        //joyR_X is the absolute value of the position of the joy stick when moved left or right

        // Safety stop - if joysticks not being touched, make sure robot does not move.
        if (gamepad1.atRest()) {
            robot.stopMotor();

        } else if (joyR_Y > joyR_X) {
            // FORWARD / REVERSE MOTION
            //when joyR_Y is > than joyR_X, the robot moves forward/reverse
            //note we need to resample joystick position for direction input (+/-)
            //speed magnitude is absolute value of joystick position
            //correct direction is commanded by negating in the motor commands below
            if (gamepad1.right_stick_y > 0) {
                //straight forward
                //when right joy stick is moved upward and its y-axis position on the cartesian plane is greater than zero, robot moves forward
                robot.motorBackLeft.setPower(joyR_Y);
                robot.motorBackRight.setPower(joyR_Y);
                robot.motorFrontLeft.setPower(joyR_Y);
                robot.motorFrontRight.setPower(joyR_Y);
                telemetry.update();
            } else if (gamepad1.right_stick_y < 0) {
                //straight backward
                //when right joy stick is moved downward and its y-axis position on the cartesian plane is less than zero, robot moves backward
                robot.motorBackLeft.setPower(-joyR_Y);
                robot.motorBackRight.setPower(-joyR_Y);
                robot.motorFrontLeft.setPower(-joyR_Y);
                robot.motorFrontRight.setPower(-joyR_Y);
                telemetry.update();
            }
        } else if (joyR_X > joyR_Y) {
            // STRAFE LEFT / RIGHT MOTION
            //when joyR_X is > than joyR_Y, the robot strafes left or right
            //note we need to resample joystick position for direction input (+/-)
            //speed magnitude is absolute value of joystick position
            //correct direction is commanded by negating in the motor commands below
            if (gamepad1.right_stick_x < 0) {
                //strafe right
                //when right joy stick is moved right and its x-axis position on the cartesian plane is less than zero, robot strafes right
                robot.motorBackLeft.setPower(-joyR_X);
                robot.motorBackRight.setPower(joyR_X);
                robot.motorFrontLeft.setPower(joyR_X);
                robot.motorFrontRight.setPower(-joyR_X);
                telemetry.update();
            } else if (gamepad1.right_stick_x > 0) {
                //strafe left
                //when right joy stick is moved left and its x-axis position on the cartesian plane is greater than than zero, robot strafes left
                robot.motorBackLeft.setPower(joyR_X);
                robot.motorBackRight.setPower(-joyR_X);
                robot.motorFrontLeft.setPower(-joyR_X);
                robot.motorFrontRight.setPower(joyR_X);
                telemetry.update();
            }
        } else if (gamepad1.left_stick_x > 0) {
            // TURN CLOCKWISE/COUNTERCLOCKWISE MOTION
            //when joyL is moved, the robot turns clockwise (left) or counterclockwise (right)
            //note we needed to resample joystick position for direction input (+/-)
            //speed magnitude is absolute value of joystick position
            //correct direction is commanded by negating in the motor commands below
            //
            //turn left
            //when joy stick's displacement is greater than zero, the robot turns left
            robot.motorBackLeft.setPower(-joyL_X);
            robot.motorBackRight.setPower(joyL_X);
            robot.motorFrontLeft.setPower(-joyL_X);
            robot.motorFrontRight.setPower(joyL_X);

        } else if (gamepad1.left_stick_x < 0) {
            //turn right
            //when joy stick's displacement is less than zero, the robot turns right
            robot.motorBackLeft.setPower(joyL_X);
            robot.motorBackRight.setPower(-joyL_X);
            robot.motorFrontLeft.setPower(joyL_X);
            robot.motorFrontRight.setPower(-joyL_X);
        }

        // Code for the 2nd controller

        // LIFT UP / LIFT DOWN MOVEMENT (@ CONSTANT SPEED).
        // when dpad is pressed up or down, lift moves up and down at a set speed.
        // later we will need to find a good speed to set lift to.
        if (gamepad2.dpad_up) {
            robot.motorLiftLeft.setPower(0.5);
            robot.motorLiftRight.setPower(0.5);
        } else if (gamepad2.dpad_down) {
            robot.motorLiftLeft.setPower(-0.5);
            robot.motorLiftRight.setPower(-0.5);
        } else {
            robot.motorLiftLeft.setPower(0.0);
            robot.motorLiftRight.setPower(0.0);
        }

        if (gamepad2.left_stick_y != 0) {
            // LIFT UP / LIFT DOWN MOVEMENT (@ LINEAR SPEED)
            //when liftjoyL_Y is > than liftjoyL_X, the lift mechanism lifts up/down
            //speed magnitude is the absolute value of joystick position
            if (gamepad2.left_stick_y > 0) {
                //upward motion
                //when left joy stick is moved upward and its y-axis position on cartesian plane is > than 0, lift moves upward
                robot.motorLiftLeft.setPower(liftjoyL_Y);
                robot.motorLiftRight.setPower(-liftjoyL_Y);
                telemetry.update();


            } else if (gamepad2.left_stick_y < 0) {
                //downward motion
                //when left joy stick is moved upward and its y-axis position on cartesian plane is < than 0, lift moved upward
                //Motors have to running in opposite directions (L = up: ccw; down: cw || R = up: cw; down: ccw)
                robot.motorLiftLeft.setPower(-liftjoyL_Y);
                robot.motorLiftRight.setPower(liftjoyL_Y);
                telemetry.update();
            }
        }


        // Code for claw in progress
        if (gamepad2.y) {
            //move to -135 degrees
            robot.claw.setPosition(0);
        } else if (gamepad2.x || gamepad2.b) {
            //move to 0 degrees
            robot.claw.setPosition(0.5);
        } else if (gamepad2.a) {
            //move to 135 degrees
            robot.claw.setPosition(1);
        }
        


    }
   }
