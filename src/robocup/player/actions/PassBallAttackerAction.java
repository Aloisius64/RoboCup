package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Position;

/*
 */
public class PassBallAttackerAction extends GoapAction {

	private boolean ballPassed = false;

	public PassBallAttackerAction() {
		super(1.0f);
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
		return ballPassed;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		// Numero di avversari vicini, compagni liberi

		AbstractPlayer player = (AbstractPlayer) agent;
		String teamName = player.getRoboClient().getTeam();
		int opponents = player.getMemory().getNearestOpponents(teamName, 5);

		// System.out.println("Teammates "+teammates+", Opponents: "+opponents);

		return opponents >= 2;
	}

	@Override
	public boolean perform(Object agent) {
		// La palla viene passata ad un compagno se il
		// giocatore � ostacolato da un certo numero di
		// avversari e alcuni compagni sono in zone pi�
		// libere rispetto a lui.

		AbstractPlayer player = (AbstractPlayer) agent;
		if(!player.getAction().isPlayMode("play_on"))
			return false;
		// System.out.println(player.getMemory().getuNum()+ " Performing
		// "+getClass().getSimpleName());
		if (player.getMemory().getBall() != null) {
			ObjBall ball = player.getMemory().getBall();
			ObjPlayer closestPlayer = null;
			try {
				closestPlayer = player.getAction().closestTeammate();
				if (closestPlayer != null) {
					// System.out.println(player.getMemory().getuNum()+" is
					// trying to pass to player: "+closestPlayer.getuNum());
					Polar p = MathHelp.getNextPlayerPoint(closestPlayer);
					player.getAction().kickToPoint(ball, p);
					ballPassed = true;
				} else {
					player.getAction().kickToPoint(ball, new Position(0, 0));
					ballPassed = true;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return true;
		}

		return false;
	}

}
