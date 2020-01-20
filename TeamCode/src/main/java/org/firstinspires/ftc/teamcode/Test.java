package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="Test", group="autonomous")
public class Test extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.5, 5, 50);
        robot.drive(0.5, 5, 50);
        robot.turn(0.5, -30, 50);
    }
}
