package robocup.sensors;

public class Ball {

    private  double distance;
    private  double direction;
    private  double distChange;
    private  double dirChange;
    private  double bodyFacingDirection;
    private  double headFacingDirection;
    private boolean seenInLastStep;

    public Ball(double distance, double direction, double distChange, double dirChange, double bodyFacingDirection, double headFacingDirection) {
        this.distance = distance;
        this.direction = direction;
        this.distChange = distChange;
        this.dirChange = dirChange;
        this.bodyFacingDirection = bodyFacingDirection;
        this.headFacingDirection = headFacingDirection;
        this.seenInLastStep=true;
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

	public boolean isSeenInLastStep() {
		return seenInLastStep;
	}

	public void setSeenInLastStep(boolean seenInLastStep) {
		this.seenInLastStep = seenInLastStep;
	}
}
