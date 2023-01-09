package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="First Autonomous", group = "autonomous")

public class Autonomous_Code extends LinearOpMode {
    private HardwareCletus robot = new HardwareCletus(this);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

       /* robot.drive(0.5,15,50);
        robot.turn(0.5,90,50);
        robot.drive(0.5,15,50);
        robot.turn(0.5,90,50);
        robot.drive(0.5,15,50);
        robot.turn(0.5,90,50);
        robot.drive(0.5,15,50);
        robot.turn(0.5,90,50);
*/
        //Red Start 2 Test Code
        robot.drive(.5, 26,5);
        robot.turn(.45, -90, 5);

    }
}

/*
  One of the Autonomous Codes for the 2022-2023 School Year

  If more components are added, please add them to this class

  @author Ian Hedgecoth
  @author Maya Kirshtein
*/
