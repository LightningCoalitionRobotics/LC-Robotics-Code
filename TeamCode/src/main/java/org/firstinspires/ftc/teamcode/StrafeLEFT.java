package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

public class StrafeLEFT extends LinearOpMode {


    HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT, .7, 15, 50 );
        robot.stop();

    }
}
