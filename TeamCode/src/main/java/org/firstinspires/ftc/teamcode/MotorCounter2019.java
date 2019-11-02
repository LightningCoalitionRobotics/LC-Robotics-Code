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
            robot.frontController.setMotorPower(2, 0.1);
            robot.frontController.setMotorPower(1, -0.1);
            robot.rearController.setMotorPower(2, -0.1);
            robot.rearController.setMotorPower(1, 0.1);
        } else if (gamepad1.x) { // Left
            robot.frontController.setMotorPower(2, -0.1);
            robot.frontController.setMotorPower(1, -0.1);
            robot.rearController.setMotorPower(2, 0.1);
            robot.rearController.setMotorPower(1, 0.1);
        } else {
            robot.frontController.setMotorPower(2, 0);
            robot.frontController.setMotorPower(1, 0);
            robot.rearController.setMotorPower(2, 0);
            robot.rearController.setMotorPower(1, 0);
        }

        countsPerRevolutionTopLeft = robot.frontController.getMotorCurrentPosition(2) - topLeftPos;
        countsPerRevolutionTopRight = robot.frontController.getMotorCurrentPosition(1) - topRightPos;
        countsPerRevolutionBottomLeft = robot.rearController.getMotorCurrentPosition(1) - bottomLeftPos;
        countsPerRevolutionBottomRight = robot.rearController.getMotorCurrentPosition(2) - bottomRightPos;

        telemetry.addData("Current top left position:", robot.frontController.getMotorCurrentPosition(2));
        telemetry.addData("Current top right position", robot.frontController.getMotorCurrentPosition(1));
        telemetry.addData("Current bottom left position:", robot.rearController.getMotorCurrentPosition(1));
        telemetry.addData("Current bottom right position", robot.rearController.getMotorCurrentPosition(2));

        telemetry.addData("Counts per revolution (top left)", countsPerRevolutionTopLeft);
        telemetry.addData("Counts per revolution (top right)", countsPerRevolutionTopRight);
        telemetry.addData("Counts per revolution (bottom left)", countsPerRevolutionBottomLeft);
        telemetry.addData("Counts per revolution (bottom right)", countsPerRevolutionBottomRight);

        telemetry.update();
    }
}
