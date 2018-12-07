package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * An autonomous program whose purpose has not yet been determined.
 * @author Noah Simon
 */
public class NoahAutonomous extends LinearOpMode {
    HardwareBobAlexanderIII robot = new HardwareBobAlexanderIII();

    /**
     * Function called by a superclass to run the robot.
     * @author Noah Simon
     */
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initializing encoders");
        telemetry.update();
    }

    public void display(String value) {

    }
}
