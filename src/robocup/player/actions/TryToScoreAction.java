package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjFlag;
import robocup.objInfo.ObjGoal;
import robocup.player.AbstractPlayer;

/*
TRY_TO_SCORE **************************************
PRECONDITIONS:
	TRY_TO_SCORE (FALSE)
	BALL_CATCHED (TRUE)

EFFECTS:
	TRY_TO_SCORE (TRUE)
	BALL_CATCHED (FALSE)

THINGS TO CHECK:

PERFORMING:
	Il giocatore prova a tirare, qui usiamo la 
	funzione smart kick.
 */
public class TryToScoreAction extends GoapAction {

	private boolean tryToScore = false;

	public TryToScoreAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_SEEN, true);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
		addEffect(GoapGlossary.BALL_CATCHED, false);
	}

	@Override
	public void reset() {
		tryToScore = false;
	}

	@Override
	public boolean isDone() {
		return tryToScore;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {

		// Il giocatore prova a tirare, qui usiamo la
		// funzione smart kick.

		AbstractPlayer player = ((AbstractPlayer) agent);

		System.out.println("Performing " + getClass().getSimpleName());

		try {
			if (player.getAction().isBallVisible()) {
				ObjBall ball = player.getMemory().getBall();
				if (ball.getDistance() <= 0.7) {// ball in kickable margin
					ObjGoal goal = player.getMemory().getOppGoal();
					if (goal != null) {// vediamo la porta?
						if (goal.getDistance() <= 17) {// dentro l'area
							// in caso di opponent vicini tira senza girarti a
							// cercare i pali
							if (player.getMemory().getLeftPost() == null) {
								System.out.println(goal.getDirection());
								player.getAction().turn(30);
								// Thread.sleep(50);
							} else if (player.getMemory().getRightPost() == null) {
								player.getAction().turn(-30);
							}
							Thread.sleep(100);
							ObjFlag flagLeft = player.getMemory().getLeftPost();
							ObjFlag flagRight = player.getMemory().getRightPost();
							player.getAction().smartKick(flagLeft, flagRight);
							tryToScore = true;

						} else {
							player.getAction().kick(5, goal.getDirection());

						}
					} else {// goal null
						if (ball.getDistance() < 0.7) {// gira la palla
							// muovere la palla verso la porta
							player.getAction().kick(5, -player.getDirection());

						} else {// mi avvicino alla palla
							player.getAction().turn(ball.getDirection());
							Thread.sleep(50);
							player.getAction().dash(100);
						}
					}
				} else {// ball distante da me
					player.getAction().turn(ball.getDirection());
					Thread.sleep(50);
					player.getAction().dash(100);

				}
			} else {
				player.getRoboClient().turn(30.0);
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
