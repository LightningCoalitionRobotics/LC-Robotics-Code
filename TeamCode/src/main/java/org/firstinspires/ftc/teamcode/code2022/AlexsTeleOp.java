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
        float joyR_X = Math.abs(gamepad1.right_stick_x);
        float joyR_Y = Math.abs(gamepad1.right_stick_y);
        telemetry.addLine("double startTime = " + startTime);
        telemetry.addLine("double speedArm = " + speedArm);


        if (gamepad1.dpad_up && speed <= 1) {
            speed *= 2;
            telemetry.addLine("pad 1 dpad up pushed");
            telemetry.addLine("speed is now " + speed);

        } else if (gamepad1.dpad_down && speed >= 0.25) {
            speed /= 2;
            telemetry.addLine("pad 1 dpad down  pushed");
            telemetry.addLine("speed is now " + speed);

        }


        //controls for the movement gamepad 1
        //joyR_Y is the absolute value of the position of the joy stick when moved forward or backward
        //joyR_X is the absolute value of the position of the joy stick when moved left or right
        if (gamepad1.atRest()) {
            robot.stopMotor();

        } else if(joyR_Y > joyR_X) {
            //when joyR_Y is > than joyR_X, the robot moves forward or backward
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
        } else if(joyR_X > joyR_Y){
            //when joyR_X is > than joyR_Y, the robot strafes left or right
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
        } else if (gamepad1.left_stick_x > 0.25) {
            //turn left
            //when joy stick's displacement is greater than a quarter, the robot turns left
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);

        } else if (gamepad1.left_stick_x < -0.25) {
            //turn right
            //when joy stick's displacement is less than a quarter, the robot turns right
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);

        }
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

         /*else if(gamepad1.right_stick_x != 0 && gamepad1.right_stick_y != 0){
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
        }*/
        //controls for the arm gamepad

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
        telemetry.update();
    }
}