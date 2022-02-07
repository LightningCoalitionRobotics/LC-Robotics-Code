package org.firstinspires.ftc.teamcode;

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

    public void loop() {
        if (gamepad1.dpad_up && speed <= 1) {
            speed *= 2;
            telemetry.addLine("speed is now " + speed);

        } else if (gamepad1.dpad_down && speed >= 0.25) {
            speed /= 2;
            telemetry.addLine("speed is now " + speed);

        }

        //controls for the movement gamepad
        if (gamepad1.atRest()) {
            robot.stopMotor();

        } else if (gamepad1.right_stick_y > 0) {
            //drive forwards
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);

        } else if (gamepad1.right_stick_y < 0) {
            //drive backwards
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(-speed);

        } else if (gamepad1.left_stick_x > 0.25) {
            //turn right
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);

        } else if (gamepad1.left_stick_x < -0.25) {
            //turn left
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);

        }

        //controls for the arm gamepad

        if (gamepad2.atRest()) {
            robot.arm.setPower(0);

            if (gamepad2.dpad_up) {
                robot.extend();

            } else if (gamepad2.dpad_down) {
                robot.unextend();

            }

            if (gamepad2.right_stick_y > 0) {
                resetStartTime();
                robot.arm.setPower(0.5);

            } else if (gamepad2.right_stick_y < 0) {
                resetStartTime();
                robot.arm.setPower(0.5);

            }

            if (gamepad2.left_stick_y > 0) {
                robot.grabber.setPosition(robot.grabber.getPosition() + 0.001);

            } else if (gamepad2.left_stick_y < 0) {
                robot.grabber.setPosition(robot.grabber.getPosition() - 0.001);

            }

        }

    }
}