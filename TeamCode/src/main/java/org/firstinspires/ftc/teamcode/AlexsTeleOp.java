package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@TeleOp(name = "AlexsTeleOp", group = "TeleOp")
public class AlexsTeleOp extends OpMode {
    private HardwareCletus robot = new HardwareCletus(this);




    public void init() {
        robot.init(hardwareMap);
    }

    double speed = 0.5;
    boolean lift = false;

    public void fullLift(String direction) {

        if (lift) {
            if (direction.equals("up")) {
                robot.liftArm();
            } else {
                robot.lowerArm();
            }

        }
        lift = false;
    }

    public void loop() {
        //movement gamepad: triggers for forward/backward, B/X for strafing left and right, right joystick for moving tangent, left joystick for turning, dpad for changing speed
        //arm gamepad: Y/A to move arm to max/min height, B/X to open and close claw, dpad for more precise height changes

        if (gamepad1.dpad_up) {
            speed *= 2;
            telemetry.addLine("pad 1 dpad up pushed");
            telemetry.addLine("speed is now " + speed);

        } else if (gamepad1.dpad_down) {
            speed /= 2;
            telemetry.addLine("pad 1 dpad down  pushed");
            telemetry.addLine("speed is now " + speed);

        }

        //controls for the movement gamepad
        if (gamepad1.atRest()) {
            robot.stopMotor();

        } else if (gamepad1.left_trigger > 0.49) {
            //moves forwards
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);

        } else if (gamepad1.right_trigger > 0.49) {
            //moves backwards
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(-speed);

       /* } else if(gamepad1.left_bumper){
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
        } else if (gamepad1.left_stick_x > 0.25) {
            //turn right
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);

        } else if (gamepad1.left_stick_x < -0.25) {
            //turn left
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);

        } else if (gamepad1.right_stick_x != 0 && gamepad1.right_stick_y != 0) {
            double angle = Math.toDegrees(Math.atan(Math.abs(gamepad1.right_stick_y) / Math.abs(gamepad1.right_stick_x)));
            int quadrant = 0;
            if (gamepad1.right_stick_x > 0) {
                if (gamepad1.right_stick_y > 0) {
                    quadrant = 1;
                } else if (gamepad1.right_stick_y < 0) {
                    quadrant = 4;
                }
            } else if (gamepad1.right_stick_x < 0) {
                if (gamepad1.right_stick_y > 0) {
                    quadrant = 2;
                } else if (gamepad1.right_stick_y < 0) {
                    quadrant = 3;
                }
            }
            robot.driveAngleIndefinite(angle, speed, quadrant);
        }
        //controls for the arm gamepad
        if (gamepad1.y) {

            telemetry.addLine("pad 1 Y button pushed");

        } else if (gamepad1.a) {

            telemetry.addLine("pad 1 A button pushed");

        } else if (gamepad1.b) {
            telemetry.addLine("pad 1 B button pushed");

        } else if (gamepad1.x) {
            telemetry.addLine("pad 1 X button pushed");

        }

        if (gamepad2.atRest()) {
            robot.arm.setPower(0);

        }

        if (gamepad2.y) {
            lift = true;
            fullLift("up");
            telemetry.addLine("pad 2 Y button pushed");

        } else if (gamepad2.a) {
            lift = true;
            fullLift("down");
            telemetry.addLine("pad 2 A button pushed");

        } else if (gamepad2.b) {
            telemetry.addLine("pad 2 B button pushed");
            robot.extend();

        } else if (gamepad2.x) {
            telemetry.addLine("pad 2 X button pushed");
            robot.unextend();

        }

        if (gamepad2.right_stick_y > 0) {
            robot.arm.setPower(0.5);

        } else if (gamepad2.right_stick_y < 0) {
            robot.arm.setPower(-0.5);

        }
        if (gamepad2.left_stick_y > 0) {
            robot.grabber.setPosition(robot.grabber.getPosition() + 0.025);

        } else if (gamepad2.left_stick_y < 0) {
            robot.grabber.setPosition(robot.grabber.getPosition() - 0.025);

        }
    }


}