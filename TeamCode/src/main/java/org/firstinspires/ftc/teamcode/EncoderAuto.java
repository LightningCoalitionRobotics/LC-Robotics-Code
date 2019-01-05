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
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

/**
 * This autonomous utilizes TFOD, Vuforia, and encoders to detect and hit the proper mineral.
 *
 * The robot begins on the ground after releasing from the lander, and rotates 45 degrees* to
 * face the rightmost mineral. It then scans the mineral and, if it's the correct one, drives
 * forward and hits it. Otherwise, it rotates one third of 45 degrees* to face the next mineral.
 *
 * *45 degrees is an estimate. this will have to be measured.
 */

@Autonomous(name="EncoderAuto", group="Autonomous")
public class EncoderAuto extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    static final double turnErrorConstant = (1.75);
    static final double COUNTS_PER_INCH = 3.5;
    static final double DRIVE_SPEED = 0.5;
    static final double RUSH_SPEED = 0.8;
    static final double TURN_SPEED = 0.3;

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

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch = 25.4f;
    private static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    private static final String VUFORIA_KEY = "ASSk3sX/////AAABmZWE717QekkdmC87AEyc0L6Na1AyUrqBEaj7NYyPpaf18ZBSjn+obOdkmGLU1q/w7LLf7qJc5azO7JMsOnmafRv5PeDR2pE7SHMjmW00ym0OesQecU0IsHE5LscvJT891aKTdEj+ED8rJLk8WXtotM4trkZvgt7X6bHp35nf1cuCJn7E4niNNdE1FiYADOTNSPTgCMUn1e2U/0x7SEUpMn+uFj/CQTmUMW+UurQi+OV/4FWHCK9Q6HyWmQsgY3BYs7BsEzk5D6PEVOilwvyk9tA7Rni+DjbwJcN/pOkMCucWYgLhIkQEL4/Wg6Aog8uqkRPi4mAB5n+2R+7PXpJzQ12xNBZbAJKJkvn7sPtEelId";
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    public static VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    //Initialize current position, vector, and quadrant arrays
    //Course is 12ft by 12ft
    int[] currentPosition = new int[2];
    String[] quadrant = new String[2];
    int[] translationVector = new int[2];

    //initialize orientation variable
    private static double orientation;

    //initialize signs for vectors
    private static int iSign, jSign = 1;

    @Override
    public void runOpMode() {

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets that for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsRoverRuckus);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * This Rover Ruckus sample places a specific target in the middle of each perimeter wall.
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /**
         * To place the RedFootprint target in the middle of the red perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative Y axis to the red perimeter wall.
         */
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        /**
         * To place the FrontCraters target in the middle of the front perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative X axis to the front perimeter wall.
         */
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        /**
         * Create a transformation matrix describing where the phone is on the robot.
         *
         * The coordinate frame for the robot looks the same as the field.
         * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
         * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
         *
         * The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
         * pointing to the LEFT side of the Robot.  It's very important when you test this code that the top of the
         * camera is pointing to the left side of the  robot.  The rotation angles don't work if you flip the phone.
         *
         * If using the rear (High Res) camera:
         * We need to rotate the camera around it's long axis to bring the rear camera forward.
         * This requires a negative 90 degree rotation on the Y axis
         *
         * If using the Front (Low Res) camera
         * We need to rotate the camera around it's long axis to bring the FRONT camera forward.
         * This requires a Positive 90 degree rotation on the Y axis
         *
         * Next, translate the camera lens to where it is on the robot.
         * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
         */

        final int CAMERA_FORWARD_DISPLACEMENT  = 152;   // eg: Camera is 110 mm in front of robot center
        final int CAMERA_VERTICAL_DISPLACEMENT = 203;   // eg: Camera is 200 mm above ground
        final int CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == BACK ? 90 : 90, 0, 0));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        targetsRoverRuckus.activate();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {

            initTfod();
        } else {

            telemetry.addData("Sorry!", "This device is not compatible with TFOD");

        }


        //notify the driver
        telemetry.addData(">", "Vuforia/TFOD ready");
        telemetry.update();

        sleep(50);

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

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        sleep(100);   //pause

        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        sleep(sleep);   //pause

        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Mode Right", motorRight.getMode());
        telemetry.addData("Mode Left", motorLeft.getMode());
        telemetry.update();
        sleep(sleep);   //pause

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                motorRight.getCurrentPosition(),
                motorLeft.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //detatch from hook
        //exactEncoderDrive(DRIVE_SPEED, (-2), (-2), 5);
        //sleep(100);

        //Turn 62 degrees from starting point to rightmost mineral
        encoderTurn(TURN_SPEED, 62, 7); // Turn 90 degrees

        //define exit bool for detection loop
        boolean detectedGoldMineral = false;

        //Loop to position robot in front of gold mineral
        while (!detectedGoldMineral && opModeIsActive()) {

            //Observe mineral in front of the bot
            if ((getObject() != null) && (getObject() == LABEL_GOLD_MINERAL) && opModeIsActive()) {

                detectedGoldMineral = true;
                telemetry.addData("Detected:", "gold mineral");
                telemetry.update();

            } else {

                encoderTurn(TURN_SPEED, (19), 7);
                telemetry.addData("Detected:", "silver mineral");
                telemetry.update();

            }

        }

        //Move robot forward and hit mineral
        encoderDrive(DRIVE_SPEED, 39, 7);
        sleep(10);

        //go back from mineral
        exactEncoderDrive(DRIVE_SPEED, (-35), (-35), 7);

        //rotate back to original position then turn to face corner and drive forward 30 inches
        telemetry.addData("Turning", (-orientation+90) + "degrees");
        telemetry.update();

        //if(Math.abs(orientation-90) < 5) {
            encoderTurn(TURN_SPEED, (-orientation + 90), 7);
        //}

        sleep(50);
        encoderDrive(DRIVE_SPEED, 7, 5);
        telemetry.addData("Driving", "5");
        telemetry.update();
        sleep(100);

        sleep(100);
        encoderTurn(TURN_SPEED, 67.5, 7);
        telemetry.addData("Turning", "90");
        telemetry.update();

        sleep(100);
        encoderDrive(DRIVE_SPEED, 30, 5);
        telemetry.addData("Driving", "20");
        telemetry.update();

        //find the first vumark
        //Turn left until it sees the vumark
        while((visibleVumark(allTrackables) == "none") && opModeIsActive()) {

            //encoderTurn(TURN_SPEED, 25, 2);
            telemetry.addData("Detecting", "...");
            telemetry.update();
            sleep(50);

        }

        //Set quadrant entry depending on the vumark it sees
        if(visibleVumark(allTrackables) == "Back-Space") {

            quadrant[0] = "BACK";
            telemetry.addData("Detected", "Back-Space");
            telemetry.update();
            sleep(1000);

        } else if(visibleVumark(allTrackables) == "Front-Craters") {

            quadrant[0] = "FRONT";
            telemetry.addData("Detected", "Front-Craters");
            telemetry.update();
            sleep(1000);

        } else if(visibleVumark(allTrackables) == "Blue-Rover") {

            quadrant[1] = "BLUE";
            telemetry.addData("Detected", "Blue-Rover");
            telemetry.update();
            sleep(1000);

        } else {

            quadrant[1] = "RED";
            telemetry.addData("Detected", "Red-Footprint");
            telemetry.update();
            sleep(1000);

        }

        //back up 20
        sleep(50);
        exactEncoderDrive(DRIVE_SPEED, -30, -30, 5);
        telemetry.addData("Reversing", "20");
        telemetry.update();
        sleep(100);

        //Turn right so first vumark is out of view
        encoderTurn(TURN_SPEED, -140, 5);
        telemetry.addData("Turning", "-110");
        telemetry.update();
        sleep(100);

        //Drive 20
        sleep(50);
        encoderDrive(DRIVE_SPEED, 30, 5);
        telemetry.addData("Driving", "20");
        telemetry.update();

        //wait untul robot sees the vumark
        while(opModeIsActive() && (visibleVumark(allTrackables) == "none")) {

            telemetry.addData("Detecting", "...");
            telemetry.update();

        }

        //When second vumark is found, see what it is
        //Set quadrant entry depending on the vumark it sees
        if(visibleVumark(allTrackables) == "Back-Space") {

            quadrant[0] = "BACK";
            telemetry.addData("Detected", "Back-Space");
            telemetry.update();
            sleep(500);

        } else if(visibleVumark(allTrackables) == "Front-Craters") {

            quadrant[0] = "FRONT";
            telemetry.addData("Detected", "Front-Craters");
            telemetry.update();
            sleep(500);

        }

        if(visibleVumark(allTrackables) == "Blue-Rover") {

            quadrant[1] = "BLUE";
            telemetry.addData("Detected", "Blue-Rover");
            telemetry.update();
            sleep(500);

        } else if(visibleVumark(allTrackables) == "Red-Footprint") {

            quadrant[1] = "RED";
            telemetry.addData("Detected", "Red-Footprint");
            telemetry.update();
            sleep(500);

        }

        //back up 30
        exactEncoderDrive(DRIVE_SPEED, -30, -30, 5);
        telemetry.addData("Reversing", "30");
        telemetry.update();

        //orientation
        telemetry.addData("Orientation", orientation);
        telemetry.update();
        sleep(sleep);

        //revert back to original position facing the corner
        encoderTurn(TURN_SPEED, (90-orientation), 5);


        //determine sign of vector components from the determined quadrant
        if(quadrant[0] == "FRONT") {

            jSign = 1;

            if(quadrant[1] == "RED") {

                orientation += 45;
                iSign = -1;

            } else {

                orientation -= 135;
                iSign = 1;

            }

        } else {

            jSign = -1;

            if(quadrant[1] == "RED") {

                orientation -= 135;
                iSign = -1;

            } else {

                orientation +=45;
                iSign = 1;

            }

        }

        //update vector array based on how far the robot has moved, adding the width of the lander. (11.65 is the distance from
        //the center of the lander to where the robot initially starts). The next operation uses 45,45,90 triangle to find the distance
        //in inches the bot has moved from its starting position. Averages currentPositions of both motors.
        translationVector[0] = (iSign)*(int)(11.65+(((motorLeft.getCurrentPosition()+motorRight.getCurrentPosition())/2)/3.5)/Math.sqrt(2.0));
        translationVector[1] = (jSign)*(int)(11.65+(((motorLeft.getCurrentPosition()+motorRight.getCurrentPosition())/2)/3.5)/Math.sqrt(2.0));

        //Update position array
        currentPosition = addTranslationVector(currentPosition, translationVector);

        //tell the driver what the current position is
        telemetry.addData("Position X", currentPosition[0]);
        telemetry.addData("Position Y", currentPosition[1]);
        telemetry.update();
        sleep(3000);


        //Go to depot depending on what quadrant bot is in. Opposite quadrants have the same moves.
        if((quadrant[0] == "FRONT" && quadrant[1] == "RED" || (quadrant[0] == "BACK" && quadrant[1] == "BLUE"))) {

            //turn, drive, and turn towards depot
            encoderTurn(TURN_SPEED, 45, 5);

            encoderDrive(DRIVE_SPEED, 31, 5); //31 is an estimate
            sleep(500);

            //add drive to translation vector
            translationVector[0] = (int)((5*12)*Math.cos((orientation*Math.PI)/180));
            translationVector[1] = (int)((5*12)*Math.sin((orientation*Math.PI)/180));
            currentPosition = addTranslationVector(currentPosition, translationVector);

            //Turn to face depot
            encoderTurn(TURN_SPEED, -140, 5);
            sleep(100);

            //Drive into depot
            encoderDrive(RUSH_SPEED, (5*12), 6); //5 feet is an estimate
            sleep(100);

            //add drive to translation vector
            translationVector[0] = (int)((5*12)*Math.cos((orientation*Math.PI)/180));
            translationVector[1] = (int)((5*12)*Math.sin((orientation*Math.PI)/180));
            currentPosition = addTranslationVector(currentPosition, translationVector);

        } else if((quadrant[0] == "FRONT" && quadrant[1] == "BLUE") || (quadrant[0] == "BACK" && quadrant[1] == "RED")) {

            //turn, drive, and turn towards depot
            encoderTurn(TURN_SPEED, -45, 5);

            encoderDrive(DRIVE_SPEED, 36, 5); //36 is an estimate
            sleep(500);

            //add drive to translation vector
            translationVector[0] = (int)((5*12)*Math.cos((orientation*Math.PI)/180));
            translationVector[1] = (int)((5*12)*Math.sin((orientation*Math.PI)/180));
            currentPosition = addTranslationVector(currentPosition, translationVector);

            //Turn to face depot
            encoderTurn(TURN_SPEED, -45, 5);
            sleep(500);

            //Drive into depot
            encoderDrive(RUSH_SPEED, (5*12), 6); //5 feet is an estimate
            sleep(500);

            //add drive to translation vector
            translationVector[0] = (int)((5*12)*Math.cos((orientation*Math.PI)/180));
            translationVector[1] = (int)((5*12)*Math.sin((orientation*Math.PI)/180));
            currentPosition = addTranslationVector(currentPosition, translationVector);

        }

        /*
         *
         * Place object in depot
         *
         */

        //Park in crater (same for all quadrants)
        //Turn to face crater
        encoderTurn(TURN_SPEED, 180, 7);

        //Drive into crater
        encoderDrive(RUSH_SPEED, (10*12), 10);

        //add drive to translation vector
        translationVector[0] = (int)((5*12)*Math.cos((orientation*Math.PI)/180));
        translationVector[1] = (int)((5*12)*Math.sin((orientation*Math.PI)/180));
        currentPosition = addTranslationVector(currentPosition, translationVector);

        //Notify driver that path is complete
        telemetry.addData("Path", "Complete");
        telemetry.addData("Current Orientation:", orientation);
        telemetry.addData("Current Position X", Integer.toString(currentPosition[0]), "Y:", Integer.toString(currentPosition[1]));
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
    public void encoderDrive(double speed, double inches, double timeoutS) {

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
            motorRight.setPower(((Math.abs(inches) / inches) * speed));
            motorLeft.setPower(((Math.abs(inches) / inches) * speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.*/
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (!leftStop || !rightStop)) {

                //Stop right motor if it's finished.
                if (motorRight.getCurrentPosition() >= newRightTarget) {

                    motorRight.setPower(0);
                    rightStop = true;

                }

                //Stop left motor if it's finished.
                if ((motorLeft.getCurrentPosition()) >= newLeftTarget) {

                    motorLeft.setPower(0);
                    leftStop = true;

                    }

                // Display it for the driver.
                telemetry.addData("Path1 Right, Left", "Running to %7d :%7d", ((int) newRightTarget), ((int) newLeftTarget));
                telemetry.addData("Status Right, Left", "Running at %7d :%7d",

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

        }
    }

    public void exactEncoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {

        double newLeftTarget;
        double newRightTarget;

        boolean leftStop = false;
        boolean rightStop = false;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftTarget = motorLeft.getCurrentPosition() + (leftInches * COUNTS_PER_INCH);
            newRightTarget = motorRight.getCurrentPosition() + (rightInches * COUNTS_PER_INCH);

            // reset the timeout time and start motion.
            runtime.reset();
            motorRight.setPower(((Math.abs(rightInches) / rightInches) * speed));
            motorLeft.setPower(((Math.abs(rightInches) / rightInches) * speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.*/
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (!leftStop || !rightStop)) {

                if(leftInches < 0) {

                    //Stop right motor if it's finished.
                    if (motorRight.getCurrentPosition() <= newRightTarget) {

                        motorRight.setPower(0);
                        rightStop = true;

                    }

                    //Stop left motor if it's finished.
                    if (motorLeft.getCurrentPosition() <= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                } else {

                    //Stop right motor if it's finished.
                    if (Math.abs(motorRight.getCurrentPosition()) >= newRightTarget) {

                        motorRight.setPower(0);
                        rightStop = true;

                    }

                    //Stop left motor if it's finished.
                    if ((Math.abs(motorLeft.getCurrentPosition())) >= newLeftTarget) {

                        motorLeft.setPower(0);
                        leftStop = true;

                    }

                }

                // Display robot position and target position information for the driver during the turn.
                telemetry.addData("Path1 Right, Left", "Running to %7d :%7d", ((int) newRightTarget), ((int) newLeftTarget));
                telemetry.addData("Status Right, Left", "Running at %7d :%7d",

                        motorRight.getCurrentPosition(),
                        (Math.abs((motorLeft.getCurrentPosition()))));

                telemetry.addData("Motor powers:", ((Math.abs(rightInches) / rightInches) * speed));

                telemetry.addData("Mode Right", motorRight.getMode());
                telemetry.addData("Mode Left", motorLeft.getMode());
                telemetry.addData("Motor Right", motorRight.isBusy());
                telemetry.addData("Motor Left", motorLeft.isBusy());
                telemetry.update();
            }

            // Stop all motion;
            motorRight.setPower(0);
            motorLeft.setPower(0);

            //Display the post-move encoder positions for the driver
            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();

        }
    }

    /*
    *
    * Method to turn to a certain angle. Can be used with both positive
    * and negative angles.
    *
     */
    public void encoderTurn(double speed, double angle, double timeoutS) {

        double newLeftTarget;
        double newRightTarget;

        boolean leftStop = false;
        boolean rightStop = false;

        double angleToInches = ((((angle) * (Math.PI) / 180.0) * (5.66) * turnErrorConstant)); // Needs to be calibrated (angle -> radians * radius * constant.

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftTarget = (motorLeft.getCurrentPosition() - (angleToInches * COUNTS_PER_INCH));
            newRightTarget = (motorRight.getCurrentPosition()) + (angleToInches * COUNTS_PER_INCH);


            // reset the timeout time and start motion.
            runtime.reset();

            if (angle > 0) {

                motorRight.setPower(Math.abs(0.5));
                motorLeft.setPower((-0.5));

            } else {

                motorRight.setPower((-0.5));
                motorLeft.setPower(Math.abs(0.5));

            }

            // Keep looping while we are still active, and there is time left, and both motors are running.
            // We're using (isBusy() || isBusy()) in the loop test so the bot stops only after both motors
            // have reached their targets.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (!leftStop || !rightStop)) {

                if (angle > 0) {

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

                // Display robot position and target position information for the driver during the turn.
                telemetry.addData("Target Right, Left", "Running to %7d :%7d", ((int) newRightTarget), ((int) newLeftTarget));
                telemetry.addData("Status Right, Left", "Running at %7d :%7d",
                        motorRight.getCurrentPosition(), (Math.abs((motorLeft.getCurrentPosition()))));

                telemetry.addData("Mode Right", motorRight.getMode());
                telemetry.addData("Mode Left", motorLeft.getMode());
                telemetry.addData("Motor Right", motorRight.isBusy());
                telemetry.addData("Motor Left", motorLeft.isBusy());
                telemetry.update();

            }

            //update orientation variable
            orientation += angle;

            // Stop all motion;
            motorRight.setPower(0);
            motorLeft.setPower(0);

            sleep(500);

            //Display post-turn encoder position for the driver
            telemetry.addData("Final position Left: ", motorLeft.getCurrentPosition());
            telemetry.addData("Final position Right: ", motorRight.getCurrentPosition());
            telemetry.update();

        }
    }

    /*
     *Detect a visible Vuforia VuMark
     */
    private String visibleVumark(List<VuforiaTrackable> allTrackables) {

        // check all the trackable target to see which one (if any) is visible.
        targetVisible = false;
        for (VuforiaTrackable trackable : allTrackables) {

            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {

                telemetry.addData("Visible Target", trackable.getName());
                targetVisible = true;

                //return target
                return trackable.getName();
            }
        }

        return "none";

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

    //Method to add a translation vector to update current position
    public int[] addTranslationVector(int[] position, int[] vector) {

        position[0] += vector[0];
        position[1] += vector[1];

        return position;

    }

}