package robocup.utility.kick;

import java.util.List;

import robocup.objInfo.ObjPlayer;

public class KickMathUtility {

	public static GoalView getGoalView(double leftFlagDirection, double rightFlagDirection, List<ObjPlayer> otherPlayers,
			double playerSize) {
		GoalView goalView = new GoalView(leftFlagDirection, rightFlagDirection);
		System.out.println("goalView " + goalView);
		
		for (ObjPlayer seenPlayer : otherPlayers) {
			ViewInterval opponentInterval = getPlayerInterval(seenPlayer, playerSize);
			System.out.println("opponent interval " + opponentInterval);
			goalView = goalView.subtractInterval(opponentInterval);
		}

		return goalView;
	}

	private static ViewInterval getPlayerInterval(ObjPlayer seenPlayer, double playerSize) {
		double distance = seenPlayer.getDistance();
		double theta = seenPlayer.getDirection();

		double playerX = distance * cos(theta);
		double playerY = distance * sin(theta);

		// Considero la playerSize come raggio.

		double delta = sqr(playerX) * sqr(playerSize) + sqr(playerY) * sqr(playerSize)
				- sqr(playerSize) * sqr(playerSize);
		double m1 = (-playerX * playerY - sqrt(delta)) / (sqr(playerSize) - sqr(playerX));
		double m2 = (-playerX * playerY + sqrt(delta)) / (sqr(playerSize) - sqr(playerX));

		if (m1 > m2) {
			double tmp = m1;
			m1 = m2;
			m2 = tmp;
		}
		
		double angle1 = atan(m1);
		double angle2 = atan(m2);

		BoundType boundType = BoundType.PLAYER;

		if (seenPlayer.isGoalie()) {
			boundType = BoundType.GOALIE;
		}

		return new ViewInterval(new ViewBound(angle1, boundType), new ViewBound(angle2, boundType));
	}

	private static double sin(double angle) {
		double angleRadians = Math.toRadians(angle);
		return Math.sin(angleRadians);
	}

	private static double cos(double angle) {
		double angleRadians = Math.toRadians(angle);
		return Math.cos(angleRadians);
	}

	private static double atan(double tan) {
		double angle = Math.atan(tan);
		return Math.toDegrees(angle);
	}

	private static double sqrt(double value) {
		return Math.sqrt(value);
	}

	private static double sqr(double value) {
		return value * value;
	}
}
