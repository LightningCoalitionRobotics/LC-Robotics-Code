package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="TestTelemetry", group ="Autonomous")

public class TestTelemetry extends LinearOpMode {

    DcMotor leftMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor1;
    DcMotor rightMotor2;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor1 = hardwareMap.dcMotor.get("left_motor_1");
        leftMotor2 = hardwareMap.dcMotor.get("left_motor_2");
        rightMotor1 = hardwareMap.dcMotor.get("right_motor_1");
        rightMotor2 = hardwareMap.dcMotor.get("right_motor_2");

        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.FORWARD);
        rightMotor2.setDirection(DcMotor.Direction.FORWARD);

        leftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Encoders are at zero counts

        leftMotor1.setTargetPosition(1000);
        leftMotor2.setTargetPosition(1000);
        rightMotor1.setTargetPosition(1000);
        rightMotor2.setTargetPosition(1000);

        leftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("mode", "waiting");
        telemetry.update();

        leftMotor1.setPower(.25);
        leftMotor2.setPower(.25);
        rightMotor1.setPower(.25);
        rightMotor2.setPower(.25);

        while (opModeIsActive() && leftMotor1.isBusy()) {   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
            telemetry.addData("encoder-fwd-left1", leftMotor1.getCurrentPosition() + "  busy=" + leftMotor1.isBusy());
            telemetry.addData("encoder-fwd-right1", rightMotor1.getCurrentPosition() + "  busy=" + rightMotor1.isBusy());
            telemetry.addData("encoder-fwd-left2", leftMotor2.getCurrentPosition() + "  busy=" + leftMotor2.isBusy());
            telemetry.addData("encoder-fwd-right2", rightMotor2.getCurrentPosition() + "  busy=" + rightMotor2.isBusy());
            telemetry.update();
            idle();
        }

        leftMotor1.setPower(0);
        leftMotor2.setPower(0);
        rightMotor1.setPower(0);
        rightMotor2.setPower(0);

        resetStartTime();

        //ready to test 11/14; make note of registered counts for each motor HERE!
    }




}
