package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@TeleOp(name="MotorCounter2019", group="Teleop")
public class MotorCounter2019 extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);
    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            telemetry.addData("Exception",e);
        }

        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        topLeftPos = robot.motorFrontLeft.getCurrentPosition();
        topRightPos = robot.motorFrontRight.getCurrentPosition();
        bottomLeftPos = robot.motorBackLeft.getCurrentPosition();
        bottomRightPos = robot.motorBackRight.getCurrentPosition();
    }

    private int topLeftPos;
    private int topRightPos;
    private int bottomLeftPos;
    private int bottomRightPos;

    @Override
    public void loop() {

        int countsPerRevolutionTopLeft;
        int countsPerRevolutionTopRight;
        int countsPerRevolutionBottomLeft;
        int countsPerRevolutionBottomRight;

        if (gamepad1.a) { // Turn
            robot.motorFrontLeft.setPower(0.1);
            robot.motorFrontRight.setPower(0.1);
            robot.motorBackLeft.setPower(0.1);
            robot.motorBackRight.setPower(0.1);
        } else if (gamepad1.y) { // Forward
            robot.motorFrontLeft.setPower(0.1);
            robot.motorFrontRight.setPower(0.1);
            robot.motorBackRight.setPower(0.1);
            robot.motorBackLeft.setPower(0.1);
        } else if (gamepad1.x) { // Left
            robot.motorFrontLeft.setPower(0.1);
            robot.motorFrontRight.setPower(0.1);
            robot.motorBackRight.setPower(0.1);
            robot.motorBackLeft.setPower(0.1);
        } else {
            robot.motorFrontLeft.setPower(0);
            robot.motorFrontRight.setPower(0);
            robot.motorBackRight.setPower(0);
            robot.motorBackLeft.setPower(0);
        }

        countsPerRevolutionTopLeft = robot.motorFrontLeft.getCurrentPosition() - topLeftPos;
        countsPerRevolutionTopRight = robot.motorFrontRight.getCurrentPosition() + topRightPos;
        countsPerRevolutionBottomLeft = robot.motorBackLeft.getCurrentPosition() - bottomLeftPos;
        countsPerRevolutionBottomRight = robot.motorBackRight.getCurrentPosition() + bottomRightPos;

        telemetry.addData("Current top left position:", robot.motorFrontLeft.getCurrentPosition());
        telemetry.addData("Current top right position", robot.motorFrontRight.getCurrentPosition());
        telemetry.addData("Current bottom left position:", robot.motorBackLeft.getCurrentPosition());
        telemetry.addData("Current bottom right position", robot.motorBackRight.getCurrentPosition());

        telemetry.addData("Counts per revolution (top left)", countsPerRevolutionTopLeft);
        telemetry.addData("Counts per revolution (top right)", countsPerRevolutionTopRight);
        telemetry.addData("Counts per revolution (bottom left)", countsPerRevolutionBottomLeft);
        telemetry.addData("Counts per revolution (bottom right)", countsPerRevolutionBottomRight);

        telemetry.update();
    }
}
