package goap;

import java.util.HashMap;
import java.util.Queue;

/**
 * Collect the world data for this Agent that will be
 * used for GOAP planning.
 * Any agent that wants to use GOAP must implement
 * this interface. It provides information to the GOAP
 * planner so it can plan what actions to use.
 * 
 * It also provides an interface for the planner to give 
 * feedback to the Agent and report success/failure.
 */
public interface IGoap {
    /**
	 * The starting state of the Agent and the world.
	 * Supply what states are needed for actions to run.
	 */
    HashMap<String, Object> getWorldState();

    /**
	 * Give the planner a new goal so it can figure out 
	 * the actions needed to fulfill it.
	 */
    HashMap<String, Object> createGoalState();

    /**
	 * No sequence of actions could be found for the supplied goal.
	 * You will need to try another goal
	 */
    void planFailed(HashMap<String, Object> failedGoal);

    /**
	 * A plan was found for the supplied goal.
	 * These are the actions the Agent will perform, in order.
	 */
    void planFound(HashMap<String, Object> goal, Queue<GoapAction> actions);

    /**
	 * All actions are complete and the goal was reached. Hooray!
	 */
    void actionsFinished();

    /**
	 * One of the actions caused the plan to abort.
	 * That action is returned.
	 */
    void planAborted(GoapAction aborter);

}
