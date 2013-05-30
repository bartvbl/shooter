package ai.astar;

import java.util.List;


public class ManualCollections {
	public static boolean contains(List<OpenState> openStates, State state) {
		for(OpenState currentState : openStates) {
			if(state.equals(currentState)) return true;
		}
		return false;
	}
	
	public static OpenState min(List<OpenState> states) {
		int index = -1;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < states.size(); i++) {
			OpenState state = states.get(i);
			if(state.fValue < min) {
				index = i;
			}
		}
		if(index == -1) return null;
		return states.get(index);
	}
}
