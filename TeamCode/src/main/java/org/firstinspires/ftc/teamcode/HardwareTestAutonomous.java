package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareBobAlexanderIII;

/**
 * An autonomous program to test the features in {@link org.firstinspires.ftc.teamcode.hardware.HardwareBobAlexanderIII}.
 * @author Noah Simon
 */
@Autonomous(name="HardwareTestAutonomous", group="autonomous")
public class HardwareTestAutonomous extends LinearOpMode {
    private HardwareBobAlexanderIII robot = new HardwareBobAlexanderIII(this, 0.5);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.moveMotor(robot.lifter, 0.5, 2000);
        telemetry.addData("Status", "On ground");
        telemetry.update();

        robot.drive(30, 10);
        telemetry.addData("Status", "Finished drive.");
        telemetry.update();

        robot.turn(90, 5);
        telemetry.addData("Status", "Finished turn.");
        telemetry.update();

        robot.drive(false, 15, 5);
        telemetry.addData("Status", "Finished reverse.");
        telemetry.update();
    }
}
