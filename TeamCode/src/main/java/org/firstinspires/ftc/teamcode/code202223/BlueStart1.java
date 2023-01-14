package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardwareGoobus;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="BlueStart2", group = "autonomous")

public class BlueStart1 extends LinearOpMode {
    private HardwareGoobus robot = new HardwareGoobus(this);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        //this is a test
    }
}