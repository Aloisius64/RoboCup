package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.ai.OffensiveAI;
import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;

/** @class Forward (currently unused)
 * The Forward class inherits from the Player class.  The Forward is a specialized
 * type of Player that focuses on offensive behaviors such as scoring and ball interception.
 */
public class OffensivePlayer extends AbstractPlayer{

	public OffensivePlayer() {
		super();
		this.setAi(new OffensiveAI(this));
	}

	public OffensivePlayer(String team) {
		super(team);
		this.setAi(new OffensiveAI(this));
	}

	/********************************************/
	/* 			IGoap implementations 			*/
	/********************************************/

	@Override
	public HashMap<String, Object> getWorldState() {
		HashMap<String, Object> worldState = new HashMap<>();
		
		return worldState;
	}

	@Override
	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<>();
		
		return goal;
	}

	@Override
	public void planFailed(HashMap<String, Object> failedGoal) {
		System.out.println("Player "+getMemory().getuNum()+" - Plan failed");
	}

	@Override
	public void planFound(HashMap<String, Object> goal, Queue<GoapAction> actions) {
		System.out.println("Player "+getMemory().getuNum()+" - Plan found "+GoapAgent.prettyPrint(actions));
	}

	@Override
	public void actionsFinished() {
		System.out.println("Player "+getMemory().getuNum()+" - Actions finished");
	}

	@Override
	public void planAborted(GoapAction aborter) {
		System.out.println("Player "+getMemory().getuNum()+" - Plan aborted");
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();

		return actions;
	}

}
