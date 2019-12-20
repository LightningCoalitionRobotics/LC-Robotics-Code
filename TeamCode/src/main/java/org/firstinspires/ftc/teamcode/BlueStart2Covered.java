package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@Autonomous(name="BlueStart2Covered", group = "autonomous")
public class BlueStart2Covered extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark";

    OpenGLMatrix lastLocation = null;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;

    private HardwareLilPanini robot = new HardwareLilPanini(this);

    @Override
    public void runOpMode() {
        //Setting up Vuforia to find Skystone
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZLuN0P/////AAABmXgtUaFkU0DIk56Jx+XytyKDs9/Ax2YiCDZkM76i5kOmHu1gIDWVNX4LS1fRmfnreaxfK3keo5gWF+Ot6tSDVDLTy3vCR3skPLoZmR+ZiOhiXHSucmBiO2GPPHfSvuhDK+1l4Mqc+ogxle0SlPJz3DhZz47DT07XBSvJaXFBDd/tHeIodQVb0ysOi0yRbUNQ7RkxftKt3lRCq/5JwS28/TiNTpE33psKj3rusF5LLyFB0XviowHrPdLObQEhuCbY0LUljVagijOJ6jvYciIZhRBK65fjDqFCsVkd9+d2waFMhC1JdZD2VnuCkblfdnWpd+EOkrzqCtCDf6bHSEdnzSnzc5jXuzFhNcjyMmdIY9S+";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);



        robot.init(hardwareMap);
        waitForStart();

        robot.drive(0.5, 29, 50);
        int frontRightTarget = robot.motorFrontRight.getCurrentPosition();
        int frontLeftTarget = robot.motorFrontLeft.getCurrentPosition();
        int backLeftTarget = robot.motorBackLeft.getCurrentPosition();
        int backRightTarget = robot.motorBackRight.getCurrentPosition();
        //finding number of counts before while loop

        while (!skyblockDetected) {
            robot.motorFrontRight.setPower(-0.5);
            robot.motorFrontLeft.setPower(0.5);
            robot.motorBackLeft.setPower(-0.5);
            robot.motorBackRight.setPower(0.5);
        }
        //code to pick up block goes here

        while (robot.motorFrontRight.getCurrentPosition() >= frontRightTarget || robot.motorFrontLeft.getCurrentPosition() <= frontLeftTarget || robot.motorBackLeft.getCurrentPosition() >= backLeftTarget || robot.motorBackRight.getCurrentPosition() <= backRightTarget) {
            robot.motorFrontRight.setPower(0.5);
            robot.motorFrontLeft.setPower(-0.5);
            robot.motorBackLeft.setPower(0.5);
            robot.motorBackRight.setPower(-0.5);
        }
        robot.drive(-0.7, 30, 50);
        robot.turn(0.6, 90, 50);
        robot.drive(0.7, 51, 50);

        //code to put skyblock onto foundation goes here
    }

}