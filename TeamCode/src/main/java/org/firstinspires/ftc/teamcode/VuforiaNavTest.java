package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="VuforiaNavTest", group="Autonomous")
public class VuforiaNavTest extends LcVuforiaOpMode {
    public void runTasks() {
        sleep(1000);
        if (searchForTrackables().size() > 0) {
            telemetry.addData("I can see", searchForTrackables().toArray());
            telemetry.addData("Current position", getLastPosition());
            telemetry.addData("Current orientation", getLastOrientation());
        } else {
            telemetry.addLine("No targets found.");
        }
        telemetry.update();
        sleep(2000);
        robot.drive(1, 10, 10);
        robot.turn(0.5, 60, 10);
    }
}
