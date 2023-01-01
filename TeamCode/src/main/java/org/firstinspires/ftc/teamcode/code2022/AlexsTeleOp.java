package org.firstinspires.ftc.teamcode.code2022;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@TeleOp(name = "AlexsTeleOp", group = "TeleOp")
public class AlexsTeleOp extends OpMode {
    private HardwareCletus robot = new HardwareCletus(this);

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
        float joyR_X = Math.abs(gamepad1.right_stick_x);
        float joyR_Y = Math.abs(gamepad1.right_stick_y);
        float joyL_X = Math.abs(gamepad1.left_stick_x);
        float joyL_Y = Math.abs(gamepad1.left_stick_y);
        float liftjoyL_X = Math.abs(gamepad2.left_stick_x); // variables used for lift mechanism
        float liftjoyL_Y = Math.abs(gamepad2.left_stick_y);
        float clawjoyR_X = Math.abs(gamepad2.right_stick_x);
        float clawjoyR_Y = Math.abs(gamepad2.right_stick_x);

        // LEGACY CODE TO BE CLEANED UP
        telemetry.addLine("double startTime = " + startTime);
        telemetry.addLine("double speedArm = " + speedArm);

        // LEGACY CODE TO BE CLEANED UP
        // needs to be updated to use new speed magnitude values (joyR_X, joyR_Y, joyL_X)
        // "SPEED" is no longer used in motion control code.
        if (gamepad1.dpad_up && speed <= 1) {
            speed *= 2;
            telemetry.addLine("pad 1 dpad up pushed");
            telemetry.addLine("speed is now " + speed);
        } else if (gamepad1.dpad_down && speed >= 0.25) {
            speed /= 2;
            telemetry.addLine("pad 1 dpad down  pushed");
            telemetry.addLine("speed is now " + speed);
        }

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
                robot.liftLeft.setPower(0.5);
                robot.liftRight.setPower(0.5);
            }
            else if (gamepad2.dpad_down) {
                robot.liftLeft.setPower(-0.5);
                robot.liftRight.setPower(-0.5);
            }
            else{
                robot.liftLeft.setPower(0.0);
                robot.liftRight.setPower(0.0);
        }

        // Safety stop - if joysticks not being touched, make sure robot does not move.
        if (gamepad2.atRest()) {
            robot.stopMotor();

        } else if (liftjoyL_Y != 0) {
            // LIFT UP / LIFT DOWN MOVEMENT (@ LINEAR SPEED)
            //when liftjoyL_Y is > than liftjoyL_Y, the lift mechanism lifts up/down
            //speed magnitude is the absolute value of joystick position
            if (gamepad2.left_stick_y > 0) {
                //upward motion
                //when left joy stick is moved upward and its y-axis position on cartesian plane is > than 0, lift moves upward
                robot.liftLeft.setPower(liftjoyL_Y);
                robot.liftRight.setPower(liftjoyL_Y);
                telemetry.update();
            } else if (gamepad2.left_stick_y < 0) {
                //downward motion
                //when left joy stick is moved upward and its y-axis position on cartesian plane is < than 0, lift moved upward
                robot.liftLeft.setPower(-liftjoyL_Y);
                robot.liftRight.setPower(-liftjoyL_Y);
                telemetry.update();
            }
        }
        if (gamepad1.y){
            //move to -135 degrees
            robot.claw.setPosition(0);
        }







        /* LEGACY ROBOT MOTION CODE TO BE CLEANED UP, NO LONGER USED.
        } else if(gamepad1.left_trigger > 0.49) {
            //moves forwards
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
        } else if(gamepad1.right_trigger > 0.49){
            //moves backwards
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(-speed);
       } else if(gamepad1.left_bumper){
            //strafes left
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);
        } else if(gamepad1.right_bumper){
            //strafe right
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);
        */

         /* MORE LEGACY MOTION CODE TO BE CLEANED UP, NO LONGER USED
         else if(gamepad1.right_stick_x != 0 && gamepad1.right_stick_y != 0){
            double angle = Math.toDegrees(Math.atan(Math.abs(gamepad1.right_stick_y)/Math.abs(gamepad1.right_stick_x)));
            int quadrant = 0;
            if(gamepad1.right_stick_x > 0){
                if(gamepad1.right_stick_y > 0){
                    quadrant = 1;
                } else if(gamepad1.right_stick_y < 0){
                    quadrant = 4;
                }
            } else if (gamepad1.right_stick_x < 0){
                if(gamepad1.right_stick_y > 0){
                    quadrant = 2;
                } else if(gamepad1.right_stick_y < 0){
                    quadrant = 3;
                }
            }
            robot.driveAngleIndefinite(angle, speed, quadrant);
        }
        */

        // ROBOT ACTUATOR CODE
        // Input = gamepad2 controls (TBD)
        // Output = power commands to the actuators (TBD)
        // Actuators: (1) lift arm mechanism, (2) claw

       /* LEGACY CODE TO BE UPDATED AFTER NEW MECHANISMS ARE DESIGNED.
       if(gamepad2.atRest() && speedArm == 0){
            robot.arm.setPower(0);
        }

        if(startTime >= 0.45){
            speedArm = 0;
        }

        if(gamepad2.y){
            resetStartTime();
            speedArm = -0.75;
            robot.arm.setPower(speedArm);
        } else if(gamepad2.a){
            resetStartTime();
            speedArm = 0.75;
            robot.arm.setPower(speedArm);
        }

        if(gamepad2.x){
            robot.extend();
        } else if(gamepad2.b){
            robot.unextend();
        }

        if(gamepad2.right_stick_y > 0){
            resetStartTime();
            robot.arm.setPower(0.5);
        } else if(gamepad2.right_stick_y < 0){
            resetStartTime();
            robot.arm.setPower(-0.5);
        }

        if(gamepad2.left_stick_y > 0){
            robot.grabber.setPosition(robot.grabber.getPosition() + 0.001);
        } else if(gamepad2.left_stick_y < 0){
            robot.grabber.setPosition(robot.grabber.getPosition() - 0.001);
        }

    }

 */
        // update telemetry values
        telemetry.update();

    } // end main loop
} // end TeleOp