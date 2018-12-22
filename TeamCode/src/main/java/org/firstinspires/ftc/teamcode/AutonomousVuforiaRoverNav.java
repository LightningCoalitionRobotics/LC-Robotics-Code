package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareBobAlexanderIII;


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

import java.util.ArrayList;
import java.util.List;


/**
 * An autonomous program to test the features in {@link org.firstinspires.ftc.teamcode.hardware.HardwareBobAlexanderIII}.
 * @author Noah Simon
 */
@Autonomous(name="AutonomousVuforiaRoverNav", group="autonomous")
public class AutonomousVuforiaRoverNav extends LinearOpMode {

int DesiredXLocation = 5;
int DesiredYLocation = 6;

    @Override
    public void runOpMode() {
        for (DesiredXLocation /= translation.get(0) / mmPerInch, DesiredXLocation - translation.get(0) / mmPerInch = 4) {
            for (rotation.ThirdAngle /=0, rotation.thirdAngle /= 0){
                robot.turn(1, 1);
                telemetry.update();
            }

            robot.drive(3,1);
            telemetry.update();

        }

        robot.turn(90, 3);
        telemetry.update();

        for (DesiredYLocation /= translation.get(1) / mmPerInch, DesiredYLocation - translation.get(1) / mmPerInch = 4) {
            for (rotation.ThirdAngle /=0, rotation.thirdAngle /= 0){
                robot.turn(1, 1);
                telemetry.update();
            }

            robot.drive(3,1);
            telemetry.update();

        }
    }
}
