package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@TeleOp(name="EthansTeleOp", group="TeleOp")
public class EthansTeleOp extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {
        robot.motorFrontLeft.setPower(gamepad1.left_stick_y);
        robot.motorFrontRight.setPower(gamepad1.right_stick_y);
        robot.motorBackLeft.setPower(gamepad2.left_stick_y);
        robot.motorBackRight.setPower(gamepad2.right_stick_y);
    }
}