package robocup.utility.kick;

import java.util.ArrayList;
import java.util.List;

public class GoalView {

	private List<ViewInterval> intervals;

	public GoalView(double leftFlagDirection, double rightFlagDirection) {
		this.intervals = new ArrayList<>();

		ViewBound start = new ViewBound(leftFlagDirection, BoundType.POST);
		ViewBound end = new ViewBound(rightFlagDirection, BoundType.POST);

		ViewInterval interval = new ViewInterval(start, end);

		intervals.add(interval);
	}

	private GoalView(List<ViewInterval> intervals) {
		this.intervals = intervals;
	}

	public GoalView subtractInterval(ViewInterval opponentInterval) {
		List<ViewInterval> resultIntervals = new ArrayList<ViewInterval>();

		double opponentStart = opponentInterval.getStart().getAngle();
		double opponentEnd = opponentInterval.getEnd().getAngle();

		for (ViewInterval interval : intervals) {
			double intervalStart = interval.getStart().getAngle();
			double intervalEnd = interval.getEnd().getAngle();

			if (opponentStart > intervalStart) {
				if (opponentStart > intervalEnd) {
					resultIntervals.add(interval);
				} else {
					resultIntervals.add(new ViewInterval(interval.getStart(), opponentInterval.getStart()));
				}
			}

			if (opponentEnd < intervalEnd) {
				if (opponentEnd < intervalStart) {
					resultIntervals.add(interval);
				} else {
					resultIntervals.add(new ViewInterval(opponentInterval.getEnd(), interval.getEnd()));
				}
			}
		}

		return new GoalView(resultIntervals);
	}
	
	public ViewInterval getLargerInterval() {
		ViewInterval largerInterval = null;
		
		for (ViewInterval interval : intervals) {
			if (largerInterval == null) {
				largerInterval = interval;
			} else {
				if (interval.getSize() > largerInterval.getSize()) {
					largerInterval = interval;
				}
			}
		}
		
		return largerInterval;
	}
	
	@Override
	public String toString() {
		return intervals.toString();
	}
}
