package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
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
	giocatore è ostacolato da un certo numero di
	avversari e alcuni compagni sono in zone più
	libere rispetto a lui.
 */
public class PassBallDefensorAction extends GoapAction {

	private boolean ballPassed = false;

	public PassBallDefensorAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
		addEffect(GoapGlossary.BALL_CATCHED, false);
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
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		La palla viene passata ad un compagno se il
		//		giocatore è ostacolato da un certo numero di
		//		avversari e alcuni compagni sono in zone più
		//		libere rispetto a lui.

//		System.out.println("Performing "+getClass().getSimpleName());
		
		AbstractPlayer player = (AbstractPlayer) agent;
		ballPassed = true;

		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			ObjPlayer closestPlayer = null;
			try {
				closestPlayer = player.getAction().closestTeammate();
				if(closestPlayer!=null){
					System.out.println("Try to pass to player: "+closestPlayer.getuNum());
					return player.getAction().passBall(ball, closestPlayer);
				} else {
					player.getAction().kickToPoint(ball, new Position(0,0));
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
