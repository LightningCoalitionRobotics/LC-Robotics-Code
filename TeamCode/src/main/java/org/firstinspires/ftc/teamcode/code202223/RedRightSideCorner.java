package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;

@Autonomous(name="RedRightSideCorner", group = "autonomous")


public class RedRightSideCorner extends LinearOpMode {
    private HardwareGoobus robot = new HardwareGoobus(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();


        robot.drive(.5, 3,  5);
        robot.turn(.5, -90, 5);
        robot.drive(.5, 24, 5);
        robot.turn(.5, 90, 5);
        robot.drive(.5, 24, 5);
        robot.turn(.5, -45, 5);
        robot.LiftLowerArm(.5, 34, 5);
        robot.drive(.5, 11.97, 5);
        robot.open();
        robot.drive(-.5, 11.97, 5);
        robot.LiftLowerArm(-.5, 34, 5);
        robot.turn(.5, 45, 5);
        robot.strafe(.5, 84, 5);
        robot.drive(-.5, 24, 5);
    }
}
