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
		
		JSONObject json = new JSONObject();
		json.put("type", "ng");
		json.put("data", data);
		json.put("desc", "No force");
		
		return json;
	}

	public String getType() {
		return "nf";
	}

	public String getDesc() {
		return "No forces are applied";
	}
}