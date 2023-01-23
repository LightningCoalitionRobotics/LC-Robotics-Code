package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;

@Autonomous(name="LeftOfPark", group = "autonomous")

public class LeftOfPark extends LinearOpMode {
    private HardwareGoobus robot = new HardwareGoobus(this);
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        robot.strafeRight(.7, 36,5);

    }
}
