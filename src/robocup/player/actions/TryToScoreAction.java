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
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, false);
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
		// System.out.println("Performing " + getClass().getSimpleName());
		if(!player.getAction().isPlayMode("play_on"))
			return false;
		try {
			if (player.getAction().isBallVisible()) {
				if (player.getAction().isBallNearTeammate())
					return false;
				ObjBall ball = player.getMemory().getBall();
				if (ball.getDistance() <= 0.7) {

					ObjGoal goal = player.getMemory().getOppGoal();
					Double direction = player.getAction().getDirectionPassToTeammates();
					if (direction != null) {
						player.getAction().kick(20, direction);
						System.out.println("pass ball");
						tryToScore = true;
						return true;
					}

					if (goal != null) {
						if (goal.getDistance() <= 20 || player.getMemory().getBallPos(ball).x > 40) {
							if (player.getMemory().getLeftPost() == null) {
								player.getAction().turn(30);
							} else if (player.getMemory().getRightPost() == null) {
								player.getAction().turn(-30);
							}
							Thread.sleep(100);
							ObjFlag flagLeft = player.getMemory().getLeftPost();
							ObjFlag flagRight = player.getMemory().getRightPost();
							player.getAction().smartKick(flagLeft, flagRight);
							tryToScore = true;

						} else {
							player.getAction().kick(10, goal.getDirection());

						}
					} else {
						if (ball.getDistance() < 0.7) {

							player.getAction().kick(10, -player.getDirection());
							return true;

						} else {
							player.getAction().turn(ball.getDirection());
							Thread.sleep(100);
							player.getAction().dash(50);
							return true;
						}
					}
				} else {// ball distante da me
					player.getAction().turn(ball.getDirection());
					Thread.sleep(100);
					player.getAction().dash(60);

				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
