package org.firstinspires.ftc.teamcode;

/**
 * A series of useful constants relating to distances on the course.
 *
 * Please feel free to add more, but remember to document what it measures!
 * @author Noah Simon
 */
public final class SkystoneCourseConstants implements CourseConstants {
    /**
     * The distance in inches from one side of the red beams to the other along the horizontal axis
     */
    public static final int RED_CLEARANCE_X = 47;

    /**
     * The distance in inches from one side of the yellow beams to the other along the horizontal axis
     */
    public static final int YELLOW_CLEARANCE_X = 44;

    /**
     * The distance in inches from the ground to the red beams
     */
    public static final double RED_CLEARANCE_Y = 14.5;

    /**
     * The distance in inches from the ground to the yellow beams
     */
    public static final double YELLOW_CLEARANCE_Y = 20.5;

    /**
     * The distance in inches of one block
     */
    public static final double ONE_BLOCK_ACROSS = 23;

    /**
     * The distance in inches of one diagonal of the square
     */
    public static final double ONE_BLOCK_DIAGONAL = 33;

    // Begin constants from org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaSkyStoneNavigation
    // 25.4 is number of mm per in
    public static final float STONE_Z_MM = 2 * 25.4f;
    public static final float BRIDGE_Z_MM = 6.42f * 25.4f;
    public static final float BRIDGE_Y_MM = 23 * 25.4f;
    public static final float BRIDGE_X_MM = 5.18f * 25.4f;
    public static final float BRIDGE_ROT_Y = 59;
    public static final float BRIDGE_ROT_Z = 180;
    public static final float HALF_FIELD_MM = 72 * 25.4f;
    public static final float QUAD_FIELD_MM = HALF_FIELD_MM / 2;
    public static final float TARGET_HEIGHT_MM = 6 * 25.4f;
    // End constants from org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaSkyStoneNavigation
}
