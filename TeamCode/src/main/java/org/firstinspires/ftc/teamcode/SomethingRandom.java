package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "SomethingRandom", group = "autonomous")
public class SomethingRandom extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        start();



       robot.drive(.93, 10, 3);
       robot.turn(5,2,9)


               robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.5,5,3);
       //Green Light
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.5,5,3);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.5,5,3);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.5,5,3);
        //Red light
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0,0,1);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0,0,1);
        //Orange Light
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.22,2.2,1);




    }


}
