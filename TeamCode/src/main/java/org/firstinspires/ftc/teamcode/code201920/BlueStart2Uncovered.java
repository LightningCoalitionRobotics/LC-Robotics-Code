package org.firstinspires.ftc.teamcode.code201920;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.LcVuforiaOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

/**
 * This code is used to find the sky stone and then move it into the build zone.
 * This one is used for if the other team doesn't move the platform into the build zone
 */
@Disabled
@Autonomous(name = "BlueStart2Uncovered", group = "autonomous")
public class BlueStart2Uncovered extends LcVuforiaOpMode {

    @Override
    public void runTasks() {
        robot.init(hardwareMap);
        waitForStart();

        robot.extend(4, 50);
        robot.release(5);
        robot.drive(0.5, 10, 50);

        int frontRightTarget = robot.motorFrontRight.getCurrentPosition();
        int frontLeftTarget = robot.motorFrontLeft.getCurrentPosition();
        int backLeftTarget = robot.motorBackLeft.getCurrentPosition();
        int backRightTarget = robot.motorBackRight.getCurrentPosition();
        //finding number of counts b ;before while loop

        while (!opModeIsActive() && isVisible(stoneTarget)) {
            robot.motorFrontRight.setPower(-0.43);
            robot.motorFrontLeft.setPower(0.3);
            robot.motorBackLeft.setPower(-0.3);
            robot.motorBackRight.setPower(0.43);
        }

        robot.extend(4, 69);
        robot.grab(1);

        while (opModeIsActive() && (robot.motorFrontRight.getCurrentPosition() >= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() <= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() >= backLeftTarget || robot.motorBackRight.getCurrentPosition() <= backRightTarget)) {
            robot.motorFrontRight.setPower(0.43);
            robot.motorFrontLeft.setPower(-0.3);
            robot.motorBackLeft.setPower(0.3);
            robot.motorBackRight.setPower(-0.43);
        }
        robot.drive(-0.7, 30, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 75, 50);

        robot.release(1);
        

    }
}
