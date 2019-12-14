package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="BlueStart2Covered", group = "autonomous")
public class BlueStart2Covered extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        robot.drive(0.5, 29, 50);
        int topRightBefore = robot.motorFrontRight.getCurrentPosition();
        int topLeftBefore = robot.motorFrontLeft.getCurrentPosition();
        int bottomLeftBefore = robot.motorBackLeft.getCurrentPosition();
        int bottomRightBefore = robot.motorBackRight.getCurrentPosition();
        //finding number of counts before while loop

        while (!skyblockDetected) {
            robot.motorFrontRight.setPower(-0.5);
            robot.motorFrontLeft.setPower(0.5);
            robot.motorBackLeft.setPower(-0.5);
            robot.motorBackRight.setPower(0.5);
        }
        //code to pick up block goes here

        int topRightAfter = robot.motorFrontRight.getCurrentPosition();
        int topLeftAfter = robot.motorFrontLeft.getCurrentPosition();
        int bottomLeftAfter = robot.motorBackLeft.getCurrentPosition();
        int bottomRightAfter = robot.motorBackRight.getCurrentPosition();

        int frontRightTarget = topRightAfter - topRightBefore;
        int frontLeftTarget = topLeftAfter - topLeftBefore;
        int backLeftTarget = bottomLeftAfter - bottomLeftBefore;
        int backRightTarget = bottomRightAfter - bottomRightBefore;
        //finding target amount of counts to get back to the start position

        while (robot.motorFrontRight.getCurrentPosition() >= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() <= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() >= backLeftTarget || robot.motorBackRight.getCurrentPosition() <= backRightTarget) {
            robot.motorFrontRight.setPower(0.5);
            robot.motorFrontLeft.setPower(-0.5);
            robot.motorBackLeft.setPower(0.5);
            robot.motorBackRight.setPower(-0.5);
        }
        robot.drive(-0.7, 30, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 51, 50);

        //code to put skyblock onto foundation goes here
    }

}