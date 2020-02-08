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

        robot.extend(3, 50, 0.3);
        robot.release(5);
        robot.drive(0.5, 10, 50);

        int frontRightTarget = robot.motorFrontRight.getCurrentPosition();
        int frontLeftTarget = robot.motorFrontLeft.getCurrentPosition();
        int backLeftTarget = robot.motorBackLeft.getCurrentPosition();
        int backRightTarget = robot.motorBackRight.getCurrentPosition();
        //finding number of counts before while loop

        while (opModeIsActive() && !isVisible(stoneTarget)) {
            robot.motorFrontRight.setPower(0.37);
            robot.motorFrontLeft.setPower(-0.3);
            robot.motorBackLeft.setPower(0.3);
            robot.motorBackRight.setPower(-0.37);
        }
        robot.extend(-3, 50, 0.3);
        robot.grab(1);

        while (opModeIsActive() && (robot.motorFrontRight.getCurrentPosition() >= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() <= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() >= backLeftTarget || robot.motorBackRight.getCurrentPosition() <= backRightTarget)) {
            robot.motorFrontRight.setPower(-0.37);
            robot.motorFrontLeft.setPower(0.3);
            robot.motorBackLeft.setPower(-0.3);
            robot.motorBackRight.setPower(0.37);
        }
        robot.drive(-0.7, 25, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 49, 50);

        robot.extend(5, 50);
        robot.drive(0.5, 3, 50);
        robot.extend(-2, 50);
        robot.release(1);

    }

}





     /*@Override
    public void runTasks() {
        robot.init(hardwareMap);
        waitForStart();

        robot.extend(HardwareLilPanini.EXTENSION_INCHES/4, 50);
        robot.drive(0.5, 29, 50);

        int frontRightTarget = robot.motorFrontRight.getCurrentPosition();
        int frontLeftTarget = robot.motorFrontLeft.getCurrentPosition();
        int backLeftTarget = robot.motorBackLeft.getCurrentPosition();
        int backRightTarget = robot.motorBackRight.getCurrentPosition();
        //finding number of counts before while loop

        while (!isVisible(stoneTarget)) {
            robot.motorFrontRight.setPower(0.5);
            robot.motorFrontLeft.setPower(-0.5);
            robot.motorBackLeft.setPower(0.5);
            robot.motorBackRight.setPower(-0.5);
        }
        robot.extend(HardwareLilPanini.EXTENSION_INCHES/-4, 69);
        robot.grab(1);

        while (robot.motorFrontRight.getCurrentPosition() <= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() >= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() <= backLeftTarget || robot.motorBackRight.getCurrentPosition() >= backRightTarget) {
            robot.motorFrontRight.setPower(-0.5);
            robot.motorFrontLeft.setPower(0.5);
            robot.motorBackLeft.setPower(-0.5);
            robot.motorBackRight.setPower(0.5);
        }
        robot.drive(-0.7, 30, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 51, 50);

        robot.extend(HardwareLilPanini.EXTENSION_INCHES, 50);
        robot.drive(0.7, 5, 50);
        robot.extend(-HardwareLilPanini.EXTENSION_INCHES+5, 50);
        robot.release(1);
    }

      */



