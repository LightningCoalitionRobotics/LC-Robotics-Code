package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="TestTelemetry", group ="Autonomous")

public abstract class TestTelemetry extends LinearOpMode {

    DcMotor leftMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor1;
    DcMotor rightMotor2;

        public void runOpmode() throws InterruptedException{
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



        }

}
