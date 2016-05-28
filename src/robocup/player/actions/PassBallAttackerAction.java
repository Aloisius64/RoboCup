package robocup.player.actions;

import robocup.goap.GoapAction;

/*
PASS_BALL_ATTACK **********************************
PRECONDITIONS:
	TRY_TO_SCORE (FALSE)
	BALL_CATCHED (TRUE)

EFFECTS:
	TRY_TO_SCORE (TRUE)
	BALL_CATCHED (FALSE)

THINGS TO CHECK:
	L'attaccante controlla un indice di successo
	(probabilità di segnare) ricevuto da altri
	(serve comunicazione) attaccanti e lo confronta
	con il proprio, oppure se è ostacolato da un
	certo numero di avversari.

PERFORMING:
	L'attaccante passa la palla ad un compagno che
	ha l'indice di successo maggiore (si potrebbe
	verificare forse che chi conduce palla ha il
	massimo indice di successo ma essendo ostacolato
	da un certo numero di avversari è meglio passare
	la palla ad un compagno che dopo di lui ha il più
	altro indice di successo)
 */
public class PassBallAttackerAction extends GoapAction {

	public PassBallAttackerAction() {
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
