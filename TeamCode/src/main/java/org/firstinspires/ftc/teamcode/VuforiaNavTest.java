package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="VuforiaNavTest", group="Autonomous")
public class VuforiaNavTest extends LcVuforiaOpMode {
    public void runTasks() {
        if (searchForTrackables().size() > 0) {
            telemetry.addData("Current position", getLastPosition());
            telemetry.addData("Current orientation", getLastOrientation());
        } else {
            telemetry.addLine("No targets found.");
        }
        telemetry.update();
        sleep(2000);
        robot.drive(1, 10, 10);
    }
}
