package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name = "Strafe_RIGHT", group = "autonomous")
public class Strafe_RIGHT extends LinearOpMode {

    HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        start();

        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.3, 15, 50);
        robot.stop();

    }

}