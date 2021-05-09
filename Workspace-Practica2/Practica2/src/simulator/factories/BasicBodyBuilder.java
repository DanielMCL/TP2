package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	public Body createTheInstance(JSONObject data) throws IllegalArgumentException {
		if(data.getDouble("m")<0 || data.getJSONArray("p").length() != 2 
				|| data.getJSONArray("v").length() != 2) 
			throw new IllegalArgumentException("Invalid arguments");
		// Se comprueba que la masa sea positiva y que p y v sean vectores 2D
		String id = data.getString("id");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		Vector2D v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
		double m = data.getDouble("m");
		
		return new Body(id, v, p, m); // Se crea la instancia
	}

	public JSONObject createData() { // Se devuelve un json con las claves necesarias
		JSONObject json = new JSONObject();
		json.put("id", "The body identifier");
		json.put("p", "The position vector");
		json.put("v", "The velocity vector");
		json.put("m", "The mass value");
		
		return json;
		
	}

	public String getType() {
		return "basic";
	}

	public String getDesc() {
		return "Basic body type";
	}
}