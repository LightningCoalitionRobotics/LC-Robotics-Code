package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@Autonomous(name="BlueAuto2022", group = "autonomous")

public class BlueAuto2022 extends LinearOpMode {
    private HardwareCletus robot = new HardwareCletus(this);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        robot.drive(-0.25, 28, 5);
        sleep(2000);
        robot.extend();
        sleep(2000);
        robot.turn(-0.5, 90, 5);
        sleep(2000);
        robot.drive(-0.5, 66, 10);
        robot.stop();
    }
}


