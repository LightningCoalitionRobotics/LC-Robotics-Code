package org.firstinspires.ftc.teamcode.code201819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="MotorCounter", group="Teleops")
public class MotorCounter extends OpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;

    private int countsPerRevolutionLeft = 0;
    private int countsPerRevolutionRight = 0;

    private int leftPos;
    private int rightPos;

    @Override
    public void init() {
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            telemetry.addData("Exception",e);
        }

        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftPos = motorLeft.getCurrentPosition();
        rightPos = motorRight.getCurrentPosition();
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            motorLeft.setPower(0.1);
            motorRight.setPower(-0.1);
        } else if (gamepad1.y) {
            motorLeft.setPower(0.1);
            motorRight.setPower(0.1);
        } else {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }

        countsPerRevolutionLeft = motorLeft.getCurrentPosition() - leftPos;
        countsPerRevolutionRight = motorRight.getCurrentPosition() - rightPos;

        telemetry.addData("Current left position:", motorLeft.getCurrentPosition());
        telemetry.addData("Current right position", motorRight.getCurrentPosition());
        telemetry.addData("Counts per revolution (left)", countsPerRevolutionLeft);
        telemetry.addData("Counts per revolution (right)", countsPerRevolutionRight);
        telemetry.update();
    }
}
