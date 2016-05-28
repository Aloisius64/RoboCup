package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

/*
SHOOT_BALL_GOALIE (Rinvio verso la metà campo) ***
	PRECONDITIONS:
		KEEP_AREA_SAFE (FALSE)
		BALL_CATCHED (TRUE)

	EFFECTS:
		KEEP_AREA_SAFE (TRUE)

	THINGS TO CHECK:
		Presenza di un certo numero di avversari
		vicino la porta. Se tale numero è troppo
		elevato (maggiore dei propri difensori)

	PERFORMING:
		Il portiere calcia la palla verso la metà 
		campo (possibilmente verso un proprio 
		compagno)
 */
public class ShootBallGoalieAction extends GoapAction {

	private boolean ballShooted = false;

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
		//		vicino la porta. Se tale numero è troppo
		//		elevato (maggiore dei propri difensori)
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il portiere calcia la palla verso la metà 
		//		campo (possibilmente verso un proprio 
		//		compagno)
		return false;
	}

}
