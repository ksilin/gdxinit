package info.silin.gdxinit.entity.state;

import java.util.ArrayList;

import info.silin.gdxinit.entity.Entity;

public class StateMachine {

	private Entity owner;

	private ArrayList<State> globalStates = new ArrayList<State>();

	private State lastState;
	private State currentState;

	public StateMachine() {
		currentState = Idle.getINSTANCE();
	}

	public void setState(State newState) {

		if (currentState.equals(newState))
			return;

		currentState.exit(owner);
		lastState = currentState;
		currentState = newState;
		currentState.enter(owner);
	}

	public boolean isState(State state) {
		return state.equals(currentState);
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public void update(float delta) {
		currentState.execute(owner, delta);
		for (State s : globalStates) {
			s.execute(owner, delta);
		}
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public void addGlobalState(State state) {
		if (globalStates.add(state)) {
			state.enter(owner);
		}
	}

	public void removeGlobalState(State state) {
		if (globalStates.remove(state)) {
			state.exit(owner);
		}
	}

	public ArrayList<State> getGlobalStates() {
		return globalStates;
	}
}
