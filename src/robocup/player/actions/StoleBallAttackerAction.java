package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;

/*
 */
public class StoleBallAttackerAction extends GoapAction {

	private boolean ballStoled = false;

	public StoleBallAttackerAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_SEEN, true);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, false);
		addEffect(GoapGlossary.BALL_CATCHED, true);
	}

	@Override
	public void reset() {
		ballStoled = false;
	}

	@Override
	public boolean isDone() {
		return ballStoled;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		// La palla � controllata da un avversario o
		// non, basta che sia nella propria met� campo,
		// ed � ad una certa distanza dal difensore
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		// Il difensore si avvicina alla palla � cerca
		// di recuperarla

		AbstractPlayer player = (AbstractPlayer) agent;
		if(!player.getAction().isPlayMode("play_on"))
			return false;
		// System.out.println("Performing " + getClass().getSimpleName());

		try {
			if (player.getMemory().getBall() != null) {

				ObjBall ball = player.getMemory().getBall();
				if (player.getAction().isBallNearTeammate())
					return false;
				if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
					player.getRoboClient().turn(ball.getDirection());
				}

				if (ball.getDistance() > 0.7) {

					player.getAction().dash(100, ball.getDirection());
					return true;

				} else if (ball.getDistance() <= 0.7) {
					String teamName = player.getRoboClient().getTeam();
					int opponents = player.getMemory().getNearestOpponents(teamName, 3);
					if (opponents > 1)
						return false;
					ballStoled = true;
				}

				return true;

			} else {
				return false;
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
