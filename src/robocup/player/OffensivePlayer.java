package robocup.player;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

import com.sun.xml.internal.messaging.saaj.soap.AttachmentPartImpl;

import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;
import robocup.goap.GoapGlossary;
import robocup.goap.GoapPlanner;
import robocup.objInfo.ObjFlag;
import robocup.objInfo.ObjPlayer;
import robocup.player.actions.AttackerIdleAction;
import robocup.player.actions.ComeBackHomeAction;
import robocup.player.actions.FollowAction;
import robocup.player.actions.KickOffAction;
import robocup.player.actions.PassBallAttackerAction;
import robocup.player.actions.SearchBallAction;
import robocup.player.actions.StoleBallAttackerAction;
import robocup.player.actions.TryToScoreAction;
import robocup.utility.kick.GoalView;
import robocup.utility.kick.KickMathUtility;

public class OffensivePlayer extends AbstractPlayer {

	public OffensivePlayer() {
		super("Team");
	}

	public OffensivePlayer(String team) {
		super(team);
	}

	/********************************************/
	/* IGoap implementations */
	/********************************************/

	@Override
	public void planFailed(HashMap<String, Boolean> failedGoal) {
//		System.err.println("Player " + getMemory().getuNum() + " " + getMemory().getSide() + " - Plan failed");
		// if (getMemory().getuNum() == 7) {
		// GoapPlanner.printMap(getWorldState());
		// }
	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
//		System.err.println("Player " + getMemory().getuNum() + " " + getMemory().getSide() + " - Plan found"
//				+ GoapAgent.prettyPrint(actions));
	}

	@Override
	public void actionsFinished() {
		// System.err.println("Player "+getMemory().getuNum()+" - Actions
		// finished");
	}

	@Override
	public void planAborted(GoapAction aborter) {
		// System.out.println("Player "+getMemory().getuNum()+" - Plan
		// aborted");
	}

	@Override
	public HashMap<String, Boolean> getWorldState() {
		HashMap<String, Boolean> worldState = new HashMap<>();

		// Set worldState from player memory
		worldState.put(GoapGlossary.TRY_TO_SCORE, false);
		worldState.put(GoapGlossary.KICK_OFF, false);
		worldState.put(GoapGlossary.REPOSITIONED, getAction().isPlayMode("goal_"));
		worldState.put(GoapGlossary.BALL_SEEN, getAction().isBallVisible());
		worldState.put(GoapGlossary.BALL_CATCHED, getAction().isBallInRangeOf(1.0));
		worldState.put(GoapGlossary.BALL_NEAR_TEAMMATE, getAction().isBallNearTeammate());
		worldState.put(GoapGlossary.KEEP_AREA_SAFE, !getAction().isBallInOurField());
		// worldState.put(GoapGlossary.BEHIND_BALL_LINE,
		// getAction().isBehindBall());
		// worldState.put(GoapGlossary.GOAL_SCORED,
		// getAction().isPlayMode("goal_" + getMemory().getSide()));
//		if (getMemory().getuNum() == 11) {
//			System.out.println("Player of side " + getMemory().getSide());
//			GoapPlanner.printMap(worldState);
//		}
		return worldState;
	}

	@Override
	public HashMap<String, Boolean> createGoalState() {
		HashMap<String, Boolean> goal = new HashMap<>();

		if (getAction().isPlayMode("play_on")) {
			goal.put(GoapGlossary.TRY_TO_SCORE, true);
		} else if (getAction().isPlayMode("kick_off")) {
			goal.put(GoapGlossary.KICK_OFF, true);
		}else if(getAction().isPlayMode("goal_")){
			goal.put(GoapGlossary.REPOSITIONED, true);
		}

		return goal;
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();

		actions.add(new AttackerIdleAction());
		actions.add(new SearchBallAction());
		actions.add(new FollowAction());
		actions.add(new KickOffAction());
		actions.add(new PassBallAttackerAction());
		actions.add(new TryToScoreAction());
		actions.add(new StoleBallAttackerAction());
		actions.add(new ComeBackHomeAction());

		return actions;
	}

	@Override
	public int evaluate() {
		int evaluation = 0;
		double weightDistance = 0.3;
		double weightGoalView = 1.0 - weightDistance;
		double goalViewEvaluationValue = 0;
		if (getMemory().getOppGoal() != null) {
			List<ObjPlayer> otherPlayers = getMemory().getOpponents(getRoboClient().getTeam());
			ObjFlag leftPost = getMemory().getLeftPost();
			ObjFlag rightPost = getMemory().getRightPost();
			double leftDir = leftPost == null ? -45.0 : leftPost.getDirection();
			double rightDir = rightPost == null ? 45.0 : rightPost.getDirection();
			GoalView goalView = KickMathUtility.getGoalView(leftDir, rightDir, otherPlayers, 0.7);
			goalViewEvaluationValue = goalView.getLargerInterval() == null ? 0 : goalView.getLargerInterval().getSize();

		}

		double distanceEvaluationValue = 100.0
				- (getMemory().getOppGoal() == null ? 100 : getMemory().getOppGoal().getDistance());
		goalViewEvaluationValue = goalViewEvaluationValue * 100 / MAX_VIEW;
		evaluation = (int) (distanceEvaluationValue * weightDistance + goalViewEvaluationValue * weightGoalView);
		this.setCurrentEvaluation(evaluation);
		return evaluation;
	}

	@Override
	public void broadcast(int evaluation) throws UnknownHostException, InterruptedException {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ITALY);
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("##.#", otherSymbols);
		String messageEvaluation = getMemory().getuNum() + "A" + evaluation;
		String messageDistanceBall = Integer.toString(getMemory().getuNum());
		if (getMemory().getBall() != null)
			messageDistanceBall = messageDistanceBall.concat("B" + getMemory().getBall().getDistance());
		else
			messageDistanceBall = messageDistanceBall.concat("B150");


		messageDistanceBall = messageDistanceBall.replace('.', 'p');
		getAction().say(messageDistanceBall);
		getAction().say(messageEvaluation);
		// System.out.println("broadcast=" + evaluation + " from " +
		// getMemory().getuNum());
	}

}
