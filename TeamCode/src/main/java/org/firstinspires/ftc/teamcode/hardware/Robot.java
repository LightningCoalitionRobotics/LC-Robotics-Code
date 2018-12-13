package org.firstinspires.ftc.teamcode.hardware;

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
     *
     */
    protected ElapsedTime elapsedTime = new ElapsedTime();
}
