package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

public class TestTeleOp2019 extends OpMode {
    HardwareLilPanini robot = new HardwareLilPanini(this);

    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {
        robot.frontController.setMotorPower(1, gamepad1.left_stick_y);
        robot.frontController.setMotorPower(2, gamepad1.left_stick_y);
        robot.rearController.setMotorPower(1, gamepad1.left_stick_y);
        robot.rearController.setMotorPower(2, gamepad1.left_stick_y);
    }
}
