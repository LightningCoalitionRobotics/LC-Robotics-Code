package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="BlueStart2Covered", group = "autonomous")
public class BlueStart2Covered extends LcVuforiaOpMode {

    @Override
    public void runTasks() {

        robot.init(hardwareMap);
        waitForStart();

        robot.drive(0.5, 29, 50);
        int frontRightTarget = robot.motorFrontRight.getCurrentPosition();
        int frontLeftTarget = robot.motorFrontLeft.getCurrentPosition();
        int backLeftTarget = robot.motorBackLeft.getCurrentPosition();
        int backRightTarget = robot.motorBackRight.getCurrentPosition();
        //finding number of counts before while loop

        while (!isVisible(stoneTarget)) {
            robot.motorFrontRight.setPower(-0.5);
            robot.motorFrontLeft.setPower(0.5);
            robot.motorBackLeft.setPower(-0.5);
            robot.motorBackRight.setPower(0.5);
        }
        //code to pick up block goes here

        while (robot.motorFrontRight.getCurrentPosition() >= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() <= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() >= backLeftTarget || robot.motorBackRight.getCurrentPosition() <= backRightTarget) {
            robot.motorFrontRight.setPower(0.5);
            robot.motorFrontLeft.setPower(-0.5);
            robot.motorBackLeft.setPower(0.5);
            robot.motorBackRight.setPower(-0.5);
        }
        robot.drive(-0.7, 30, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 51, 50);

    }

}