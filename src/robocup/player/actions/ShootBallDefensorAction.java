package robocup.player.actions;

import robocup.goap.GoapAction;

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

	public ShootBallDefensorAction() {
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
