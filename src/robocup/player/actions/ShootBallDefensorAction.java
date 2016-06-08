package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

/*
SHOOT_BALL (Passaggio lungo oltre la met� campo) **
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHED (TRUE)

EFFECTS:
	KEEP_AREA_SAFE (TRUE)
	BALL_CATCHED (FALSE)

THINGS TO CHECK:
	Si � visto un attaccante particolarmente 
	libero oltre la met� campo

PERFORMING:
	La palla � calciata oltre la met� campo 
	verso un proprio compagno
 */
public class ShootBallDefensorAction extends GoapAction {

	private boolean ballShooted = false;

	public ShootBallDefensorAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE_ATTACKER, false);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
		addEffect(GoapGlossary.BALL_CATCHED, false);
	}

	@Override
	public void reset() {
		ballShooted = false;
	}

	@Override
	public boolean isDone() {
		return ballShooted;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		Si � visto un attaccante particolarmente 
		//		libero oltre la met� campo
		
		AbstractPlayer player = (AbstractPlayer) agent;
		String teamName = player.getRoboClient().getTeam();

		int teammates = player.getMemory().getTeammates(teamName).size();
		int opponents = player.getMemory().getOpponents(teamName).size();

		//		System.out.println("Teammates "+teammates+", Opponents: "+opponents);

		return player.getAction().isBallNearTeammateAttacker() || teammates < opponents;
	}

	@Override
	public boolean perform(Object agent) {
		//		La palla � calciata oltre la met� campo 
		//		verso un proprio compagno
		System.out.println("Performing "+getClass().getSimpleName());
		
		AbstractPlayer player = (AbstractPlayer) agent;
		ballShooted = true;

		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			player.getAction().kickToPoint(ball, new Position(0,0));
			return true;
		}
		
		return false;
	}

}
