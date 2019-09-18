package org.firstinspires.ftc.teamcode.code201819;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public class VuforiaPositioner {
    private VuforiaLocalizer vuforia;
    public VuforiaPositioner(VuforiaLocalizer vuforia) {
        this.vuforia = vuforia;
    }

//    public Position findLocation() {
//
//    }

    public class Position {
        public double x;
        public double y;

        public Position(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
