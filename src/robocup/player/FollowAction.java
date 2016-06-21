package robocup.player;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Position;

public class FollowAction extends GoapAction {

	private boolean follow = false;

	public FollowAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, true);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
	}

	@Override
	public void reset() {
		follow = false;

	}

	@Override
	public boolean isDone() {
		return follow;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			// player.getAction().goToSidePoint(new
			// Position(player.getPosition().x+10,player.getHome().y));
			if (player.getMemory().getOppGoal() == null) {
				player.getAction().turn(30.0);
				return true;
			} else {
				if (player.getMemory().getOppGoal().getDistance() > 15.0) {
					Polar p = MathHelp
							.getPolar(new Position(50.0, player.getPosition().y));
					System.out.println(player.getMemory().getuNum() + " " + p.t);
					player.getAction().dash(100, p.t);
					return true;
				} else {
					follow = true;
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
