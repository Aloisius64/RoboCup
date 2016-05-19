/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.playerSeenData;

/**
 *
 * @author aloisius
 */
public class SeenPlayer {

    private final int number;
    private final boolean goalie;
    private final double distance;
    private final double direction;
    private final double distChange;
    private final double dirChange;
    private final double bodyFacingDirection;
    private final double headFacingDirection;

    public SeenPlayer(int number, boolean goalie, double distance, double direction, double distChange, double dirChange, double bodyFacingDirection, double headFacingDirection) {
        this.number = number;
        this.goalie = goalie;
        this.distance = distance;
        this.direction = direction;
        this.distChange = distChange;
        this.dirChange = dirChange;
        this.bodyFacingDirection = bodyFacingDirection;
        this.headFacingDirection = headFacingDirection;
    }

    public int getNumber() {
        return number;
    }

    public boolean isGoalie() {
        return goalie;
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
