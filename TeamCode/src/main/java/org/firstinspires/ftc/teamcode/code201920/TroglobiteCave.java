package org.firstinspires.ftc.teamcode.code201920;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;
@Disabled
@Autonomous(name="TroglobiteCave", group = "autonomous")
public class TroglobiteCave extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    public void cupidShuffle(){
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);

        robot.motorFrontRight.setPower(1);
        robot.motorBackRight.setPower(1);
        sleep(500);
        //kicks right side forward
        robot.motorBackRight.setPower(-1);
        robot.motorFrontRight.setPower(-1);
        sleep(500);
        //kicks right side back
        robot.stop();
        //stops the right motors
        robot.motorFrontLeft.setPower(1);
        robot.motorBackLeft.setPower(1);
        sleep(500);
        //kicks left side forward
        robot.motorBackLeft.setPower(-1);
        robot.motorFrontLeft.setPower(-1);
        sleep(500);
        //kick left side back
        robot.stop();
        //stops the left motors
        robot.motorFrontRight.setPower(1);
        robot.motorBackRight.setPower(1);
        sleep(500);
        robot.motorBackRight.setPower(-1);
        robot.motorFrontRight.setPower(-1);
        sleep(500);
        robot.stop();
        sleep(100);
        robot.motorFrontLeft.setPower(1);
        robot.motorBackLeft.setPower(1);
        sleep(500);
        robot.motorBackLeft.setPower(-1);
        robot.motorFrontLeft.setPower(-1);
        sleep(500);
        robot.stop();
        sleep(500);

        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        //makes the robot turn side to side
        robot.turn(1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(-1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);

        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        //robot does a quarter turn

        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);

        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);

        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);

        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);

        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(-1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -60, 3);
        sleep(500);

        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
        robot.turn(1, 60, 3);
        sleep(500);
        robot.turn(1, -30, 3);
        sleep(500);
            }
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        cupidShuffle();
    }
}


