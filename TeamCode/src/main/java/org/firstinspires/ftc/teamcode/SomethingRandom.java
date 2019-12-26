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
       robot.turn(5,2,9);

//---------------------------------------------------------------------------------------

        robot.drive(0.5,29, 50);
        robot.turn(.8, 165, 50);
        robot.drive(0.6, 24, 50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.5, 30, 50);
        robot.drive(0.5,37.5,50);
        robot.turn(0.5,180,50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.5,30, 50);
        robot.drive(0.5,9, 50);
        //Code for if robot starts in BlueStart2


        robot.drive(0.8,46, 50);
        robot.turn(.8, 165, 50);
        robot.drive(0.5, 24, 50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.5, 30, 50);
        robot.drive(0.5,37.5,50);
        robot.turn(0.5,180,50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.5,30, 50);
        robot.drive(0.5,9, 50);
        //Code for if robot starts in RedStart2

        

        //Code iff robot starts in the middle

        robot.drive(0.7, 44,50);
        robot.turn(.6, -90, 50);
        robot.drive(0.4, 11, 50 );
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6, 50);
        robot.turn(.5, -90, 50 );
        robot.drive(1, 14,50);
        robot.turn(1, -15, 50);
        //ONE
        robot.drive(1, 14,50);
        robot.turn(1, -15, 50);
        //TWO
        robot.drive(1, 14,50);
        robot.turn(1, -15, 50);
        //THREE
        robot.drive(1, 14,50);
        robot.turn(1, -15, 50);
        //FOUR
        robot.drive(1, 14,50);
        robot.turn(1, -15, 50);
        //FIVE
        robot.drive(1, 10,50);
        robot.turn(1, -15, 50);
        //SIX









        for(int i=0; i<5; i++) {
            //run 5 times
            robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6,50);
            robot.check;
                    //Robot checking using image recognition. Not sure what code to use: robot.(?)
            robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6,50);
            robot.check;
            robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6,50);
            robot.check;
            robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6,50);
            robot.check;
        }












    }


}
