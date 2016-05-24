package goap;

import java.util.Stack;

/**
 * Stack-based Finite State Machine.
 * Push and pop states to the FSM.
 * 
 * States should push other states onto the stack 
 * and pop themselves off.
 */
public class FSM {
	private Stack<FSMState> stateStack = new Stack<FSMState>();

	public FSMState FSMState;

	public void Update(Object object) {
		if (stateStack.peek() != null)
			stateStack.peek().update(this, object);
	}

	public void pushState(FSMState state) {
		stateStack.push(state);
	}

	public void popState() {
		stateStack.pop();
	}

}
