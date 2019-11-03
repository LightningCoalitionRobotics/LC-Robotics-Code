package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="TestAutonomous", group = "autonomous")
public class TestAutonomous extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    private void test() {
        robot.drive(.5, 10, 60);    //test forwards
        sleep(2000);
        robot.drive(-0.5, 10, 60);  //test backwards
        sleep(2000);
        robot.drive(1, 20, 5);      //test timeout
        sleep(2000);
    }

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        test();
    }
}
