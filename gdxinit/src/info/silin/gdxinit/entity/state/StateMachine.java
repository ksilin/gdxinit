package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.entity.Entity;

public class StateMachine {

	private Entity owner;

	private State lastState;
	private State currentState;

	public StateMachine() {
		currentState = new Idle();
	}

	public void setState(State newState) {
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

	public void update() {
		currentState.execute(owner);
	}
}
