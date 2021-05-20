package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	public static final double G = 6.67E-11;
	
	public ForceLaws createTheInstance(JSONObject data) {
		double g = G;
		if(data.has("G")) {
			g = data.getDouble("G");
			if (g < 0 ) throw new IllegalArgumentException("Invalid arguments");
		} // Se comprueba si se pasa el valor de la constante y este es positivo
		return new NewtonUniversalGravitation(g); // Se crea la instancia
	}

	public JSONObject createData() { // Se devuelve un json con las claves necesarias
		JSONObject data = new JSONObject();
		data.put("G", "Universal gravitation constant (Optional)");

		return data;
	}
	
	public String getType() {
		return "nlug";
	}

	public String getDesc() {
		return "Newton's law of universal gravitation";
	}
}