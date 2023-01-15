package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="OpeningMoveLeftSide", group = "autonomous")

public class OpeningMoveLeftSide extends LinearOpMode {
    private HardwareGoobus robot = new HardwareGoobus(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        robot.drive(.5, 84, 5);
        robot.turn(.5, -90, 5);
        robot.drive(.5, 96, 5);
        robot.turn(.5, 90, 5);
        robot.drive(.5, 29, 5);
    }
}