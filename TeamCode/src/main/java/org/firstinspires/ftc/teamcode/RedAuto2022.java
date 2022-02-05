package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@Autonomous(name="RedAuto2022", group = "autonomous")

public class RedAuto2022 extends LinearOpMode {
    private HardwareCletus robot = new HardwareCletus(this);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        robot.drive(-0.5, 20, 500);
        sleep(2000);
        robot.turn(-0.5, 45, 500);
        sleep(2000);
        robot.extend();
        sleep(2000);
        robot.turn(0.5, 135, 500);
        sleep(2000);
        robot.drive(-0.5, 66, 1000);
        robot.stop();
    }
}

