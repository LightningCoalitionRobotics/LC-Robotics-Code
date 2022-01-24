package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;
@Disabled
@Autonomous(name="BlueStart1", group = "autonomous")

public class BlueStart1 extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        robot.drive(0.5,46, 50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT, 0.5, 29, 50);
        robot.drive(-0.5, 24, 50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.5, 30, 50);
        robot.drive(0.5,37.5,50);
        robot.turn(0.5,180,50);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, 0.5,30, 50);
        robot.drive(0.5,9, 50);

    }
}
