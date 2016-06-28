package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

/*
SHOOT_BALL_GOALIE (Rinvio verso la met� campo) ***
	PRECONDITIONS:
		KEEP_AREA_SAFE (FALSE)
		BALL_CATCHED (TRUE)

	EFFECTS:
		KEEP_AREA_SAFE (TRUE)

	THINGS TO CHECK:
		Presenza di un certo numero di avversari
		vicino la porta. Se tale numero � troppo
		elevato (maggiore dei propri difensori)

	PERFORMING:
		Il portiere calcia la palla verso la met� 
		campo (possibilmente verso un proprio 
		compagno)
 */
public class ShootBallGoalieAction extends GoapAction {

	private boolean ballShooted = false;		
	private Position pointToShoot = new Position(0, 0);

	public ShootBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
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
		//		Presenza di un certo numero di avversari
		//		vicino la porta. Se tale numero � troppo
		//		elevato (maggiore dei propri difensori)
		
		AbstractPlayer player = (AbstractPlayer) agent;
		String teamName = player.getRoboClient().getTeam();

		int teammates = player.getMemory().getTeammates(teamName).size();
		int opponents = player.getMemory().getOpponents(teamName).size();

		//		System.out.println("Teammates "+teammates+", Opponents: "+opponents);

		return player.getAction().isBallNearTeammateAttacker() || teammates < opponents;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il portiere calcia la palla verso la met� 
		//		campo (possibilmente verso un proprio 
		//		compagno)

//		System.out.println("Performing "+getClass().getSimpleName());

		AbstractPlayer player = (AbstractPlayer) agent;
		if(!player.getAction().isPlayMode("play_on"))
			return false;
		if(player.getMemory().isObjVisible("ball") && player.getAction().isBallInRangeOf(1.0)){
			ObjBall ball = player.getMemory().getBall();
			player.getAction().kickToPoint(ball, pointToShoot);
			ballShooted = true;
			return true;
		}

		return false;
	}

}
