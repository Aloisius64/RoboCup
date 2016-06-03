package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.actions.MarkPlayerAction;
import robocup.player.actions.PassBallAttackerAction;
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

	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
	
	}

	@Override
	public void actionsFinished() {
	
	}

	@Override
	public void planAborted(GoapAction aborter) {
	
	}
	
	@Override
	public HashMap<String, Boolean> getWorldState() {
		HashMap<String, Boolean> worldState = new HashMap<>();

		// Set worldState from player memory
		worldState.put(GoapGlossary.TRY_TO_SCORE, true);

		return worldState;
	}

	@Override
	public HashMap<String, Boolean> createGoalState() {
		HashMap<String, Boolean> goal = new HashMap<>();

		goal.put(GoapGlossary.TRY_TO_SCORE, true);

		// Il goal dipende dal tipo di play mode
		// cambiarlo in base alla modalit� di gioco

		return goal;
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();

		actions.add(new SearchBallAction());
		actions.add(new MarkPlayerAction());
		actions.add(new PassBallAttackerAction());
		actions.add(new TryToScoreAction());
		actions.add(new StoleBallAttackerAction());

		return actions;
	}
	
}
