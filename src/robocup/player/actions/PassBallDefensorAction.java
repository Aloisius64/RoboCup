package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Position;

/*
PASS_BALL *****************************************
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHED (TRUE)

EFFECTS:
	BALL_CATCHED (FALSE)
	KEEP_AREA_SAFE (TRUE)

THINGS TO CHECK:
	Numero di avversari vicini, compagni liberi

PERFORMING:
	La palla viene passata ad un compagno se il
	giocatore � ostacolato da un certo numero di
	avversari e alcuni compagni sono in zone pi�
	libere rispetto a lui.
 */
public class PassBallDefensorAction extends GoapAction {

	private boolean ballPassed = false;

	public PassBallDefensorAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KICK_OFF, false);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
		addEffect(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.BALL_NEAR_TEAMMATE, true);
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
		//		Numero di avversari vicini, compagni liberi

		AbstractPlayer player = (AbstractPlayer) agent;
		String teamName = player.getRoboClient().getTeam();
		int teammates = player.getMemory().getTeammates(teamName).size();
		int opponents = player.getMemory().getOpponents(teamName).size();

		//		System.out.println("Teammates "+teammates+", Opponents: "+opponents);

		return teammates >= opponents;
	}

	@Override
	public boolean perform(Object agent) {
		//		La palla viene passata ad un compagno se il
		//		giocatore � ostacolato da un certo numero di
		//		avversari e alcuni compagni sono in zone pi�
		//		libere rispetto a lui.


		AbstractPlayer player = (AbstractPlayer) agent;

		System.out.println(player.getMemory().getuNum()+ " Performing "+getClass().getSimpleName());
		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			ObjPlayer closestPlayer = null;
			try {
				closestPlayer = player.getAction().closestTeammate();
				if(closestPlayer!=null){
					//					System.out.println(player.getMemory().getuNum()+" is trying to pass to player: "+closestPlayer.getuNum());
					player.getAction().kickToPoint(ball, MathHelp.getNextPlayerPoint(closestPlayer));
					Thread.sleep(1000);
					ballPassed = true;
				} else {
					player.getAction().kickToPoint(ball, new Position(0,0));
					ballPassed=true;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return true;
		}

		return false;
	}

}
