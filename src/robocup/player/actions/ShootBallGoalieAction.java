package robocup.player.actions;

import robocup.goap.GoapAction;

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

	public ShootBallGoalieAction() {
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
