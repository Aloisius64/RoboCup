package robocup.utility.kick;

public class ViewBound {

	private double angle;
	private BoundType boundType;
	
	public ViewBound(double angle, BoundType boundType) {
		this.angle = angle;
		this.boundType = boundType;
	}

	public double getAngle() {
		return angle;
	}
	
	public BoundType getBoundType() {
		return boundType;
	}
}
