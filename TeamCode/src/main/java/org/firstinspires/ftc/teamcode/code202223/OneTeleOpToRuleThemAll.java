package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;

@TeleOp(name = "OneTeleOpToRuleThemAll", group = "TeleOp")
public class OneTeleOpToRuleThemAll extends OpMode {

    private HardwareGoobus robot = new HardwareGoobus(this);

    public void init() {
        robot.init(hardwareMap); //sets claw closed to start;
    }

    double speed = 0.5; // a double that stores a speed that can be increased or decreased using the dpad
    double speedArm = 0.0;
    int minArmCounts = -1000000;
    int maxArmCounts = 1000000;

    //find the counts value when the arm is at max height!!!! Use to update code in Hardware for Autonomous code!!

    /*  a double that stores the speed used when moving the arm with the y or a buttons
        used to make sure that the arm doesn't stop halfway up
        if (gamepad2.atRest()) would stop it halfway through, as the button is only pressed once
        changing it to if(gamepad2.atRest() && speedArm == 0) will make sure that if the arm is moving because of y or a buttons, it will finish the movement before stopping
    */

    public void loop() {
        // Main processing loop for the robot. The loop processes gamepad inputs for robot actions.


        telemetry.addData("Arm Counts:", "motorLiftRight=%d motorLiftLeft=%d",
                robot.motorLiftRight.getCurrentPosition(), robot.motorLiftLeft.getCurrentPosition()); //adds telemetry data to phone for output
        telemetry.addData("Servo Position", robot.claw.getPosition());
        telemetry.addData("Encoder reading of motorFrontRight:", robot.motorFrontRight.getCurrentPosition());
        telemetry.addData("Encoder reading of motorFrontLeft:", robot.motorFrontLeft.getCurrentPosition());
        telemetry.addData("Encoder reading of motorBackRight:", robot.motorBackRight.getCurrentPosition());
        telemetry.addData("Encoder reading of motorBackLeft:", robot.motorBackLeft.getCurrentPosition());
        telemetry.addData("Motor lifting joystick reading:", gamepad2.left_stick_y);
        telemetry.update();

        // double startTime = getRuntime();
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
//        float liftjoyL_X = Math.abs(gamepad2.left_stick_x);
        float liftjoyL_Y = gamepad2.left_stick_y;
//        float extendArm = Math.abs(gamepad2.right_stick_y);

        //variables for grab -
//        float clawjoyR_X = Math.abs(gamepad2.right_stick_x);
//        float clawjoyR_Y = Math.abs(gamepad2.right_stick_x);


        // ROBOT MOTION CODE
        // Input = gamepad1 joysticks (both right and left)
        // Output = power commands to the robot's wheel motors (back left, back right, front left, front right)
        //joyR_Y is the absolute value of the position of the joy stick when moved forward or backward
        //joyR_X is the absolute value of the position of the joy stick when moved left or right

        // Safety stop - if joysticks not being touched, make sure robot does not move.
        if (gamepad1.atRest()) {
            robot.stopMotor();

        } else if (joyR_Y > joyR_X) {
            // FORWARD / REVERSE MOTION (@ LINEAR SPEED)
            //when joyR_Y is > than joyR_X, the robot moves forward/reverse
            //note we need to resample joystick position for direction input (+/-)
            //speed magnitude is absolute value of joystick position
            //correct direction is commanded by negating in the motor commands below
            if (gamepad1.right_stick_y > 0) {
                //straight forward
                //when right joy stick is moved upward and its y-axis position on the cartesian plane is greater than zero, robot moves forward
                robot.motorBackLeft.setPower(joyR_Y * speed);
                robot.motorBackRight.setPower(joyR_Y * speed);
                robot.motorFrontLeft.setPower(joyR_Y * speed);
                robot.motorFrontRight.setPower(joyR_Y * speed);
//                telemetry.update();
            } else if (gamepad1.right_stick_y < 0) {
                //straight backward
                //when right joy stick is moved downward and its y-axis position on the cartesian plane is less than zero, robot moves backward
                robot.motorBackLeft.setPower(-joyR_Y * speed );
                robot.motorBackRight.setPower(-joyR_Y * speed);
                robot.motorFrontLeft.setPower(-joyR_Y * speed);
                robot.motorFrontRight.setPower(-joyR_Y * speed);
//                telemetry.update();
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
                robot.motorBackLeft.setPower(-joyR_X * speed);
                robot.motorBackRight.setPower(joyR_X * speed);
                robot.motorFrontLeft.setPower(joyR_X * speed);
                robot.motorFrontRight.setPower(-joyR_X * speed);
//                telemetry.update();
            } else if (gamepad1.right_stick_x > 0) {
                //strafe left
                //when right joy stick is moved left and its x-axis position on the cartesian plane is greater than than zero, robot strafes left
                robot.motorBackLeft.setPower(joyR_X * speed);
                robot.motorBackRight.setPower(-joyR_X * speed);
                robot.motorFrontLeft.setPower(-joyR_X * speed);
                robot.motorFrontRight.setPower(joyR_X * speed);
//                telemetry.update();
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
            robot.motorBackLeft.setPower(-joyL_X * speed);
            robot.motorBackRight.setPower(joyL_X * speed);
            robot.motorFrontLeft.setPower(-joyL_X * speed);
            robot.motorFrontRight.setPower(joyL_X * speed);

        } else if (gamepad1.left_stick_x < 0) {
            //turn right
            //when joy stick's displacement is less than zero, the robot turns right
            robot.motorBackLeft.setPower(joyL_X * speed);
            robot.motorBackRight.setPower(-joyL_X * speed);
            robot.motorFrontLeft.setPower(joyL_X * speed);
            robot.motorFrontRight.setPower(-joyL_X * speed);
        }

        // Code for the 2nd controller

        // LIFT UP / LIFT DOWN MOVEMENT (@ CONSTANT SPEED).
        // when dpad is pressed up or down, lift moves up and down at a set speed.
        //r = CW                l = CCW
        // later we will need to find a good speed to set lift to.
        /*
        if (gamepad2.dpad_up && (robot.motorLiftLeft.getCurrentPosition() <= maxArmCounts && robot.motorLiftRight.getCurrentPosition() <= maxArmCounts )) {
            robot.motorLiftLeft.setPower(0.5);
            robot.motorLiftRight.setPower(0.5);
        } else if (gamepad2.dpad_down && (robot.motorLiftLeft.getCurrentPosition() >= minArmCounts && robot.motorLiftRight.getCurrentPosition() >= minArmCounts )) {
            robot.motorLiftLeft.setPower(-0.5);
            robot.motorLiftRight.setPower(-0.5);
        } else {
            robot.motorLiftLeft.setPower(0.0);
            robot.motorLiftRight.setPower(0.0);
        } */

        if (liftjoyL_Y != 0) {
            // LIFT UP / LIFT DOWN MOVEMENT (@ LINEAR SPEED)
            //when liftjoyL_Y is > than liftjoyL_X, the lift mechanism lifts up/down
            //speed magnitude is the absolute value of joystick position
            // Note that the motorLiftRight encoder is counting DOWN as the arm raises, because it is opposite the left motor.

            robot.motorLiftLeft.setPower(-liftjoyL_Y/2);
            robot.motorLiftRight.setPower(liftjoyL_Y/2);

        } else{       // Joystick is not being touched -> then brake the arm so it doesn't fall.
            robot.motorLiftLeft.setPower(0);
            robot.motorLiftRight.setPower(0);
        }
        /*
        if(gamepad2.a){ //Press a to raise to max height; - 1 is in there just for testing.
            // JWD - Note - should not change run mode during loop, because we initialize this differently up at top


            robot.motorLiftLeft.setTargetPosition(1000);
            robot.motorLiftRight.setTargetPosition(-1000);

            robot.motorLiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorLiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.update();

        }
        */

           /*
            //code to extend arm out and in
            if(gamepad2.right_stick_y != 0){
                if(gamepad2.right_stick_y > 0){
                    robot.motorExtendArm.setPower(extendArm);
                }
                else if (gamepad2.right_stick_y < 0){
                    robot.motorExtendArm.setPower(-extendArm);
                }
            }
    */
        //CLAW OPEN AND CLOSE MOVEMENT
        //When y, x, b, or a button is pressed, the claw opens or closes to the position that the servo was programed to move to.
       /* if (gamepad2.y){
            //moves servo in claw to -135 degrees
            robot.claw.setPosition(0);
            telemetry.addData("Servo Position", robot.claw.getPosition());
            telemetry.update();
*/
          if (gamepad2.left_bumper){
            //moves servo in claw to 0 degrees or its default position //OPEN CLAW
            robot.claw.setPosition(0.5);


        } else if (gamepad2.right_bumper){
            //moves servo in claw to 135 degrees; CLOSE CLAW
            robot.claw.setPosition(1);
        }


    }
}


