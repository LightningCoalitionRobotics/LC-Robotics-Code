package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "SomethingRandom", group = "autonomous")
public class SomethingRandom extends LcVuforiaOpMode {
    @Override
    public void runTasks() {
        robot.init(hardwareMap);
        waitForStart();
        start();


        robot.drive(.93, 10, 3);
        robot.turn(5, 2, 9);


        //Code if robot starts in the middle

        robot.drive(0.7, 44, 50);
        robot.turn(.6, -90, 50);
        robot.drive(0.4, 11, 50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6, 50);
        robot.turn(.5, -90, 50);
        robot.drive(1, 14, 50);
        robot.turn(1, -15, 50);
        //ONE
        robot.drive(1, 14, 50);
        robot.turn(1, -15, 50);
        //TWO
        robot.drive(1, 14, 50);
        robot.turn(1, -15, 50);
        //THREE
        robot.drive(1, 14, 50);
        robot.turn(1, -15, 50);
        //FOUR
        robot.drive(1, 14, 50);
        robot.turn(1, -15, 50);
        //FIVE
        robot.drive(1, 10, 50);
        robot.turn(1, -15, 50);
        //SIX


        robot.drive(1, 44, 50);

        for (int i = 0; i < 5; i++) {


            if (isVisible(stoneTarget)) {

                robot.stop();
                robot.grab();
                break;

            } else {
                robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, .5, 6, 50);
            }


        }

        robot.drive(.7,-25 , 50);
        robot.turn(.7, 90, 50);
        robot.drive(.7, 75, 50);
        robot.stop();
        robot.release();
    }
}
