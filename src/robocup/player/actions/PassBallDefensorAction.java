package robocup.player.actions;

import robocup.goap.GoapAction;

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

	public PassBallDefensorAction() {
		super(1.0f);
		//		addPrecondition(key, value);
		//		addEffect(key, value);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform(Object agent) {
		// TODO Auto-generated method stub
		return false;
	}

}
