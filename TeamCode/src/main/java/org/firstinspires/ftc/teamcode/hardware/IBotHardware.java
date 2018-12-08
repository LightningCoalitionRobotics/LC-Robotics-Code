package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This interface should be implemented for any robot created. It provides a basic template for what
 * a hardware class should have.
 * @author Noah Simon
 */
public interface IBotHardware {
    /**
     * Before using the robot, always call this function with the argument <b>hardwareMap</b>.
     * (It's already defined by a superclass.)
     *
     * This function configures the robot to assign specific hardware elements to fields of this class.
     */
    public void init(HardwareMap hardwareMap);
}
