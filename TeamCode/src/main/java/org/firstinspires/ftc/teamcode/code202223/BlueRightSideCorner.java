package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;

@Autonomous(name="BlueRightSideCorner", group = "autonomous")


public class BlueRightSideCorner extends LinearOpMode {
    private HardwareGoobus robot = new HardwareGoobus(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();


//        robot.LiftLowerArm(.5, 4, 5);
        robot.drive(.5, 3,  5);
        robot.turn(.5, -90, 5);
        robot.drive(.5, 24, 5);
        robot.turn(.5, 90, 5);
        robot.drive(.5, 24, 5);
        robot.turn(.5, -45, 5);
//        robot.LiftLowerArm(.5, 30, 5);
        robot.drive(.5, 11.97, 5);
        robot.open();
        robot.drive(-.5, 11.97, 5);
//        robot.LiftLowerArm(-.5, 34, 5);
        robot.turn(.5, 45, 5);
        robot.strafe(.5, 60, 5);
        robot.drive(-.5, 24, 5);
    }
}

