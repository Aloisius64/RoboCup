package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

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
	(probabilit� di segnare) ricevuto da altri
	(serve comunicazione) attaccanti e lo confronta
	con il proprio, oppure se � ostacolato da un
	certo numero di avversari.

PERFORMING:
	L'attaccante passa la palla ad un compagno che
	ha l'indice di successo maggiore (si potrebbe
	verificare forse che chi conduce palla ha il
	massimo indice di successo ma essendo ostacolato
	da un certo numero di avversari � meglio passare
	la palla ad un compagno che dopo di lui ha il pi�
	altro indice di successo)
 */
public class PassBallAttackerAction extends GoapAction {

	private boolean ballPassed = false;

	public PassBallAttackerAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KICK_OFF, false);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
		addEffect(GoapGlossary.BALL_CATCHED, false);
	}

	@Override
	public void reset() {
		ballPassed = false;
	}

	@Override
	public boolean isDone() {
		return ballPassed = false;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		L'attaccante controlla un indice di successo
		//		(probabilit� di segnare) ricevuto da altri
		//		(serve comunicazione) attaccanti e lo confronta
		//		con il proprio, oppure se � ostacolato da un
		//		certo numero di avversari.
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		L'attaccante passa la palla ad un compagno che
		//		ha l'indice di successo maggiore (si potrebbe
		//		verificare forse che chi conduce palla ha il
		//		massimo indice di successo ma essendo ostacolato
		//		da un certo numero di avversari � meglio passare
		//		la palla ad un compagno che dopo di lui ha il pi�
		//		altro indice di successo)
		return false;
	}

}
