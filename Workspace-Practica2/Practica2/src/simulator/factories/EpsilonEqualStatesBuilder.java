package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{
	public static final double DEFAULT_EPS = 0.0;
	public StateComparator createTheInstance(JSONObject data) {
		double eps = DEFAULT_EPS;
		if (data.has("eps")) {
			eps = data.getDouble("eps");
			if (eps < 0) throw new IllegalArgumentException("Invalid arguments");
		} // Se comprueba si se pasa un valor de epsiolon y si este es positivo
		return new EpsilonEqualStates(eps); // Se crea la instancia
	}

	public JSONObject createData() { // Se devuelve un json con las claves necesarias
		JSONObject json = new JSONObject();
		json.put("eps", "The value of epsilon (Optional)");
		return json;
	}

	public String getType() {
		return "epseq";
	}

	public String getDesc() {
		return "Epsilon comparator";
	}
}