package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "SomethingRandom", group = "autonomous")
public class SomethingRandom extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        start();

//        int ageOfRobot = 20;
//        int ageInWeeks = 7300;
//
//        public void blowout()

                //10 inches

       robot.drive(.93, 10, 3);







    }
    HardwareLilPanini robot = new HardwareLilPanini(this);


}
