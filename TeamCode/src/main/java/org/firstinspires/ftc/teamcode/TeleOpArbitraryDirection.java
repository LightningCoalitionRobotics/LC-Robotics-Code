package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

public class TeleOpArbitraryDirection extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

    }

    private void setPowersFromJoystick() {
        double frontLeftPower = gamepad1.left_stick_y;
        double frontRightPower = gamepad1.left_stick_y;
        double backLeftPower = gamepad1.left_stick_y;
        double backRightPower = gamepad1.left_stick_y;

        // Side to side

        frontLeftPower += gamepad1.left_stick_x;
        frontRightPower -= gamepad1.left_stick_x;
        backLeftPower -= gamepad1.left_stick_x;
        backRightPower += gamepad1.left_stick_x;

        // Correct proportions
        if (Math.abs(gamepad1.left_stick_x) != 1 && Math.abs(gamepad1.left_stick_y) != 1) {

        }

        // Turn

        frontLeftPower += gamepad1.right_stick_x;
        frontRightPower -= gamepad1.right_stick_x;
        backLeftPower += gamepad1.right_stick_x;
        backRightPower -= gamepad1.right_stick_x;

        // Correct powers

        if (Math.abs(frontLeftPower) > 1) {
            frontLeftPower /= Math.abs(frontLeftPower);
            frontRightPower /= Math.abs(frontLeftPower);
            backLeftPower /= Math.abs(frontLeftPower);
            backRightPower /= Math.abs(frontLeftPower);
        }
        if (Math.abs(frontRightPower) > 1) {
            frontRightPower /= Math.abs(frontRightPower);
            frontLeftPower /= Math.abs(frontRightPower);
            backLeftPower /= Math.abs(frontRightPower);
            backRightPower /= Math.abs(frontRightPower);
        }
        if (Math.abs(backLeftPower) > 1) {
            backLeftPower /= Math.abs(backLeftPower);
            frontLeftPower /= Math.abs(backLeftPower);
            frontRightPower /= Math.abs(backLeftPower);
            backRightPower /= Math.abs(backLeftPower);
        }
        if (Math.abs(backRightPower) > 1) {
            backRightPower /= Math.abs(backRightPower);
            frontLeftPower /= Math.abs(backRightPower);
            frontRightPower /= Math.abs(backRightPower);
            backLeftPower /= Math.abs(backRightPower);
        }
    }
}
