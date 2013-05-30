package ai.astar;

public class OpenState implements Comparable<OpenState> {
	public final State state;
	public double distanceFromStart;
	public double fValue;
	public OpenState previousState;

	public OpenState(State state) {
		this.state = state;
		this.distanceFromStart = Double.MAX_VALUE;
		this.previousState = null;
	}

	public int compareTo(OpenState otherState) {
		if(this.fValue < otherState.fValue) return -1;
		if(this.fValue > otherState.fValue) return 1;
		return 0;
	}
	
	public boolean equals(Object otherState) {
		if(!(otherState instanceof OpenState)) return false;
		//abusing a little java quirk: if the first part of a boolean expression is false the second won't be evaluated.
		if((otherState instanceof State) && (((State)otherState).equals(this.state))) return true;
		return ((OpenState)otherState).state.equals(this.state);
	}
}