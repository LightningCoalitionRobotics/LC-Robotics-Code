package org.firstinspires.ftc.teamcode.code2022;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

public class TestTeleOp extends OpMode {

    private HardwareCletus robot = new HardwareCletus(this);

    public void init() { robot.init(hardwareMap);}

        public void loop() {

            robot.motorFrontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) / 3);
            robot.motorFrontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) / 3);
            robot.motorBackLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) / 3);
            robot.motorBackRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) / 3);


            telemetry.addData("Test", gamepad1.left_stick_y + " " + gamepad1.left_stick_x + " " + gamepad1.right_stick_x);
        }


    }



