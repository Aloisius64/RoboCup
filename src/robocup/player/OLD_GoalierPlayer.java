//package robocup.player;
//
//import com.github.robocup_atan.atan.model.enums.Flag;
//import com.github.robocup_atan.atan.model.enums.PlayMode;
//
//import robocup.FlagCategory;
//import robocup.sensors.SeenFlag;
//
//public class OLD_GoalierPlayer extends OLD_AbstractPlayer {
//
//	public OLD_GoalierPlayer() {
//		super();
//	}
//
//	@Override
//	public void infoSeeFlagGoalOther(Flag flag, double distance, double direction, double distChange, double dirChange,
//			double bodyFacingDirection, double headFacingDirection) {
//		knowledgeBase.addFlag(FlagCategory.GOAL_OTHER, flag, new SeenFlag(flag, distance, direction, distChange,
//				dirChange, bodyFacingDirection, headFacingDirection));
//	}
//
//	@Override
//	public void infoHearPlayMode(PlayMode playMode) {
//		if (playMode == PlayMode.BEFORE_KICK_OFF || playMode == PlayMode.GOAL_L || playMode == PlayMode.GOAL_R) {
//			if (player.getNumber() == 1)
//				player.move(-50, 0);
//			if (player.getNumber() == 2)
//				player.move(-50, -4);
//			if (player.getNumber() == 3)
//				player.move(-50, 3);
//		}
//	}
//
//	@Override
//	public void infoSeePlayerOther(int number, boolean goalie, double distance, double direction, double distChange,
//			double dirChange, double bodyFacingDirection, double headFacingDirection) {
//
//	}
//
//	@Override
//	public void postInfo() {
//		// if (PlayMode.PLAY_ON == this.knowledgeBase.getPlayMode()) {
//		// //player.say("giovanniDice");
//		// if (knowledgeBase.getBall().isSeenInLastStep()) {
//		// if (knowledgeBase.getBall().getDistance() > (double)
//		// serverInfo.get(ServerParams.KICKABLE_MARGIN)) {
//		// player.turn(knowledgeBase.getBall().getDirection());
//		// player.dash(100);
//		// } else {
//		// if (!knowledgeBase.getFlags().containsKey(FlagCategory.GOAL_OTHER)
//		// ||
//		// !knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).containsKey(Flag.CENTER))
//		// {
//		// player.turn(90.0);
//		// } else {
//		// if
//		// (knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
//		// .getDistance() > 20.0) {
//		// player.kick(5,
//		// knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
//		// .getDirection());
//		// } else {
//		// player.kick(9001,
//		// knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
//		// .getDirection());
//		//
//		// }
//		// }
//		// }
//		// } else {
//		// player.turn(60.0);
//		// }
//		// }
//
//	}
//
//}
