package org.firstinspires.ftc.teamcode.code201920;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

/**
 * A teleop that assigns ever motor to a joystick, mostly used for testing purposes. It is extremely rare that you should
 * ever have to edit this file
 *
 */
@Disabled
@TeleOp(name="TestTeleOp", group="TeleOp")
public class TestTeleOp2019 extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {
        robot.motorDrawerSlide.setPower(gamepad1.a?0.5:0);
        robot.grabber.setPower(gamepad1.b?0.5:0);
    }
}
