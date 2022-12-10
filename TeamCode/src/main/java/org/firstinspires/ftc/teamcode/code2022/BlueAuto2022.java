package org.firstinspires.ftc.teamcode.code2022;

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

        robot.drive(-0.5, 20, 500);
        sleep(2000);
        robot.turn(0.5, 45, 500);
        sleep(2000);
//        robot.extend();       //Commenting out for now, because HardwareCletus does not have extend() defined, or the arm defined yet, and build will fail.
        sleep(2000);
        robot.turn(0.5, 45, 500);
        sleep(2000);
        robot.turn(0.5, 90, 500);
        sleep(2000);
        robot.drive(-0.5, 66, 1000);
        robot.stop();
//        robot.arm.setPower(-20);      //Commenting out for now, because HardwareCletus does not have arm defined yet, and build will fail.

    }
}


