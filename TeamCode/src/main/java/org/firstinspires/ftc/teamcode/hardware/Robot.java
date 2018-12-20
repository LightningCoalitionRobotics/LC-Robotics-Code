package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This interface should be implemented for any robot created. It provides a basic template for what
 * a hardware class should have.
 * @author Noah Simon
 */
public abstract class Robot {
    /**
     * Before using the robot, always call this function with the argument <b>hardwareMap</b>.
     * (It's already defined by a superclass.)
     *
     * This function configures the robot to assign specific hardware elements to fields of this class.
     * @param hardwareMap The hardware map defined by a superclass containing all of the hardware elements.
     */
    public abstract void init(HardwareMap hardwareMap);

    /**
     * Use the drive motors to move the robot in a straight line.
     *
     * @param speed A value from -1 to 1, a higher absolute value meaning a higher speed.
     * @param dist In inches, the distance the robot should travel.
     * @param timeout In seconds, how long the robot should attempt to reach the target distance.
     */
    public abstract void drive(double speed, double dist, double timeout);

    /**
     * Use the drive motors to turn the robot in place.
     *
     * @param speed A value from -1 to 1, a higher absolute value meaning a higher speed.
     * @param angle In degrees, how far the robot should turn. A positive amount is counterclockwise.
     * @param timeout In seconds, how long the robot should attempt to reach the target angle.
     */
    public abstract void turn(double speed, double angle, double timeout);

    /**
     * Move a single motor for a specific amount of time.
     * @param motor The motor that will be in use.
     * @param speed A value from -1 to 1, a higher absolute value meaning a higher speed.
     * @param time In milliseconds, how long the motor should turn for.
     */
    public void moveMotor(DcMotor motor, double speed, long time) {
        if (opMode instanceof LinearOpMode) {
            motor.setPower(speed);
            ((LinearOpMode) opMode).sleep(time);
            motor.setPower(0);
        } else {
            throw new UnsupportedOperationException("Tried to run automatically during teleop.");
        }
//        motor.setPower(speed);
//        if (opMode instanceof LinearOpMode) {
//            ((LinearOpMode) opMode).sleep(time);
//        } else {
//            double startTime = opMode.getRuntime();
//            while (opMode.getRuntime() - startTime > 0) {
//            }
//        }
//        motor.setPower(0);
    }

    /**
     * An object used to determine the amount of time elapsed since this class was called.
     */
    protected ElapsedTime elapsedTime = new ElapsedTime();

    /**
     * Factors by which motors should multiply their speed, usually during teleop.
     */
    public enum SlowModeType {
        /**
         * A factor of 2.
         */
        DOUBLE(2),
        /**
         * A factor of 1, or normal speed.
         */
        NORMAL(1),
        /**
         * A factor of 0.5.
         */
        HALF(1/2),
        /**
         * A factor of 0.25.
         */
        QUARTER(1/4),
        /**
         * A factor of 0.125.
         */
        EIGHTH(1/8);

        /**
         * Create a new value in this enum.
         * @param changeFactor The factor by which the instance should change motor speed.
         */
        SlowModeType(float changeFactor) {
            this.changeFactor = changeFactor;
        }

        /**
         * Get the factor by which the instance should change motor speed.
         * @return the {@link #changeFactor} of the instance.
         */
        public float getChangeFactor() {
            return changeFactor;
        }

        /**
         * The factor by which the instance should change motor speed.
         */
        private float changeFactor;

        @Override
        public String toString() {
            switch (this) {
                case DOUBLE:
                    return "Double speed";
                case NORMAL:
                    return "Normal Speed";
                case HALF:
                    return "Half Speed";
                case QUARTER:
                    return "Quarter Speed";
                case EIGHTH:
                    return "Eighth Speed";
                default:
                    throw new IllegalArgumentException();
            }
        }

        /**
         * Double the change factor of the caller.
         * @return The throttle level above the caller.
         */
        public SlowModeType gearUp() {
            switch (this) {
                case NORMAL:
                    return DOUBLE;
                case HALF:
                    return NORMAL;
                case QUARTER:
                    return HALF;
                case EIGHTH:
                    return QUARTER;
                default:
                    return this;
            }
        }

        /**
         * Half the change factor of the caller.
         * @return The gear level below the caller.
         */
        public SlowModeType gearDown() {
            switch (this) {
                case QUARTER:
                    return EIGHTH;
                case HALF:
                    return QUARTER;
                case NORMAL:
                    return HALF;
                case DOUBLE:
                    return NORMAL;
                default:
                    return this;
            }
        }
    }

    /**
     * The registry of motors and other hardware elements on the robot.
     */
    public HardwareMap hardwareMap;
    /**
     * The class instantiating this instance.
     */
    protected OpMode opMode;
}
