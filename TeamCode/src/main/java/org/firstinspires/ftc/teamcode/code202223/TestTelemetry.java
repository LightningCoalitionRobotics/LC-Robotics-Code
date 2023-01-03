package org.firstinspires.ftc.teamcode.code202223;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.HardwareCletus;

@Autonomous(name="TestTelemetry", group ="Autonomous")

public class TestTelemetry extends LinearOpMode {

    private HardwareCletus robot = new HardwareCletus(this);

    private DcMotor leftMotor1; //defines each motor of the robot
    private DcMotor leftMotor2;
    private DcMotor rightMotor1;
    private DcMotor rightMotor2;

    //front motors are motor 1 and back motors are motor 2

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        leftMotor1 = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        leftMotor2 = hardwareMap.get(DcMotor.class, "motorBackLeft");
        rightMotor1 = hardwareMap.get(DcMotor.class, "motorFrontRight");
        rightMotor2 = hardwareMap.get(DcMotor.class, "motorBackRight");

       /* leftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //makes sure encoders start at zero counts
        leftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
*/
        robot.drive(.25, 50, 25); //simple autonomous command

        while(opModeIsActive()) {      //using while loop allows for more time to check telemetry

            telemetry.addData("Counts:", "leftMotor1=%d leftMotor2=%d rightMotor1=%d rightMotor2=%d",
                    leftMotor1.getCurrentPosition(), leftMotor2.getCurrentPosition(), rightMotor1.getCurrentPosition(), rightMotor2.getCurrentPosition());

            telemetry.update();

        }






    }
    }





       /* leftMotor1 = hardwareMap.dcMotor.get("motorFrontLeft");
        leftMotor2 = hardwareMap.dcMotor.get("motorBackLeft");
        rightMotor1 = hardwareMap.dcMotor.get("motorFrontRight");
        rightMotor2 = hardwareMap.dcMotor.get("motorBackRight");

        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.FORWARD);
        rightMotor2.setDirection(DcMotor.Direction.FORWARD);

        leftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Encoders are at zero counts

        leftMotor1.setTargetPosition(3000);
        leftMotor2.setTargetPosition(3000);
        rightMotor1.setTargetPosition(3000);
        rightMotor2.setTargetPosition(3000);

        leftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("count", "currentCount");
        telemetry.update();

        leftMotor1.setPower(.25);
        leftMotor2.setPower(.25);
        rightMotor1.setPower(.25);
        rightMotor2.setPower(.25);

        while (opModeIsActive() && leftMotor1.isBusy()) {   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
            telemetry.addData("encoder-fwd-left1", leftMotor1.getCurrentPosition() + "  busy=" + leftMotor1.isBusy());
            telemetry.addData("encoder-fwd-right1", rightMotor1.getCurrentPosition() + "  busy=" + rightMotor1.isBusy());
            telemetry.addData("encoder-fwd-left2", leftMotor2.getCurrentPosition() + "  busy=" + leftMotor2.isBusy());
            telemetry.addData("encoder-fwd-right2", rightMotor2.getCurrentPosition() + "  busy=" + rightMotor2.isBusy());
            telemetry.update();
            idle();
        }


        //ready to test 11/14; make note of registered counts for each motor HERE!
    }


    } */

