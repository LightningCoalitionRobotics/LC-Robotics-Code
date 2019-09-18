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

package org.firstinspires.ftc.teamcode.code201819;

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
import java.util.concurrent.TimeoutException;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static java.lang.Thread.sleep;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
@TeleOp(name="Drive", group="Teleops")

public class lcTeleOp extends OpMode {

    //Declare hardware
	// motor controllers
    DcMotorController wheelController;
    DcMotorController stronkBoiController;
    DcMotorController spinnerController;

	// motors
	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor tensioner;

	DcMotor stronkBoi;

	DcMotor leftSpinner, rightSpinner;

	// Servo Controller
    ServoController servoController;

    // Servo
    Servo idol;

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

    boolean spinnerStatus = false;

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
        tensioner = hardwareMap.dcMotor.get("tensioner");

        //reverse the right motor
        motorRight.setDirection(REVERSE);

        stronkBoi = hardwareMap.dcMotor.get("stronkBoi");

        leftSpinner = hardwareMap.dcMotor.get("leftSpinner");
        rightSpinner = hardwareMap.dcMotor.get("rightSpinner");

        servoController = hardwareMap.servoController.get("servoController");

        idol = hardwareMap.servo.get("idol");

        idol.setPosition(1);

    }

	/*
	 * This method will be called repeatedly in a loop
	 *
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
    public void init_loop(){

    }

    public void loop() {

		/*
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
			if(gamepad2.left_trigger > gamepad2.right_trigger) {

			    stronkBoiMove = -gamepad2.left_trigger;

            } else {

			    stronkBoiMove = gamepad2.right_trigger;

            }

            float spinner1Float = 0, spinner2Float = 0;

            //spinner collect / dispense
			if(gamepad2.right_bumper) {

			        spinner1Float = 1;
			        spinner2Float = 1;

			}
			if(gamepad2.left_bumper) {

			    spinner1Float = -1;
			    spinner2Float = -1;

            }

            // clip the right/left values so that the values never exceed +/- 1
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);

            // scale the joystick value to make it easier to control
            // the robot more precisely at slower speeds.
            right = (float) scaleInput(right);
            left = (float) scaleInput(left);

            stronkBoiMove = (float) scaleInput(stronkBoiMove);

            tensioner.setPower(scaleInput(Range.clip((float)gamepad2.left_stick_x, -1, 1)));

            //write the values to the motors
            motorRight.setPower(right/slowModeType);
            motorLeft.setPower(left/slowModeType);

			stronkBoi.setPower(stronkBoiMove);

			leftSpinner.setPower(spinner1Float);
			rightSpinner.setPower(spinner2Float);

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
        telemetry.addData("stronkboi position", stronkBoi.getCurrentPosition()/3.5);

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