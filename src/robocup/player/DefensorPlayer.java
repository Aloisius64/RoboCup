package robocup.player;

import com.github.robocup_atan.atan.model.enums.Flag;
import com.github.robocup_atan.atan.model.enums.PlayMode;
import com.github.robocup_atan.atan.model.enums.ServerParams;

import robocup.FlagCategory;
import robocup.formation.AbstarctFormation;
import robocup.sensors.SeenFlag;

public class DefensorPlayer extends AbstractPlayer {

	public DefensorPlayer(AbstarctFormation formation) {
		super(formation);
	}


	@Override
	public void infoSeeFlagGoalOther(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
		knowledgeBase.addFlag(FlagCategory.GOAL_OTHER, flag,
				new SeenFlag(flag, distance, direction, distChange, dirChange, bodyFacingDirection, headFacingDirection));
	}
	
	@Override
	public void infoSeePlayerOther(int number, boolean goalie, double distance, double direction, double distChange,
			double dirChange, double bodyFacingDirection, double headFacingDirection) {
			
		
	}

	@Override
	public void postInfo() {
		if (PlayMode.PLAY_ON == this.knowledgeBase.getPlayMode()) {
			//player.say("giovanniDice");
			if (knowledgeBase.getBall().isSeenInLastStep()) {
				if (knowledgeBase.getBall().getDistance() > (double) serverInfo.get(ServerParams.KICKABLE_MARGIN)) {
					player.turn(knowledgeBase.getBall().getDirection());
					player.dash(100);
				} else {
					if (!knowledgeBase.getFlags().containsKey(FlagCategory.GOAL_OTHER)
							|| !knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).containsKey(Flag.CENTER)) {
						player.turn(90.0);
					} else {
						if (knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
								.getDistance() > 20.0) {
							player.kick(5, knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
									.getDirection());
						} else {
							player.kick(9001, knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
									.getDirection());

						}
					}
				}
			} else {
				player.turn(60.0);
			}
		}

	}

}
