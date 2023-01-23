package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="RedLeftSideCenter", group = "autonomous")

public class RedLeftSideCenter extends LinearOpMode {
    private HardwareGoobus robot = new HardwareGoobus(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        //from origin of (36,9) or (108,135)
//        robot.LiftLowerArm(.5, 4, 5);
        robot.drive(.5, 3,  5);
        robot.turn(.5, 90, 5);
        robot.drive(.5, 24, 5);
        robot.turn(.5, -90, 5);
        robot.drive(.5, 24, 5);
        robot.turn(.5, 45, 5);
//        robot.LiftLowerArm(.5, 30, 5);
        robot.drive(.5, 11.97, 5);
        robot.open();
        robot.drive(-.5, 11.97, 5);
//        robot.LiftLowerArm(-.5, 34, 5);
        robot.turn(.5, -45, 5);
        robot.drive(-.5, 24, 5);
        robot.strafeRight(.5, 12, 5);

    }
}