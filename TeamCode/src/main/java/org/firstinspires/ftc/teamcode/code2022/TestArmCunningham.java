/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="TestArmCunningham", group="autonomous")
public class TestArmCunningham extends LinearOpMode {
    HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        robot.arm2.setPosition(1);
        sleep(1000);
        robot.arm2.setPosition(0);

    }

}

 */