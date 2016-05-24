package robocup.ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Plans what actions can be completed in order to fulfill a goal state.
 * Plan what sequence of actions can fulfill the goal.
 * Returns null if a plan could not be found, or a list of the actions
 * that must be performed, in order, to fulfill the goal.
 */
public class GoapPlanner {
	
    public Queue<GoapAction> plan(Object agent,
                                  HashSet<GoapAction> availableActions,
                                  HashMap<String, Object> worldState,
                                  HashMap<String, Object> goal) {
        // reset the actions so we can start fresh with them
    	for (GoapAction goapAction : availableActions) {
			goapAction.doReset();
		}

        // check what actions can run using their checkProceduralPrecondition
        HashSet<GoapAction> usableActions = new HashSet<GoapAction>();
        for (GoapAction a : availableActions) {
            if (a.checkProceduralPrecondition(agent))
                usableActions.add(a);
        }

        // we now have all actions that can run, stored in usableActions

        // build up the tree and record the leaf nodes that provide a solution to the goal.
        List<Node> leaves = new LinkedList<>();

        // build graph
        Node start = new Node(null, 0, worldState, null);
        boolean success = buildGraph(start, leaves, usableActions, goal);

        if (!success) {
            // oh no, we didn't get a plan
        	System.out.println("NO PLAN");
            return null;
        }

        // get the cheapest leaf
        Node cheapest = null;
        for (Node leaf : leaves) {
            if (cheapest == null)
                cheapest = leaf;
            else {
                if (leaf.runningCost < cheapest.runningCost)
                    cheapest = leaf;
            }
        }

        // get its node and work back through the parents
        List<GoapAction> result = new LinkedList<>();
        Node n = cheapest;
        while (n != null) {
            if (n.action != null) {
                result.add(0, n.action);	// insert the action in the front
            }
            n = n.parent;
        }
        // we now have this action list in correct order

        Queue<GoapAction> queue = new LinkedList<>();
        for (GoapAction a : result) {
            queue.add(a);
        }

        // hooray we have a plan!
        return queue;
    }

    /**
	 * Returns true if at least one solution was found.
	 * The possible paths are stored in the leaves list. Each leaf has a
	 * 'runningCost' value where the lowest cost will be the best action
	 * sequence.
	 */
    private boolean buildGraph(Node parent, List<Node> leaves, HashSet<GoapAction> usableActions, HashMap<String, Object> goal) {
        boolean foundOne = false;

        // go through each action available at this node and see if we can use it here
        for (GoapAction action : usableActions) {

            // if the parent state has the conditions for this action's preconditions, we can use it here
            if (inState(action.getPreconditions(), parent.state)) {

                // apply the action's effects to the parent state
            	HashMap<String, Object> currentState = populateState(parent.state, action.getEffects());
                //Debug.Log(GoapAgent.prettyPrint(currentState));
                Node node = new Node(parent, parent.runningCost + action.cost, currentState, action);

                if (inState(goal, currentState)) {
                    // we found a solution!
                    leaves.add(node);
                    foundOne = true;
                } else {
                    // not at a solution yet, so test all the remaining actions and branch out the tree
                    HashSet<GoapAction> subset = actionSubset(usableActions, action);
                    boolean found = buildGraph(node, leaves, subset, goal);
                    if (found)
                        foundOne = true;
                }
            }
        }

        return foundOne;
    }

    /**
	 * Create a subset of the actions excluding the removeMe one. Creates a new set.
	 */
    private HashSet<GoapAction> actionSubset(HashSet<GoapAction> actions, GoapAction removeMe) {
        HashSet<GoapAction> subset = new HashSet<GoapAction>();
        for (GoapAction a : actions) {
            if (!a.equals(removeMe))
                subset.add(a);
        }
        return subset;
    }

    /**
	 * Check that all items in 'test' are in 'state'. If just one does not match or is not there
	 * then this returns false.
	 */
    private boolean inState(HashMap<String, Object> test, HashMap<String, Object> state) {
        boolean allMatch = true;
        
        for (String keyTetst : test.keySet()) {
			boolean match = false;
			for (String keyState : state.keySet()) {
				if(state.get(keyState).equals(test.get(keyTetst))){
					match = true;
					break;
				}
			}
			if(!match)
				allMatch = false;
		}
        return allMatch;
    }

    /**
	 * Apply the stateChange to the currentState
	 */
    private HashMap<String, Object> populateState(HashMap<String, Object> currentState, HashMap<String, Object> stateChange) {
    	HashMap<String, Object> state = new HashMap<>();
    	
        // copy the KVPs over as new objects
    	for (String key : currentState.keySet()) {
			state.put(key, currentState.get(key));
		}
    	
        for (String stateChangeKey : stateChange.keySet()) {
            // if the key exists in the current state, update the Value
            boolean exists = false;

            for (String stateKey : state.keySet()) {
                if (stateKey.equals(stateChangeKey)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
            	state.remove(stateChangeKey);
            	Object value = stateChange.get(stateChangeKey);
            	state.put(stateChangeKey, value);
            }
            // if it does not exist in the current state, add it
            else {
                state.put(stateChangeKey, stateChange.get(stateChangeKey));
            }
        }
        return state;
    }

    /**
	 * Used for building up the graph and holding the running costs of actions.
	 */
    private class Node {
        public Node parent;
        public float runningCost;
        public HashMap<String, Object> state;
        public GoapAction action;

        public Node(Node parent, float runningCost, HashMap<String, Object> state, GoapAction action) {
            this.parent = parent;
            this.runningCost = runningCost;
            this.state = state;
            this.action = action;
        }
    }

}


