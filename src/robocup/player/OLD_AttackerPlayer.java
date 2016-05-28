//package robocup.player;
//
//import java.util.List;
//
//import com.github.robocup_atan.atan.model.enums.Flag;
//import com.github.robocup_atan.atan.model.enums.PlayMode;
//import com.github.robocup_atan.atan.model.enums.ServerParams;
//
//import robocup.FlagCategory;
//import robocup.sensors.SeenFlag;
//import robocup.sensors.SeenPlayer;
//import robocup.utility.kick.BoundType;
//import robocup.utility.kick.GoalView;
//import robocup.utility.kick.KickMathUtility;
//import robocup.utility.kick.ViewInterval;
//
//public class AttackerPlayer extends AbstractPlayer {
//
//	public AttackerPlayer() {
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
//		super.infoHearPlayMode(playMode);
//		if (playMode == PlayMode.BEFORE_KICK_OFF || playMode == PlayMode.GOAL_L || playMode == PlayMode.GOAL_R) {
//			player.move(20, 0);
//		}
//	}
//
//	@Override
//	public void postInfo() {
//		if (PlayMode.PLAY_ON == this.knowledgeBase.getPlayMode()) {
//			if (knowledgeBase.getBall().isSeenInLastStep()) {
//				if (knowledgeBase.getBall().getDistance() > (double) serverInfo.get(ServerParams.KICKABLE_MARGIN)) {
//					player.turn(knowledgeBase.getBall().getDirection());
//					player.dash(100);
//				} else {
//					if (!knowledgeBase.getFlags().containsKey(FlagCategory.GOAL_OTHER)
//							|| !knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).containsKey(Flag.CENTER)) {
//						player.turn(90.0);
//					} else {
//						if (knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
//								.getDistance() > 20.0) {
//							player.kick(5, knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.CENTER)
//									.getDirection());
//						} else {
//							if (!knowledgeBase.hasFlag(FlagCategory.GOAL_OTHER, Flag.LEFT)
//									|| !knowledgeBase.hasFlag(FlagCategory.GOAL_OTHER, Flag.RIGHT)) {
//								player.turn(knowledgeBase.getFlag(FlagCategory.GOAL_OTHER, Flag.CENTER).getDirection());
//							} else {
//								smartKick();
//							}
//						}
//					}
//				}
//			} else {
//				player.turn(60.0);
//			}
//		}
//	}
//
//	private void smartKick() {
//		double leftFlagDirection = knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.LEFT).getDirection();
//		double rightFlagDirection = knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.RIGHT)
//				.getDirection();
//
//		List<SeenPlayer> otherPlayers = knowledgeBase.getOtherPlayers();
//		double playerSize = (double) this.serverInfo.get(ServerParams.PLAYER_SIZE);
//
//		GoalView goalView = KickMathUtility.getGoalView(leftFlagDirection, rightFlagDirection, otherPlayers,
//				playerSize);
//
//		ViewInterval largerInterval = goalView.getLargerInterval();
//		if (largerInterval.getStart().getBoundType() == BoundType.POST) {
//			player.kick(9001, largerInterval.getStart().getAngle() + 5);
//		} else if (largerInterval.getEnd().getBoundType() == BoundType.POST) {
//			player.kick(9001, largerInterval.getEnd().getAngle() - 5);
//		} else {
//			player.kick(9001, largerInterval.getMidAngle());
//		}
//	}
//}
