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
		addPrecondition(GoapGlossary.BALL_NEAR, true);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, false);
		// addPrecondition(GoapGlossary.PLAYER_MARKED, false);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE_ATTACKER, false);
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

		AbstractPlayer player = (AbstractPlayer) agent;

		if (player.getMemory().isObjVisible("ball")) {
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
		System.out.println(player.getMemory().getuNum()+" Performing "+getClass().getSimpleName());
		if (player.getAction().isBallNearTeammate())
			return false;
		try {

			if (player.getMemory().isObjVisible("ball")) {
				ObjBall ball = player.getMemory().getBall();

				if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
					player.getRoboClient().turn(ball.getDirection());
				}

				if (ball.getDistance() > 0.7 && player.getAction().isBallInOurField().booleanValue()) {
					// player.getAction().interceptBall(ball);

					Polar p = MathHelp.getNextBallPoint(ball);
					Position p2 = MathHelp.getPos(p);
					if ((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36))
						return false;
					else if (player.getAction().stayInBounds()) {
						player.getAction().goToPoint(p);
					}
					return true;

				} else if (ball.getDistance() <= 0.7) {
					ballStoled = true;
				}

				if (!player.getAction().isBallInOurField().booleanValue()) {
					return false;
				}

				return true;

			} else {
				player.getRoboClient().turn(30);
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
