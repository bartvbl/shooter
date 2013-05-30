package ai.astar;

import java.util.List;

public interface State {
	public List<State> getSuccessors();
	public double estimateHeuristicTo(State other);
	public double getDistanceToSuccessor(State successor);
	public boolean equals(State otherState);
}
