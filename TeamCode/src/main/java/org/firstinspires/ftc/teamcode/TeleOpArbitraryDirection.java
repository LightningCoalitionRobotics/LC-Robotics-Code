package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@TeleOp(name="TeleOp Arbitrary Direction", group="TeleOp")
public class TeleOpArbitraryDirection extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.clearAll();
        if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0) {
            robot.stop();
            telemetry.addLine("Stopped");
        } else if (gamepad1.left_stick_x == 0) {
            correctPowersAndGo(gamepad1.left_stick_y + gamepad1.right_stick_x, gamepad1.left_stick_y - gamepad1.right_stick_x, gamepad1.left_stick_y + gamepad1.right_stick_x, gamepad1.left_stick_y - gamepad1.right_stick_x);
            telemetry.addData("Joystick (x,y)", "(0," + gamepad1.left_stick_y + ")");
            telemetry.addLine();
            telemetry.addData("Turn power", gamepad1.right_stick_x);
            telemetry.addLine();
            telemetry.addData("Motor powers TR TL BL BR", robot.motorFrontRight.getPower() + " " + robot.motorFrontLeft.getPower() + " " + robot.motorBackLeft.getPower() + " " + robot.motorBackRight.getPower());

        } else {
            setPowersFromJoystick();
        }

        if (gamepad2.right_bumper) {
            robot.grabber.setPosition(1);
            telemetry.addLine("Extending grabber");
        } else if (gamepad2.left_bumper) {
            robot.grabber.setPosition(0);
            telemetry.addLine("Retracting grabber");
        }

        robot.motorDrawerSlide.setPower(gamepad2.right_stick_y);
        telemetry.addData("Drawer slide power", gamepad2.right_stick_y);

        telemetry.update();
    }

    private void setPowersFromJoystick() {
        // Correct proportions - basically i want to map the joystick circle on the circumscribed square because i can get better power
        // y = (gamepad1.left_stick_y / gamepad1.left_stick_x)x
        double squareDim;
        if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y)) {
            if (gamepad1.left_stick_x > 0) {
                // x = 1
                squareDim = gamepad1.left_stick_y / gamepad1.left_stick_x;
            } else {
                // x = -1
                squareDim = -gamepad1.left_stick_y / gamepad1.left_stick_x;
            }
        } else {
            if (gamepad1.left_stick_y > 0) {
                // y = 1
                squareDim = 1 / (gamepad1.left_stick_y / gamepad1.left_stick_x);
            } else {
                // y = -1
                squareDim = -1 / (gamepad1.left_stick_y / gamepad1.left_stick_x);
            }
        }
        double distCenterToEdgeOfSquare = Math.hypot(1, squareDim);
        double newY = gamepad1.left_stick_y * distCenterToEdgeOfSquare;
        double newX = gamepad1.left_stick_x * distCenterToEdgeOfSquare;

        // Forward/back

        @SuppressWarnings("SuspiciousNameCombination")
        double frontLeftPower = newY;
        @SuppressWarnings("SuspiciousNameCombination")
        double frontRightPower = newY;
        @SuppressWarnings("SuspiciousNameCombination")
        double backLeftPower = newY;
        @SuppressWarnings("SuspiciousNameCombination")
        double backRightPower = newY;

        // Side to side

        frontLeftPower -= newX;
        frontRightPower += newX;
        backLeftPower += newX;
        backRightPower -= newX;


        // Turn

        frontLeftPower -= gamepad1.right_stick_x;
        frontRightPower += gamepad1.right_stick_x;
        backLeftPower -= gamepad1.right_stick_x;
        backRightPower += gamepad1.right_stick_x;

        correctPowersAndGo(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        // Debug
        telemetry.addData("Joystick (x,y)", "(" + gamepad1.left_stick_x + "," + gamepad1.left_stick_y + ")");
        telemetry.addData("Mapped powers (x,y)", "(" + newX + "," + newY + ")");
        telemetry.addData("Turn power", gamepad1.right_stick_x);
        telemetry.addLine();
        telemetry.addData("Motor powers TR TL BL BR", frontRightPower + " " + frontLeftPower + " " + backLeftPower + " " + backRightPower);
    }

    private void correctPowersAndGo(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
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

        // Set motors

        robot.motorFrontLeft.setPower(frontLeftPower);
        robot.motorFrontRight.setPower(frontRightPower);
        robot.motorBackLeft.setPower(backLeftPower);
        robot.motorBackRight.setPower(backRightPower);
    }
}
