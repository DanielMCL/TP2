package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	public ForceLaws createTheInstance(JSONObject data) {
		return new NoForce(); // Se crea la instancia
	}

	public JSONObject createData() { // Se devuelve un json con las claves necesarias (ninguna)
		JSONObject data = new JSONObject();
		
		
		return data;
	}

	public String getType() {
		return "nf";
	}

	public String getDesc() {
		return "No force";
	}
}