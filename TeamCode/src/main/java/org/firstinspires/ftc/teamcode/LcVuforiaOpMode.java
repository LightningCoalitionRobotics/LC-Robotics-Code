package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

import java.util.ArrayList;
import java.util.List;

public abstract class LcVuforiaOpMode extends LinearOpMode {
    private static final String VUFORIA_LICENSE_KEY = "AZLuN0P/////AAABmXgtUaFkU0DIk56Jx+XytyKDs9/Ax2YiCDZkM76i5kOmHu1gIDWVNX4LS1fRmfnreaxfK3keo5gWF+Ot6tSDVDLTy3vCR3skPLoZmR+ZiOhiXHSucmBiO2GPPHfSvuhDK+1l4Mqc+ogxle0SlPJz3DhZz47DT07XBSvJaXFBDd/tHeIodQVb0ysOi0yRbUNQ7RkxftKt3lRCq/5JwS28/TiNTpE33psKj3rusF5LLyFB0XviowHrPdLObQEhuCbY0LUljVagijOJ6jvYciIZhRBK65fjDqFCsVkd9+d2waFMhC1JdZD2VnuCkblfdnWpd+EOkrzqCtCDf6bHSEdnzSnzc5jXuzFhNcjyMmdIY9S+";

    protected HardwareLilPanini robot = new HardwareLilPanini(this);
    protected VuforiaLocalizer vuforia;

    protected VuforiaTrackable stoneTarget;
    protected VuforiaTrackable blueRearBridge;
    protected VuforiaTrackable redRearBridge;
    protected VuforiaTrackable redFrontBridge;
    protected VuforiaTrackable blueFrontBridge;
    protected VuforiaTrackable red1;
    protected VuforiaTrackable red2;
    protected VuforiaTrackable front1;
    protected VuforiaTrackable front2;
    protected VuforiaTrackable blue1;
    protected VuforiaTrackable blue2;
    protected VuforiaTrackable rear1;
    protected VuforiaTrackable rear2;
    protected List<VuforiaTrackable> allTrackables = new ArrayList<>();

    protected OpenGLMatrix lastKnownLocation;

    private VuforiaTrackables targetsSkyStone;

    protected OpenGLMatrix robotFromCamera = OpenGLMatrix.translation(robot.CAMERA_FORWARD_DISPLACEMENT_MM, robot.CAMERA_LEFT_DISPLACEMENT_MM, robot.CAMERA_VERTICAL_DISPLACEMENT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZX, AngleUnit.DEGREES, -90, 0, 90));

    private void vuforiaInit() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        targetsSkyStone = vuforia.loadTrackablesFromAsset("Skystone");

        stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        stoneTarget.setLocation(OpenGLMatrix.translation(0, 0, SkystoneCourseConstants.STONE_Z_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, -90)));

        blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        blueRearBridge.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.BRIDGE_X_MM, SkystoneCourseConstants.BRIDGE_Y_MM, SkystoneCourseConstants.BRIDGE_Z_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, -SkystoneCourseConstants.BRIDGE_ROT_Y, SkystoneCourseConstants.BRIDGE_ROT_Z)));

        redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        redRearBridge.setLocation(OpenGLMatrix.translation(SkystoneCourseConstants.BRIDGE_X_MM, -SkystoneCourseConstants.BRIDGE_Y_MM, SkystoneCourseConstants.BRIDGE_Z_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, SkystoneCourseConstants.BRIDGE_ROT_Y, 0)));

        redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        redFrontBridge.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.BRIDGE_X_MM, -SkystoneCourseConstants.BRIDGE_Y_MM, SkystoneCourseConstants.BRIDGE_Z_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, -SkystoneCourseConstants.BRIDGE_ROT_Y, 0)));

        blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        blueFrontBridge.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.BRIDGE_X_MM, SkystoneCourseConstants.BRIDGE_Y_MM, SkystoneCourseConstants.BRIDGE_Z_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, SkystoneCourseConstants.BRIDGE_ROT_Y, SkystoneCourseConstants.BRIDGE_ROT_Z)));

        red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        red1.setLocation(OpenGLMatrix.translation(SkystoneCourseConstants.QUAD_FIELD_MM, -SkystoneCourseConstants.HALF_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 180)));

        red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        red2.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.QUAD_FIELD_MM, -SkystoneCourseConstants.HALF_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 180)));

        front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        front1.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.HALF_FIELD_MM, -SkystoneCourseConstants.QUAD_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 90)));

        front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        front2.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.HALF_FIELD_MM, SkystoneCourseConstants.QUAD_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 90)));

        blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        blue1.setLocation(OpenGLMatrix.translation(-SkystoneCourseConstants.QUAD_FIELD_MM, SkystoneCourseConstants.HALF_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 0)));

        blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        blue2.setLocation(OpenGLMatrix.translation(SkystoneCourseConstants.QUAD_FIELD_MM, SkystoneCourseConstants.HALF_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 0)));

        rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        rear1.setLocation(OpenGLMatrix.translation(SkystoneCourseConstants.HALF_FIELD_MM, SkystoneCourseConstants.QUAD_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, -90)));

        rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");
        rear2.setLocation(OpenGLMatrix.translation(SkystoneCourseConstants.HALF_FIELD_MM, -SkystoneCourseConstants.QUAD_FIELD_MM, SkystoneCourseConstants.TARGET_HEIGHT_MM).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, -90)));

        allTrackables.addAll(targetsSkyStone);

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
    }

    @Override
    public void runOpMode() {
        vuforiaInit();
        robot.init(hardwareMap);
        waitForStart();
        targetsSkyStone.activate();

        runTasks();

        // Do vuforia cleanup
        targetsSkyStone.deactivate();
    }

    abstract void runTasks();

    protected boolean isVisible(VuforiaTrackable trackable) {
        if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastKnownLocation = robotLocationTransform;
            }
            return true;
        }
        return false;
    }

    public class Position {
        public float x;
        public float y;
        public float z;

        Position(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ", " + z + ")";
        }
    }

    protected Position getLastPosition() {
        if (lastKnownLocation != null) {
            return new Position(lastKnownLocation.getTranslation().get(0) / 25.4f, lastKnownLocation.getTranslation().get(1) / 25.4f, lastKnownLocation.getTranslation().get(2) / 25.4f);
        }
        return null;
    }

    public class Facing {
        public float roll; // Rotation on robot's y axis, robot's left and right axles at different heights
        public float pitch; // Rotation on robot's x axis, robot sight aimed up/down
        public float heading; // Rotation on robot's z axis, flat direction of robot

        Facing(float roll, float pitch, float heading) {
            this.roll = roll;
            this.pitch = pitch;
            this.heading = heading;
        }

        @Override
        public String toString() {
            return "r=" + roll + ", p=" + pitch + ", h=" + heading;
        }
    }

    protected Facing getLastOrientation() {
        if (lastKnownLocation != null) {
            Orientation rotation = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            return new Facing(rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        }
        return null;
    }

    protected List<VuforiaTrackable> searchForTrackables() {
        List<VuforiaTrackable> trackables = new ArrayList<>();
        for (VuforiaTrackable trackable : allTrackables) {
            if (isVisible(trackable)) {
                trackables.add(trackable);
            }
        }
        return trackables;
    }
}
