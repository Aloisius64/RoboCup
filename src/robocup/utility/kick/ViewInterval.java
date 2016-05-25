package robocup.utility.kick;

public class ViewInterval {

	private ViewBound start;
	private ViewBound end;

	public ViewInterval(ViewBound start, ViewBound end) {
		this.start = start;
		this.end = end;
	}

	public ViewBound getStart() {
		return start;
	}

	public ViewBound getEnd() {
		return end;
	}

	public double getSize() {
		return end.getAngle() - start.getAngle();
	}

	public double getMidAngle() {
		return (start.getAngle() + end.getAngle()) / 2;
	}

	@Override
	public String toString() {
		return "[" + start.getAngle() + ", " + end.getAngle() + "]";
	}
}
