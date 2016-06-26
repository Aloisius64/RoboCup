package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

/*
PASS_BALL_GOALIE **********************************
	PRECONDITIONS:
		KEEP_AREA_SAFE (FALSE)
		BALL_CATCHED (TRUE)

	EFFECTS:
		KEEP_AREA_SAFE (TRUE)

	THINGS TO CHECK:
		Presenza di un certo numero di avversari
		vicino la porta. Se tale numero non � 
		troppo elevato (minore o circa i propri
		difensori)

	PERFORMING:
		Il portiere passa la palla al difensore
		pi� libero
 */
public class PassBallGoalieAction extends GoapAction {

	private boolean ballPassed = false;
	private Position pointToShoot = new Position(0, 0);

	public PassBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KICK_OFF, false);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
	}

	@Override
	public void reset() {
		ballPassed = false;
	}

	@Override
	public boolean isDone() {
		return ballPassed;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		Presenza di un certo numero di avversari
		//		vicino la porta. Se tale numero non � 
		//		troppo elevato (minore o circa i propri
		//		difensori)
		
		AbstractPlayer player = (AbstractPlayer) agent;
		String teamName = player.getRoboClient().getTeam();
		int teammates = player.getMemory().getTeammates(teamName).size();
		int opponents = player.getMemory().getOpponents(teamName).size();

		//		System.out.println("Teammates "+teammates+", Opponents: "+opponents);

		return teammates >= opponents;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il portiere passa la palla al difensore
		//		pi� libero

		System.out.println("Performing "+getClass().getSimpleName());

		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			if(player.getMemory().isObjVisible("ball") && player.getAction().isBallInRangeOf(1.0)){
				ObjBall ball = player.getMemory().getBall();
				ObjPlayer closestTeammate;
				closestTeammate = player.getAction().closestTeammate();
				if(closestTeammate!=null){
					player.getAction().kickToPlayer(closestTeammate);
				} else {
					player.getAction().kickToPoint(ball, pointToShoot);
				}
				ballPassed = true;
				return true;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
