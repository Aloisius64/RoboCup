package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
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

		System.out.println("Performing "+getClass().getSimpleName());
		
		AbstractPlayer player = (AbstractPlayer) agent;
		ballPassed = true;

		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			player.getAction().kickToPoint(ball, new Position(0,0));
			return true;
		}
		
		return false;
	}

}
