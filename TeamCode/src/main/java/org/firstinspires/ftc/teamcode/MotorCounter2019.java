package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

public class MotorCounter2019 extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);
    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.frontController.setMotorMode(1, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontController.setMotorMode(2, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rearController.setMotorMode(1, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rearController.setMotorMode(2, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            telemetry.addData("Exception",e);
        }

        robot.frontController.setMotorMode(1, DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontController.setMotorMode(2, DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rearController.setMotorMode(1, DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rearController.setMotorMode(2, DcMotor.RunMode.RUN_USING_ENCODER);

        topLeftPos = robot.frontController.getMotorCurrentPosition(2);
        topRightPos = robot.frontController.getMotorCurrentPosition(1);
        bottomLeftPos = robot.frontController.getMotorCurrentPosition(1);
        bottomRightPos = robot.rearController.getMotorCurrentPosition(2);
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

        if (gamepad1.a) {
            robot.frontController.setMotorPower(2, 0.1);
            robot.frontController.setMotorPower(1, 0.1);
            robot.rearController.setMotorPower(2, 0.1);
            robot.rearController.setMotorPower(1, 0.1);
        } else if (gamepad1.y) {
            robot.frontController.setMotorPower(2, 0.1);
            robot.frontController.setMotorPower(1, -0.1);
            robot.rearController.setMotorPower(2, -0.1);
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
