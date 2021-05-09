package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {
	public StateComparator createTheInstance(JSONObject data) {
		return new MassEqualStates(); // Se crea la instancia
	}
	
	public JSONObject createData() { // Se devuelve un json con las claves necesarias (ninguna)
		return new JSONObject();
	}

	public String getType() {
		return "masseq";
	}

	public String getDesc() {
		return "Mass comparator";
	}
}