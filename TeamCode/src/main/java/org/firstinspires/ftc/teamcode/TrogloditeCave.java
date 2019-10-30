package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="TrogloditeCave", group = "autonomous")
public class TrogloditeCave extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    private void cupidShuffle(){
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(30);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
    }
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();
        cupidShuffle();
    }
}
