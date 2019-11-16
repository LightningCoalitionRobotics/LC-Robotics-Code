package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="BlueStart1", group = "autonomous")
public class BlueStart1 extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();


    }
}
