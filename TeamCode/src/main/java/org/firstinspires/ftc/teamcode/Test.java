package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;
@Disabled
@Autonomous(name="Test", group="autonomous")
public class Test extends LinearOpMode {
    private HardwareCletus robot = new HardwareCletus(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();


    }
}
