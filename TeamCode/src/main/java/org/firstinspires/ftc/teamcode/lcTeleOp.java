/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Boolean;
import java.util.ArrayList;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static java.lang.Thread.sleep;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
@TeleOp(name="Drive", group="Teleops")

public class lcTeleOp extends OpMode {
	


	// motor controllers
    DcMotorController wheelController;
    DcMotorController stronkBoiController;
    DcMotorController spinnerController;

	// motors
	DcMotor motorRight;
	DcMotor motorLeft;

	DcMotor stronkBoi;

	DcMotor leftSpinner, rightSpinner;

	// Servo Controller
    ServoController servoController;

    // Servo
    CRServo idol;

    // slow mode settings
    int slowModeType = 1;
    String displaySlowModeType = "Full Speed";

    //Boolean for turning recording on/off
    boolean recording = false;

    //ints, speed, and and time vars for tracking movements
    int oldPositionLeft, oldPositionRight = 0;
    ArrayList<Float> leftSpeedList = new ArrayList<Float>();
    ArrayList<Float> rightSpeedList = new ArrayList<Float>();
    ElapsedTime timer = new ElapsedTime();

    /**
	 * Constructor
	 */
	public lcTeleOp() {

	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {

		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

        motorRight = hardwareMap.dcMotor.get("motorRight");
		motorLeft = hardwareMap.dcMotor.get("motorLeft");

		//reverse the right motor
		motorRight.setDirection(REVERSE);

		stronkBoi = hardwareMap.dcMotor.get("stronkBoi");

		leftSpinner = hardwareMap.dcMotor.get("leftSpinner");
		rightSpinner = hardwareMap.dcMotor.get("rightSpinner");

		servoController = hardwareMap.servoController.get("servoController");

		idol = hardwareMap.crservo.get("idol");

        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        try {
            sleep(700);   //pause
        } catch(InterruptedException e) {}

	}

	@Override
    public void init_loop(){


    }

    public void loop() {

	    //Check if we want to record
        if(gamepad1.dpad_up) {

            //if not already recording
            if(!recording) {

                //start recording
                recording = true;
                //reset timer, speed lists, and encoder counts.
                timer.reset();
                oldPositionLeft = motorLeft.getCurrentPosition();
                oldPositionRight = motorRight.getCurrentPosition();
                leftSpeedList.clear();
                rightSpeedList.clear();

            } else {

                //stop recording
                recording = false;
                displayRecordedData(oldPositionLeft, oldPositionRight, timer, leftSpeedList, rightSpeedList);

            }

        }

		/*
		 * Gamepad 1
		 * 
		 * Gamepad 1 controls the motors via the left stick and right stick (tank drive)
		 */
            // slow mode type
            if(gamepad1.a || gamepad2.a){
                slowModeType = 1;
            }
            if(gamepad1.b || gamepad2.b){
                slowModeType = 2;
            }
            if(gamepad1.y || gamepad2.y) {
                slowModeType = 4;
            }
            if(gamepad1.x || gamepad2.x) {
				slowModeType = 8;
			}

            //tank drive:
            float right = -gamepad1.right_stick_y;
            float left = -gamepad1.left_stick_y;

            //StronkBoi (Lifter)
			float stronkBoiMove = 0;

			//Conditional makes it possible to use triggers instead of sticks to consolidate to one controller
			if(gamepad1.left_trigger > gamepad1.right_trigger) {

			    stronkBoiMove = -gamepad1.left_trigger;

            } else {

			    stronkBoiMove = gamepad1.right_trigger;

            }

            float spinner1Float = 0, spinner2Float = 0;

            //spinner collect / dispense
			if(gamepad1.right_bumper) {

				spinner1Float = 1;
				spinner2Float = 1;

			}

			if(gamepad1.left_bumper) {

				spinner1Float = -1;
				spinner2Float = -1;

			}

			//idol servo
            if(gamepad1.a) {

                idol.setPower(0.75);
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                }
                idol.setPower(0.25);
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                }
                idol.setPower(0.5);

            }

            // clip the right/left values so that the values never exceed +/- 1
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);

            // scale the joystick value to make it easier to control
            // the robot more precisely at slower speeds.
            right = (float) scaleInput(right);
            left = (float) scaleInput(left);

            stronkBoiMove = (float) scaleInput(stronkBoiMove);


            //write the values to the motors
            motorRight.setPower(right/slowModeType);
            motorLeft.setPower(left/slowModeType);

            //update the speed list if we're recording
            if(recording) {

                leftSpeedList.add(left/slowModeType);
                rightSpeedList.add(right/slowModeType);

            }

			stronkBoi.setPower(stronkBoiMove);

			leftSpinner.setPower(spinner1Float);
			rightSpinner.setPower(spinner2Float);

        /*
        * Gamepad 2:
        *
        * Gamepad 2 controls the arm shoulder via left stick, and arm elbow via the right stick
         */

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        //convert slowModeType to displaySlowModeType
            switch (slowModeType){
                case 1:
                    displaySlowModeType = "Normal Speed";
                    break;
                case 2:
                    displaySlowModeType = "Half Speed";
                    break;
                case 4:
                    displaySlowModeType = "Quarter Speed";
                    break;
                case 8:
                    displaySlowModeType = "Eighth Speed";
                    break;
                default:
                    displaySlowModeType = "Error!";
                    break;
            }

            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("speed", "Speed:" + displaySlowModeType);
            telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
            telemetry.addData("Recording: ", recording);
        }




	/*
	 * Code to run when the op mode is first disabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}

    	
	/*
	 * This method scales the joystick input so for low joystick values, the 
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */

	public void displayRecordedData(int oldPositionLeft, int oldPositionRight, ElapsedTime timer, ArrayList<Float> leftSpeedList, ArrayList<Float> rightSpeedList) {

	    int avSpeedLeft = 0;
	    int avSpeedRight = 0;

	    //display move time
        telemetry.addData("Move time:", timer.seconds());
        //reset time
        timer.reset();

        //display distance traveled
        telemetry.addData("Left motor displacement:", (motorLeft.getCurrentPosition()-oldPositionLeft)/3.5);
        telemetry.addData("Right motor displacement:", (motorRight.getCurrentPosition()-oldPositionRight)/3.5);
        telemetry.addData("Average displacement: ", (((motorLeft.getCurrentPosition()-oldPositionLeft)/3.5)+(motorRight.getCurrentPosition()-oldPositionRight)/3.5)/2);

	    //find the average speed of the left motor
        int counter = 0;

	    for(Float speed : leftSpeedList) {

            avSpeedLeft += speed;
            counter++;

        }

        //display average left speed
        avSpeedLeft /= counter;
        telemetry.addData("Average Left Speed:", avSpeedLeft);

        //find the average speed of the left motor

        //reset variables
        counter = 0;
        avSpeedRight = 0;

        for(Float speed : rightSpeedList) {

            avSpeedRight += speed;
            counter++;

        }

        avSpeedRight /= counter;
        //display average left speed
        telemetry.addData("Average Right Speed:", avSpeedRight);

        telemetry.addData("Average move speed:", (avSpeedLeft+avSpeedRight)/2);
        telemetry.update();

        //wait at this screen until driver presses dpad_down or one minute has elapsed
        while(!gamepad1.dpad_down && (timer.seconds()<60)) {

            //do nothing

        }

    }

	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
		
		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);
		
		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}


}


