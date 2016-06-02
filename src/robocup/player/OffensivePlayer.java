package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.ai.AbstractAI;
import robocup.ai.OffensiveAI;
import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.actions.MarkPlayerAction;
import robocup.player.actions.PassBallAttackerAction;
import robocup.player.actions.SearchBallAction;
import robocup.player.actions.StoleBallAttackerAction;
import robocup.player.actions.TryToScoreAction;


/**
 * @class Forward (currently unused) The Forward class inherits from the Player
 *        class. The Forward is a specialized type of Player that focuses on
 *        offensive behaviors such as scoring and ball interception.
 */
public class OffensivePlayer extends AbstractPlayer {


	public OffensivePlayer() {
		super("st");
		AbstractAI ai = new OffensiveAI(this);
		ai.start();
	}
	
	public OffensivePlayer(String team) {
		super(team);
		AbstractAI ai = new OffensiveAI(this);
		ai.start();
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


	@Override
	public void run() {
		while (true) {
			try {

				receiveInput();
//				Thread.sleep(30);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
