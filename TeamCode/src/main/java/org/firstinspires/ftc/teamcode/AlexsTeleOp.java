package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@TeleOp(name="AlexsTeleOp", group="TeleOp")
public class AlexsTeleOp extends OpMode {
    private HardwareCletus robot = new HardwareCletus(this);

    public void init() {
        robot.init(hardwareMap);
    }
    double speed = 0.5;

    public void loop() {
        //movement gamepad: triggers for forward/backward, b/x for strafing left and right, right joystick for moving tangent, left joystick for turning, dpad for changing speed
        //arm gamepad: y/a to move arm to max/min height, b/x to open and close claw, dpad for more precise height changes

        if(gamepad1.dpad_up){
            speed *= 2;
        } else if(gamepad1.dpad_down){
            speed /= 2;
        }

        //controls for the movement gamepad
        if(gamepad1.atRest()){
           robot.stop();
        } else if(gamepad1.left_trigger > 0.49) {
            //moves forwards
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
        } else if(gamepad1.right_trigger > 0.49){
            //moves backwards
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(-speed);
        } else if(gamepad1.x){
            //strafes left
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);
        } else if(gamepad1.b){
            //strafe right
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);
        } else if(gamepad1.left_stick_x > 0.25){
            //turn right
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(-speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(-speed);
        } else if(gamepad1.left_stick_x < -0.25){
            //turn left
            robot.motorBackLeft.setPower(-speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(-speed);
            robot.motorFrontRight.setPower(speed);
        } else if(gamepad1.right_stick_x != 0 && gamepad1.right_stick_y != 0){
            double angle = Math.toDegrees(Math.atan(Math.abs(gamepad1.right_stick_y)/Math.abs(gamepad1.right_stick_x)));
            //drive tangent
        }
        //controls for the arm gamepad
        if(gamepad2.y){
            //move to max height
        } else if(gamepad2.a){
            //move to min height
        } else if(gamepad2.b){
            //open claw
        } else if(gamepad2.x){
            //close claw
        } else if(gamepad2.dpad_up){
            //move arm up
        } else if(gamepad2.dpad_down){
            //move arm down
        }
    }
}