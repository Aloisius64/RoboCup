package team.playerSeenData;

public class Ball {

    private final double distance;
    private final double direction;
    private final double distChange;
    private final double dirChange;
    private final double bodyFacingDirection;
    private final double headFacingDirection;

    public Ball(double distance, double direction, double distChange, double dirChange, double bodyFacingDirection, double headFacingDirection) {
        this.distance = distance;
        this.direction = direction;
        this.distChange = distChange;
        this.dirChange = dirChange;
        this.bodyFacingDirection = bodyFacingDirection;
        this.headFacingDirection = headFacingDirection;
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
