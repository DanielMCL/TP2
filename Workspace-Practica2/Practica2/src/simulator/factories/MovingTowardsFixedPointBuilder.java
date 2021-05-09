package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	public static final double DEFAULT_G = 9.81; 
	public static final Vector2D DEFAULT_ORIGIN = new Vector2D();
	
	public ForceLaws createTheInstance(JSONObject data) throws IllegalArgumentException {
		double g = DEFAULT_G;
		Vector2D c = DEFAULT_ORIGIN;
		if(data.has("g")) {
			g = data.getDouble("g");
			if (g < 0) throw new IllegalArgumentException("Invalid arguments");
		} // Se comprueba si se pasa valor de la aceleracion y este es positivo
		
		if(data.has("c")) {
			if (data.getJSONArray("c").length() != 2) throw new IllegalArgumentException("Invalid arguments");
			c = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
		} // Se comprueba si se pasa un punto de origen y este es positivo
		return new MovingTowardsFixedPoint(g, c); // Se crea la instancia
	}

	public JSONObject createData() { // Se devuelve un json con las claves necesarias
		JSONObject data = new JSONObject();
		data.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
		data.put("g", "the length of the acceleration vector (a number)");
		
		JSONObject json = new JSONObject();
		json.put("type", "mtfp");
		json.put("data", data);
		json.put("desc", "Moving towards a fixed point");
		
		return json;
	}
	
	public String getType() {
		return "mtfp";
	}

	public String getDesc() {
		return "Gravity pull towards one point";
	}
}