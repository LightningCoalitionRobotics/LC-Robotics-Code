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
        //movement gamepad: triggers for forward/backward, b/x for strafing left and right, right joystick for moving tangent, left joystick for turning, dpad for changing speed
        //arm gamepad: y/a to move arm to max/min height, b/x to open and close claw, dpad for more precise height changes
        double startTime = getRuntime();
        float speedX = Math.abs(gamepad1.right_stick_x);
        float speedY = Math.abs(gamepad1.right_stick_y);
        telemetry.addLine("double startTime = " + startTime);
        telemetry.addLine("double speedArm = " + speedArm);
        telemetry.addLine("Counts = " + telemetry.update());

        if (gamepad1.dpad_up && speed <= 1) {
            speed *= 2;
            telemetry.addLine("pad 1 dpad up pushed");
            telemetry.addLine("speed is now " + speed);

        } else if (gamepad1.dpad_down && speed >= 0.25) {
            speed /= 2;
            telemetry.addLine("pad 1 dpad down  pushed");
            telemetry.addLine("speed is now " + speed);

        }

/*
 * code associated with motion of robot is in this block, gamepad 1
 */
        //controls for the movement gamepad
        if (gamepad1.atRest()) {
            robot.stopMotor();

        } else if(speedY > speedX){
            //speed Y is the right joy stick Y deflection, speed X is the right joy stick X deflection
            //speed Y and speed X are defined as the absolute value of the speed
            //if speed Y > than speed X then we are moving forward or backward
            if (gamepad1.right_stick_y > 0) {
                //straight forward
                robot.motorBackLeft.setPower(speedY);
                robot.motorBackRight.setPower(speedY);
                robot.motorFrontLeft.setPower(speedY);
                robot.motorFrontRight.setPower(speedY);

            } else if (gamepad1.right_stick_y < 0) {
                //straight reverse
                robot.motorBackLeft.setPower(-speedY);
                robot.motorBackRight.setPower(-speedY);
                robot.motorFrontLeft.setPower(-speedY);
                robot.motorFrontRight.setPower(-speedY);
            }
        } else if(speedX > speedY){
            //if speed X > than speed Y then we are strafing left or right
            if (gamepad1.right_stick_x > 0) {
                //strafe right
                robot.motorBackLeft.setPower(-speedX);
                robot.motorBackRight.setPower(speedX);
                robot.motorFrontLeft.setPower(speedX);
                robot.motorFrontRight.setPower(-speedX);

            } else if (gamepad1.right_stick_x < 0) {
                //strafe left
                robot.motorBackLeft.setPower(speedX);
                robot.motorBackRight.setPower(-speedX);
                robot.motorFrontLeft.setPower(-speedX);
                robot.motorFrontRight.setPower(speedX);
            }

        } else if (gamepad1.left_stick_x > 0.25) {
            //turn left
            //pushing joy stick to the right = negative and pushing joy stick to the left = positive
            //if joy stick is deflected more than a quarter of the distance to the left, then we are turning left
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);

        } else if (gamepad1.left_stick_x < -0.25) {
            //turn right
            //if joy stick is deflected less than a quarter of the distance to the right, then we are turning right
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);

        /* } else if(gamepad1.left_trigger > 0.49) {
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
            robot.motorFrontRight.setPower(-speed); */



        } /*else if(gamepad1.right_stick_x != 0 && gamepad1.right_stick_y != 0){
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
        }*
/*
 * controls for the arm gamepad 2
*/
       /* if(gamepad2.atRest() && speedArm == 0){
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
    }
}