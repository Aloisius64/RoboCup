package robocup.ai;

import java.util.HashMap;

public abstract class GoapAction {

    private HashMap<String, Object> preconditions;
    private HashMap<String, Object> effects;

    /* The cost of performing the action. 
	 * Figure out a weight that suits the action. 
	 * Changing it will affect what actions are chosen during planning.
     */
    public float cost = 1f;

    /**
	 * An action often has to perform on an object. This is that object. Can be null. 
     */
    public Object target;

    public GoapAction() {
        preconditions = new HashMap<>();
        effects = new HashMap<>();
    }

    public void doReset() {
        target = null;
        reset();
    }

    /**
	 * Reset any variables that need to be reset before planning happens again.
	 */
    public abstract void reset();

    /**
	 * Is the action done?
	 */
    public abstract boolean isDone();

    /**
	 * Procedurally check if this action can run. Not all actions
	 * will need this, but some might.
	 */
    public abstract boolean checkProceduralPrecondition(Object agent);

    /**
	 * Run the action.
	 * Returns True if the action performed successfully or false
	 * if something happened and it can no longer perform. In this case
	 * the action queue should clear out and the goal cannot be reached.
	 */
    public abstract boolean perform(Object agent);
        
    public void addPrecondition(String key, Object value) {
        preconditions.put(key, value);
    }

    public void removePrecondition(String key) {
    	preconditions.remove(key);
    }

    public void addEffect(String key, Object value) {
        effects.put(key, value);
    }

    public void removeEffect(String key) {
    	effects.remove(key);
    }

	public HashMap<String, Object> getPreconditions() {
		return preconditions;
	}

	public HashMap<String, Object> getEffects() {
		return effects;
	}
    
}
