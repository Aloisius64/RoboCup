package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

/*
SHOOT_BALL (Passaggio lungo oltre la metà campo) **
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHED (TRUE)

EFFECTS:
	KEEP_AREA_SAFE (TRUE)
	BALL_CATCHED (FALSE)

THINGS TO CHECK:
	Si è visto un attaccante particolarmente 
	libero oltre la metà campo

PERFORMING:
	La palla è calciata oltre la metà campo 
	verso un proprio compagno
 */
public class ShootBallDefensorAction extends GoapAction {

	private boolean ballShooted = false;

	public ShootBallDefensorAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, false);
		addEffect(GoapGlossary.BALL_CATCHED, true);
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
		//		Si è visto un attaccante particolarmente 
		//		libero oltre la metà campo
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		La palla è calciata oltre la metà campo 
		//		verso un proprio compagno
		return false;
	}

}
