package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name = "Strafe_RIGHT", group = "autonomous")
public class Strafe_LEFT extends LinearOpMode {

    HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        start();

        robot.motorFrontRight.setPower(1);
        robot.motorFrontLeft.setPower(-1);
        robot.motorBackRight.setPower(-1);
        robot.motorBackLeft.setPower(1);

        sleep(3000);

        robot.motorFrontRight.setPower(0);
        robot.motorFrontLeft.setPower(0);
        robot.motorBackRight.setPower(0);
        robot.motorBackLeft.setPower(0);


        robot.stop();

    }

}