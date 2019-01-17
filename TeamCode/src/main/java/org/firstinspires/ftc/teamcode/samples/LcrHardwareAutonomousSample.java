package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareBobAlexanderIII;

/**
 * A sample LinearOpMode that shows how to use {@link HardwareBobAlexanderIII}.
 *
 * To use this sample:
 * <ol>
 *     <li>Copy this file</li>
 *     <li>Remove the @Disabled annotation</li>
 *     <li>Make the necessary changes to {@link #runOpMode()}</li>
 * </ol>
 */
@Disabled
@Autonomous(name="LcrHardwareAutonomousSample", group="autonomous")
public class LcrHardwareAutonomousSample extends LinearOpMode {
    private static final double DRIVE_SPEED = 0.5;
    private final HardwareBobAlexanderIII robot = new HardwareBobAlexanderIII(this, DRIVE_SPEED);

    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        // Things you can do:
        robot.moveMotor(robot.stronkboi, DRIVE_SPEED, 2); // Extend stronkboi for two seconds.
        robot.drive(30, 5); // Move thirty inches with a timeout of five seconds.
        robot.turn(40, 5); // Turn forty degrees counterclockwise with a timeout of five seconds.
        robot.autoSpin(1, 2); // Spin the front spinners inward for two seconds.
        robot.drive(false, 10, 10); // Move backward ten inches with a timeout of ten seconds.

    }
}
