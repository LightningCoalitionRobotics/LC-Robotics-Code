package org.firstinspires.ftc.teamcode.code2022;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@Autonomous(name="RedAuto2022", group = "autonomous")

public class TestAuto2023 extends LinearOpMode {
    private HardwareCletus robot = new HardwareCletus(this);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        telemetry.addLine("Counts are 0");

        robot.drive(0.25, 30, 500);
        sleep(2000);

        telemetry.addData("Counts=", telemetry.update());


    }
}

