package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="TrogloditeCave", group = "autonomous")
public class TrogloditeCave extends LinearOpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    private void cupidShuffle(){
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 4, 5 );
        sleep(30);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.RIGHT,1, 8, 5 );
        sleep(30);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.strafe(HardwareLilPanini.HorizontalDirection.LEFT,1, 4, 5 );
        sleep(20);
        robot.frontController.setMotorPower(1, -1);
        robot.rearController.setMotorPower(2, -1);
        sleep(50);
        //kicks right side forward
        robot.frontController.setMotorPower(1, 1);
        robot.rearController.setMotorPower(2, 1);
        sleep(50);
        //kicks right side back
        robot.frontController.setMotorPower(2, 0);
        robot.rearController.setMotorPower(1, 0);
        //stops the right motors
        robot.frontController.setMotorPower(2, 1);
        robot.rearController.setMotorPower(1, 1);
        sleep(50);
        //kicks left side forward
        robot.frontController.setMotorPower(2, -1);
        robot.rearController.setMotorPower(1, -1);
        sleep(50);
        //kick left side back
        robot.frontController.setMotorPower(1, 0);
        robot.rearController.setMotorPower(2, 0);
        //stops the left motors
        robot.frontController.setMotorPower(1, -1);
        robot.rearController.setMotorPower(2, -1);
        sleep(50);
        //kicks right side forward
        robot.frontController.setMotorPower(1, 1);
        robot.rearController.setMotorPower(2, 1);
        sleep(50);
        //kicks right side back
        robot.frontController.setMotorPower(2, 0);
        robot.rearController.setMotorPower(1, 0);
        //stops the right motors
        robot.frontController.setMotorPower(2, 1);
        robot.rearController.setMotorPower(1, 1);
        sleep(50);
        //kicks left side forward
        robot.frontController.setMotorPower(2, -1);
        robot.rearController.setMotorPower(1, -1);
        sleep(50);
        //kick left side back
        robot.frontController.setMotorPower(1, 0);
        robot.rearController.setMotorPower(2, 0);
        //stops the left motors
        robot.turn(-1, 30, 3);
        sleep(50);
        robot.turn(1, 60, 3);
        sleep(100);
        //makes the robot turn side to side
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);

        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        //robot does a quarter turn
        robot.turn(-1, 30, 3);
        sleep(50);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(50);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(50);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 60, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
        robot.turn(1, 60, 3);
        sleep(100);
        robot.turn(-1, 30, 3);
        sleep(100);
            }
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();
        cupidShuffle();
    }
}
