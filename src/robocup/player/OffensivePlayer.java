package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;
import robocup.goap.GoapGlossary;
import robocup.goap.GoapPlanner;
import robocup.player.actions.IdleAction;
import robocup.player.actions.SearchBallAction;
import robocup.player.actions.StoleBallAttackerAction;
import robocup.player.actions.TryToScoreAction;

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
//		System.err.println("Player " + getMemory().getuNum() + " - Plan failed");
	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
//		System.err.println("Player " + getMemory().getuNum() + " - Plan found " + GoapAgent.prettyPrint(actions));
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
		worldState.put(GoapGlossary.BALL_SEEN, getAction().isBallVisible());
		worldState.put(GoapGlossary.BALL_CATCHED, getAction().isBallInRangeOf(1.0));
		worldState.put(GoapGlossary.BALL_NEAR_TEAMMATE, getAction().isBallNearTeammate());
		GoapPlanner.printMap(worldState);
		return worldState;
	}

	@Override
	public HashMap<String, Boolean> createGoalState() {
		HashMap<String, Boolean> goal = new HashMap<>();

//		if (getMemory().getPlayMode().equals("play_on")) {
			goal.put(GoapGlossary.TRY_TO_SCORE, true);
//		} else if (getMemory().getPlayMode().equals("before_kick_off")) {
//			goal.put(GoapGlossary.KEEP_AREA_SAFE, true);
//		}
		
		return goal;
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();

		actions.add(new IdleAction());
		actions.add(new SearchBallAction());
		// actions.add(new MarkPlayerAction());
		// actions.add(new PassBallAttackerAction());
		actions.add(new TryToScoreAction());
		actions.add(new StoleBallAttackerAction());

		return actions;
	}

}
