package org.firstinspires.ftc.teamcode.code2022;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

import java.util.Arrays;

@TeleOp(name = "JacksTeleOp", group = "TeleOp")
public abstract class JacksTeleOp extends OpMode {
    private HardwareCletus robot = new HardwareCletus(this);

    public void init(){ robot.init(hardwareMap);}

    double speed = 0.5; // WARNING! DO NOT CHANGE THIS CONSTANT! ONLY CHANGE THE SPEED ON THE ROBOT USING BUMPERS! 

    public void loop() {
//Speed setting --> Final Speed Variable will be fspeed
        if (gamepad1.left_bumper && speed <=.95){  //for increasing or decreasing speed from starting speed of .5 on interval (0, 1)
           speed +=  0.05;

        } else if(gamepad1.right_bumper && speed >=.05 ) {
             speed = speed -= 0.05;
        }

        telemetry. addLine("Speed is now" + speed);


        if(gamepad1.atRest()) {
            robot.stopMotor();
        }

        
         if(gamepad1.right_stick_y > 0){ //right stick moves forward, normal position = 0
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
        }
         else if(gamepad1.right_stick_y < 0) { //right stick moves backward, normal position = 0
             robot.motorBackLeft.setPower(-speed);
             robot.motorBackRight.setPower(-speed);
             robot.motorFrontLeft.setPower(-speed);
             robot.motorFrontRight.setPower(-speed);
         }
         else if (gamepad1.left_stick_x > 0 && gamepad1.left_stick_y >= 0 || gamepad1.left_stick_x > 0 && gamepad1.left_stick_y <= 0 ){ //turn right
             robot.motorBackLeft.setPower(-speed);
             robot.motorBackRight.setPower(speed);
             robot.motorFrontLeft.setPower(-speed);
             robot.motorFrontRight.setPower(speed);

    }
         else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y >= 0 || gamepad1.left_stick_x < 0 && gamepad1.left_stick_y <= 0 ){ //turn left
             robot.motorBackLeft.setPower(speed);
             robot.motorBackRight.setPower(-speed);
             robot.motorFrontLeft.setPower(speed);
             robot.motorFrontRight.setPower(-speed);

         }

         else if (gamepad1.a){ //max speed A button
             speed = .95;



         } else if (gamepad1.b){//min speed B button
             speed = .05;
         }

         else if(gamepad1.x){//Exact medium speed = .5 = X button
             speed = .5;
         }

}

}

