/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * This autonomous utilizes TFOD, Vuforia, and encoders to detect and hit the proper mineral.
 *
 * The robot begins on the ground after releasing from the lander, and rotates 45 degrees* to
 * face the rightmost mineral. It then scans the mineral and, if it's the correct one, drives
 * forward and hits it. Otherwise, it rotates one third of 45 degrees* to face the next mineral.
 *
 * *45 degrees is an estimate this will have to be measured.
 */

@Autonomous(name="EncoderAuto", group="Autonomous")
public class EncoderAuto extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     turnErrorConstant = (1.75);
    static final double     reductionConstant = 0.2;
    static final double     COUNTS_PER_MOTOR_REV    = 30;    // eg: TETRIX Motor Encoder
    static final double     COUNTS_PER_INCH         = 3.5;
    static final double     DRIVE_SPEED             = 0.2;

    static final int sleep = 2000;

    // motor controllers
    DcMotorController wheelController;
    // motors
    DcMotor motorRight;
    DcMotor motorLeft;

    /*
    *
    * Vuforia and tensorflow definitions
    *
    */
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "ASSk3sX/////AAABmZWE717QekkdmC87AEyc0L6Na1AyUrqBEaj7NYyPpaf18ZBSjn+obOdkmGLU1q/w7LLf7qJc5azO7JMsOnmafRv5PeDR2pE7SHMjmW00ym0OesQecU0IsHE5LscvJT891aKTdEj+ED8rJLk8WXtotM4trkZvgt7X6bHp35nf1cuCJn7E4niNNdE1FiYADOTNSPTgCMUn1e2U/0x7SEUpMn+uFj/CQTmUMW+UurQi+OV/4FWHCK9Q6HyWmQsgY3BYs7BsEzk5D6PEVOilwvyk9tA7Rni+DjbwJcN/pOkMCucWYgLhIkQEL4/Wg6Aog8uqkRPi4mAB5n+2R+7PXpJzQ12xNBZbAJKJkvn7sPtEelId";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        // Initialize Vuforia and TFOD
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        //notify the driver
        telemetry.addData(">", "Vuforia/TFOD ready");
        telemetry.update();

        sleep(500);

        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");


        //reverse the right motor
        motorRight.setDirection(REVERSE);

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        sleep(sleep);   // optional pause after each move

        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        sleep(sleep);   // optional pause after each move

        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        sleep(sleep);   // optional pause after each move

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                motorRight.getCurrentPosition(),
                motorLeft.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //BEGIN DRIVING

        //Turn 45 degrees from starting point to rightmost mineral
        encoderTurn(DRIVE_SPEED,  60, 7); // Turn 90 degrees

        //define exit bool for detection loop
        boolean detectedGoldMineral = false;

        //Loop to position robot in front of gold mineral
        while(!detectedGoldMineral) {

            //Observe mineral in front of the bot
            if ((getObject() != null) && (getObject() == LABEL_GOLD_MINERAL)) {

                detectedGoldMineral = true;

            } else {

                encoderTurn(DRIVE_SPEED, (17), 7);

            }

        }

        //Move robot forward and hit mineral
        encoderDrive(DRIVE_SPEED, 36, 7);

        //Notify driver that path is complete
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double inches,
                             double timeoutS) {
        double newLeftTarget;
        double newRightTarget;

        boolean leftStop = false;
        boolean rightStop = false;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftTarget = motorLeft.getCurrentPosition() + (inches * COUNTS_PER_INCH);
            newRightTarget = motorRight.getCurrentPosition() + (inches * COUNTS_PER_INCH);

            // reset the timeout time and start motion.
            runtime.reset();
            motorRight.setPower(Math.abs(0.5));
            motorLeft.setPower(Math.abs(0.5));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.*/
            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (!leftStop || !rightStop)) {

                //Stop right motor if it's finished.
                if(motorRight.getCurrentPosition() >= newRightTarget) {

                    motorRight.setPower(0);
                    rightStop = true;

                }

                    //Stop left motor if it's finished.
                if ((motorLeft.getCurrentPosition()) >= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                // Display it for the driver.
                telemetry.addData("Path1 Right, Left",  "Running to %7d :%7d", ((int)newRightTarget),  ((int)newLeftTarget));
                telemetry.addData("Status Right, Left",  "Running at %7d :%7d",

                                            motorRight.getCurrentPosition(),
                        (Math.abs((motorLeft.getCurrentPosition()))));

                telemetry.addData("Mode Right", motorRight.getMode());
                telemetry.addData("Mode Left", motorLeft.getMode());
                telemetry.addData("Motor Right", motorRight.isBusy());
                telemetry.addData("Motor Left", motorLeft.isBusy());
                telemetry.update();
            }

            // Stop all motion;
            motorRight.setPower(0);
            motorLeft.setPower(0);

            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();
              sleep(2000);   // pause after each move
        }
    }

    public void encoderTurn(double speed,
                             double angle,
                             double timeoutS) {
        double newLeftTarget;
        double newRightTarget;

        boolean leftStop = false;
        boolean rightStop = false;

        double angleToInches = ((((angle)*(Math.PI)/180.0)*(5.66)*turnErrorConstant)); // Needs to be calibrated (angle -> radians * radius * constant.

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


                // Determine new target position, and pass to motor controller
                newLeftTarget = (motorLeft.getCurrentPosition() - (angleToInches*COUNTS_PER_INCH));
                newRightTarget = (motorRight.getCurrentPosition()) + (angleToInches*COUNTS_PER_INCH);


            // reset the timeout time and start motion.
            runtime.reset();

            if(angle > 0) {

                motorRight.setPower(Math.abs(0.5));
                motorLeft.setPower((-0.5));

            } else {

                motorRight.setPower((-0.5));
                motorLeft.setPower(Math.abs(0.5));

            }

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.*/
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (!leftStop || !rightStop)) {

                if(angle > 0) {

                    //Stop right motor if it's finished.
                    if (motorRight.getCurrentPosition() >= newRightTarget) {

                        motorRight.setPower(0);
                        rightStop = true;

                    }

                    //Stop left motor if it's finished.
                    if ((motorLeft.getCurrentPosition()) <= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                } else {

                    //Stop right motor if it's finished.
                    if (motorRight.getCurrentPosition() <= newRightTarget) {

                        motorRight.setPower(0);
                        rightStop = true;

                    }

                    //Stop left motor if it's finished.
                    if (((motorLeft.getCurrentPosition())) >= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                }

                // Display it for the driver.
                telemetry.addData("Turn Right, Left",  "Running to %7d :%7d", ((int)newRightTarget),  ((int)newLeftTarget));
                telemetry.addData("Status Right, Left",  "Running at %7d :%7d",

                        motorRight.getCurrentPosition(),
                        (Math.abs((motorLeft.getCurrentPosition()))));

                telemetry.addData("Mode Right", motorRight.getMode());
                telemetry.addData("Mode Left", motorLeft.getMode());
                telemetry.addData("Motor Right", motorRight.isBusy());
                telemetry.addData("Motor Left", motorLeft.isBusy());
                telemetry.update();
            }

            // Stop all motion;
            motorRight.setPower(0);
            motorLeft.setPower(0);

            sleep(500);

            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    //Method to return which object the robot sees
    public String getObject() {

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addData("Gold mineral", "detected");
                                    //update telemetry
                                    telemetry.update();
                                    return recognition.getLabel();
                                } else {

                                    telemetry.addData("Silver mineral", "detected");
                                    //update telemetry
                                    telemetry.update();
                                    return recognition.getLabel();

                            }

                        }
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }

        //if no object detected
        telemetry.addData("No mineral", "detected");
        //update telemetry
        telemetry.update();
        return null;

    }

}
