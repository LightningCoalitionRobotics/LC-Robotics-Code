package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareLilPanini;

@TeleOp(name="AlexsTeleOp", group="TeleOp")
public class AlexsTeleOp extends OpMode {
    private HardwareLilPanini robot = new HardwareLilPanini(this);

    public void init() {
        robot.init(hardwareMap);
    }
    public boolean placeholderTrigger = true;
    public void loop() {
        //movement gamepad: triggers for forward/backward, b/x for strafing left and right, right joystick for moving tangent, left joystick for turning, dpad for changing speed
        //arm gamepad: y/a to move arm to max/min height, b/x to open and close claw, dpad for more precise height changes
        if(gamepad1.left_trigger > 0.49) {
            //move forward
        }
    }
}