package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

/**
 * A sample Autonomous that shows you how to use HardwareLilPanini
 * Helpful to look over, it will give you an overview of everything we can currently do with the robot and how to do it
 *
 * @author Chris
 */

@Disabled //This serves to tell the phone not to download it, if you actually are going to create an autonomous program do not include this
@Autonomous(name="Lcr2019AutonomousSample", group="autonomous")
public class Lcr2019AutonomousSample extends LinearOpMode { //This class extends (inherits) properties from LinearOpMode
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    private void movements() {                                                                //Put what you want the robot to do here
        robot.drive(0.5,15,60);                                            // Robot should drive at half speed for at most 60 seconds or until it has gone 15 inches
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1,10,60); // Robot should strafe right 10 inches at full speed or until 60 seconds has passed
        robot.turn(1,60,60);                                              // Robot should turn 60 degrees at full speed or until 60 seconds has passed
        robot.driveAngle(30,1,10,60);                               // Robot should drive at a 30 degree angle at full speed for 60 inches, or for 60 second
//        robot.extend(HardwareLilPanini.INCHES_PER_EXTENSION, 60);                      // Robot should extend drawer slide
    }

    @Override // Let the code know that this will override anything conflicting in the class it inherited stuff from
    public void runOpMode(){ //When the init button is pressed
        robot.init(hardwareMap); //initializes the piece of code that tells it which motors are what
        waitForStart(); //Wait until we press start on the phone (remember you have to press init and then start when you're ready
        movements(); // Does your movements
    }
}
