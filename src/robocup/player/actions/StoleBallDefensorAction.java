package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Position;

/*
DEFENSOR_STOLE_BALL ****************************************
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHED (FALSE)
	BALL_NEAR (TRUE)
	PLAYER_MARKED (FALSE) // Forse non serve per questa azione

EFFECTS:
	BALL_CATCHED (TRUE)

THINGS TO CHECK:
	La palla � controllata da un avversario o
	non, basta che sia nella propria met� campo,
	ed � ad una certa distanza dal difensore

PERFORMING:
	Il difensore si avvicina alla palla � cerca
	di recuperarla
 */
public class StoleBallDefensorAction extends GoapAction {

	private boolean ballStoled = false;

	public StoleBallDefensorAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addPrecondition(GoapGlossary.BALL_SEEN, true);
		// addPrecondition(GoapGlossary.BALL_NEAR, true);
		addPrecondition(GoapGlossary.BALL_IN_DEFENSIVE_AREA, true);
		addPrecondition(GoapGlossary.BALL_NEAR_DEFENDER, false);
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

		AbstractPlayer player = (AbstractPlayer) agent;

		if (player.getMemory().getBall() != null) {
			ObjBall ball = player.getMemory().getBall();
			if (ball.getDistance() > 10) {
				// return false;
				return !player.getAction().isBallNearTeammateAttacker();
			}
		}

		return true;
	}

	@Override
	public boolean perform(Object agent) {
		// Il difensore si avvicina alla palla � cerca
		// di recuperarla

		AbstractPlayer player = (AbstractPlayer) agent;
		// System.out.println(player.getMemory().getuNum() + " Performing " +
		// getClass().getSimpleName());

		try {
			if (player.getMemory().isObjVisible("ball")) {

				ObjBall ball = player.getMemory().getBall();
				if (player.getAction().isBallNearDefender()) {
					return false;
				}
				if (!player.getAction().isBallInDefensiveArea())
					return false;
				if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
					player.getRoboClient().turn(ball.getDirection());
				}

				if (ball.getDistance() > 0.7) {
					if (player.getAction().isBallNearTeammateAttacker() && ball.getDistance() > 10) {
						return false;
					}
					player.getAction().dash(100, ball.getDirection());
				} else if (ball.getDistance() <= 0.7) {
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
