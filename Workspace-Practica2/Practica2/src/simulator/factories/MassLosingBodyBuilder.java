package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	public MassLosingBody createTheInstance(JSONObject data) throws IllegalArgumentException {
		if(data.getDouble("m")<0 || data.getJSONArray("p").length() != 2 
				|| data.getJSONArray("v").length() != 2
				|| data.getDouble("freq") <= 0 
				|| data.getDouble("factor") < 0 || data.getDouble("factor") > 1)
			throw new IllegalArgumentException("Invalid arguments");
		// Se comprueba que la masa sea positiva y que p y v sean vectores 2D
		// Se comprueba que la frecuencia sea positiva y que el factor este entre 0 y 1
		
		String id = data.getString("id");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		Vector2D v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
		double m = data.getDouble("m");
		double freq = data.getDouble("freq");
		double fact = data.getDouble("factor");
		
		return new MassLosingBody(id, v, p, m, fact, freq); // Se crea la instancia
	}

	public JSONObject createData() { // // Se devuelve un json con las claves necesarias
		JSONObject json = new JSONObject();
		json.put("id", "The body identifier");
		json.put("p", "The position vector");
		json.put("v", "The velocity vector");
		json.put("m", "The mass value");
		json.put("freq", "The interval of time after which the body losses mass");
		json.put("fact", "The mass lossing factor");
		
		return json;
	}
	
	public String getType() {
		return "mlb";
	}

	public String getDesc() {
		return "Body that loses mass over time";
	}
}