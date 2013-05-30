package ai.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {
	private List<OpenState> closedStates;
	private List<OpenState> openStates;
	
	private State destinationState;
	private State startingState;
	
	public Path findPath(State startingState, State destinationState) {
		this.reset();
		this.destinationState = destinationState;
		this.startingState = startingState;
		return this.performAStarSearch();
	}

	private Path performAStarSearch() {
		this.addStartingStateToOpenList();
		while(!this.openStates.isEmpty()) {
			OpenState currentState = this.getOpenStateWithLowestHeuristic();
			this.closedStates.add(currentState);
			if(goalHasBeenReached(currentState)) {
				return generateSearchPath(currentState);
			} else {				
				this.visitCurrentState(currentState);
			}
		}
		return generateSearchedFailedPath();
	}

	private void visitCurrentState(OpenState currentState) {
		List<State> successorStates = currentState.state.getSuccessors();
		for(State successorState : successorStates) {
			if(ManualCollections.contains(this.closedStates, successorState)) continue;
			if(ManualCollections.contains(this.openStates, successorState)) {
				OpenState successorOpenState = this.openStates.get(this.openStates.indexOf(successorState));
				this.relaxNodeCost(currentState, successorOpenState);
			} else {
				this.addSuccessorToOpenStateList(currentState, successorState);
			}
		}
	}

	private void relaxNodeCost(OpenState currentState, OpenState successorState) {
		double distanceToSuccessor = calculateTotalDistanceToNextState(currentState, successorState.state);
		if(distanceToSuccessor < successorState.distanceFromStart) {
			successorState.distanceFromStart = distanceToSuccessor;
			successorState.fValue = successorState.distanceFromStart + successorState.state.estimateHeuristicTo(this.destinationState);
			successorState.previousState = currentState;
		}
	}

	private void addSuccessorToOpenStateList(OpenState currentState, State successorState) {
		OpenState newOpenState = this.createSuccessorOpenState(currentState, successorState);
		this.openStates.add(newOpenState);
	}

	private OpenState createSuccessorOpenState(OpenState currentState, State successorState) {
		OpenState newOpenState = new OpenState(successorState);
		newOpenState.previousState = currentState;
		newOpenState.distanceFromStart = currentState.distanceFromStart + currentState.state.getDistanceToSuccessor(successorState);
		newOpenState.fValue = newOpenState.distanceFromStart + newOpenState.state.estimateHeuristicTo(this.destinationState);
		return newOpenState;
	}
	
	private double calculateTotalDistanceToNextState(OpenState currentState, State successorState) {
		return currentState.distanceFromStart + currentState.state.getDistanceToSuccessor(successorState);
	}

	private void addStartingStateToOpenList() {
		OpenState openStartingState = new OpenState(startingState);
		openStartingState.distanceFromStart = 0;
		openStartingState.fValue = startingState.estimateHeuristicTo(destinationState);
		this.openStates.add(openStartingState);
	}
	
	private boolean goalHasBeenReached(OpenState currentState) {
		return currentState.state.equals(this.destinationState);
	}
	
	private Path generateSearchPath(OpenState finalState) {
		ArrayList<State> path = new ArrayList<State>();
		OpenState currentState = finalState;
		while(!currentState.equals(this.startingState)){
			path.add(0, currentState.state);
			currentState = currentState.previousState;
		}
		return new Path(path);
	}

	private OpenState getOpenStateWithLowestHeuristic() {
		OpenState nextState = ManualCollections.min(this.openStates);
		this.openStates.remove(nextState);
		return nextState;
	}
	
	private Path generateSearchedFailedPath() {
		ArrayList<State> stateList = new ArrayList<State>();
		stateList.add(this.startingState);
		return new Path(stateList);
	}

	private void reset() {
		this.openStates = new ArrayList<OpenState>();
		this.closedStates = new ArrayList<OpenState>();
	}
}
