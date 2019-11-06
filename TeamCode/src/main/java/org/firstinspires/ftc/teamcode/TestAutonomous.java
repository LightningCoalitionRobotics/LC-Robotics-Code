package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

/**
 * This OpMode should be used to test the functions of HardwareLilPanini.java.
 * Each clump of 3 lines first sends telemetry describing what the robot should do, then does it, then sleeps for 2 seconds
 * @author Chris
 */

@Autonomous(name="TestAutonomous", group = "autonomous")
public class TestAutonomous extends LinearOpMode {

    private HardwareLilPanini robot = new HardwareLilPanini(this);

    private void testdrive() {
        telemetry.addLine("Testing Drive function: Forwards at half speed");
        robot.drive(.5, 10, 60);    //test forwards
        sleep(2000);

        telemetry.addLine("Testing Drive function: Backwards at half speed");
        robot.drive(-0.5, 10, 60);  //test backwards
        sleep(2000);

        telemetry.addLine("Testing Drive function: Timeout after 5 seconds");
        robot.drive(1, 20, 5);      //test timeout
        sleep(2000);
        //remember, the reason we don't test negative distance is because distance should never be negative
    }
    private void testturn() {
        telemetry.addLine("Testing Turn function: 30 degrees at half speed");
        robot.turn(.5,30,60); //testing positive angle and positive speed
        sleep(2000);

        telemetry.addLine("Testing Turn function: -30 degrees  at half speed");
        robot.turn(.5,-30,60); //test negative angle and positive speed
        sleep(2000);

        telemetry.addLine("Testing Turn function: Timeout after 5 seconds");
        robot.turn(1,700,5); //test timeout
        sleep(2000);
        //remember, the reason we don't test negative speed is because speed should never be negative
    }

    private void teststrafe() {
        telemetry.addLine("Testing Strafe function: Left at half speed for 10 inches");
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,.5,10,60);
        sleep(2000);

        telemetry.addLine("Testing Strafe function: Right at half speed for 10 inches");
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,.5,10,60);
        sleep(2000);

        telemetry.addLine("Testing Strafe function: Timeout after 5 seconds");
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,.5,40,5);
        sleep(2000);
        //once again, speed and distance should always be positive so no need to test those
    }
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        testdrive();
        testturn();
        teststrafe();
        // testdriveangle();
    }
}
