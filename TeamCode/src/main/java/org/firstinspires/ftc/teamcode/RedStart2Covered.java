package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

/**
 * This code is used to find the sky stone and then place it onto the build platform.
 * Its used for when the other team moves the build platform into the build zone.
 */
 @Autonomous(name = "RedStart2Covered", group = "autonomous")
public class RedStart2Covered extends LcVuforiaOpMode{

    @Override
    public void runTasks() {
        robot.init(hardwareMap);
        waitForStart();

        robot.extend(HardwareLilPanini.EXTENSION_TIME/4, 50);
        robot.drive(0.5, 29, 50);

        int frontRightTarget = robot.motorFrontRight.getCurrentPosition();
        int frontLeftTarget = robot.motorFrontLeft.getCurrentPosition();
        int backLeftTarget = robot.motorBackLeft.getCurrentPosition();
        int backRightTarget = robot.motorBackRight.getCurrentPosition();
        //finding number of counts before while loop

        while (!isVisible(stoneTarget) && opModeIsActive()) {
            robot.motorFrontRight.setPower(0.5);
            robot.motorFrontLeft.setPower(-0.5);
            robot.motorBackLeft.setPower(0.5);
            robot.motorBackRight.setPower(-0.5);
        }
        robot.extend(HardwareLilPanini.EXTENSION_TIME/-4, 69);
        robot.grab(1);

        while (opModeIsActive() && (robot.motorFrontRight.getCurrentPosition() <= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() >= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() <= backLeftTarget || robot.motorBackRight.getCurrentPosition() >= backRightTarget)) {
            robot.motorFrontRight.setPower(-0.5);
            robot.motorFrontLeft.setPower(0.5);
            robot.motorBackLeft.setPower(-0.5);
            robot.motorBackRight.setPower(0.5);
        }
        robot.drive(-0.7, 30, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 51, 50);

        robot.extend(HardwareLilPanini.EXTENSION_TIME, 50);
        robot.drive(0.7, 5, 50);
        robot.extend(-HardwareLilPanini.EXTENSION_TIME, 50);
        robot.release(1);
    }

}
