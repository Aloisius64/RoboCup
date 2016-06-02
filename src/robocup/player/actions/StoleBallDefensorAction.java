package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;

/*
DEFENSOR_STOLE_BALL ****************************************
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHED (FALSE)
	BALL_NEAR (TRUE)
	PLAYER_MARKED (FALSE) // Forse non serve per questa azione

EFFECTS:
	BALL_CATCHED (TRUE)

THINGS TO CHECK:
	La palla è controllata da un avversario o
	non, basta che sia nella propria metà campo,
	ed è ad una certa distanza dal difensore

PERFORMING:
	Il difensore si avvicina alla palla è cerca
	di recuperarla
 */
public class StoleBallDefensorAction extends GoapAction {

	private boolean ballStoled = false;

	public StoleBallDefensorAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		//		addPrecondition(GoapGlossary.BALL_NEAR, true);
		//		addPrecondition(GoapGlossary.PLAYER_MARKED, false);
		addEffect(GoapGlossary.BALL_CATCHED, true);
	}

	@Override
	public void reset() {
		ballStoled = false;
	}

	@Override
	public boolean isDone() {
		return ballStoled;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		La palla è controllata da un avversario o
		//		non, basta che sia nella propria metà campo,
		//		ed è ad una certa distanza dal difensore
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il difensore si avvicina alla palla è cerca
		//		di recuperarla

		System.out.println("Performing "+getClass().getSimpleName());
		
		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			if (player.getMemory().isObjVisible("ball")) {
				ObjBall ball = player.getMemory().getBall();
				
				if ((ball.getDirection() > 5.0 
						|| ball.getDirection() < -5.0)) {
					player.getRoboClient().turn(ball.getDirection());
				}
				
				if(ball.getDistance() > 25){
					return false;
				} else if (ball.getDistance() > 0.7 
						&& player.getAction().isBallInOurField().booleanValue()) {
					player.getAction().interceptBall(ball);					
				} else if (ball.getDistance() <= 0.7) {
					ballStoled = true;
				}
				
				if(!player.getAction().isBallInOurField().booleanValue()){
					return false;
				}				
				
				return true;
				
			} else {
				player.getRoboClient().turn(30);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
