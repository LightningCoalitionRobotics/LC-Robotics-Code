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
        //forward,side,rotation
        robot.motorFrontLeft.setPower((-gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x)/3);
        robot.motorFrontRight.setPower((-gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x)/3);
        robot.motorBackLeft.setPower((-gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x)/3);
        robot.motorBackRight.setPower((-gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x)/3);

        robot.motorDrawerSlide.setPower(gamepad2.left_stick_y);
        robot.grabber.setPower(gamepad2.right_stick_y);

        telemetry.addData("Test", gamepad1.left_stick_y+" "+gamepad1.left_stick_x+" "+gamepad1.right_stick_x);
    }
}