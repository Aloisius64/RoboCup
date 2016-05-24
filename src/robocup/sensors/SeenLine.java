/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.sensors;

import com.github.robocup_atan.atan.model.enums.Line;

/**
 *
 * @author aloisius
 */
public class SeenLine {
    private final Line line;
    private final double distance;
    private final double direction;
    private final double distChange;
    private final double dirChange;
    private final double bodyFacingDirection;
    private final double headFacingDirection;

    public SeenLine(Line line, double distance, double direction, double distChange, double dirChange, double bodyFacingDirection, double headFacingDirection) {
        this.line = line;
        this.distance = distance;
        this.direction = direction;
        this.distChange = distChange;
        this.dirChange = dirChange;
        this.bodyFacingDirection = bodyFacingDirection;
        this.headFacingDirection = headFacingDirection;
    }

    public Line getLine() {
        return line;
    }

    public double getDistance() {
        return distance;
    }

    public double getDirection() {
        return direction;
    }

    public double getDistChange() {
        return distChange;
    }

    public double getDirChange() {
        return dirChange;
    }

    public double getBodyFacingDirection() {
        return bodyFacingDirection;
    }

    public double getHeadFacingDirection() {
        return headFacingDirection;
    }
    
}
