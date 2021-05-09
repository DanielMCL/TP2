package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class EpsilonEqualStates implements StateComparator{
	private double eps;
	
	public EpsilonEqualStates(double eps) { // Constructor
		this.eps = eps;
	}
	
	public boolean equal(JSONObject s1, JSONObject s2) {
		if (s1.getDouble("time") != s2.getDouble("time")) // Los times han de ser iguales
			return false;
		JSONArray bodies1 = s1.getJSONArray("bodies");
		JSONArray bodies2 = s2.getJSONArray("bodies");
		if(bodies1.length() != bodies2.length()) // Tiene que haber el mismo numero de cuerpos en el json
			return false;
		
		for(int i = 0; i < bodies1.length(); ++i) { // Comprobamos que los atributos coinciden 
			JSONObject body1 = bodies1.getJSONObject(i);
			JSONObject body2 = bodies2.getJSONObject(i);
			if(!body1.get("id").equals(body2.get("id"))) 
				return false;
			if(Math.abs(body1.getInt("m") - body2.getInt("m")) > eps) 
				return false;
			if(new Vector2D(body1.getJSONArray("p").getDouble(0), body1.getJSONArray("p").getDouble(1)).distanceTo(
					new Vector2D(body2.getJSONArray("p").getDouble(0), body2.getJSONArray("p").getDouble(1))) > eps)
				return false;
			if(new Vector2D(body1.getJSONArray("v").getDouble(0), body1.getJSONArray("v").getDouble(1)).distanceTo(
					new Vector2D(body2.getJSONArray("v").getDouble(0), body2.getJSONArray("v").getDouble(1))) > eps)
				return false;
			if(new Vector2D(body1.getJSONArray("f").getDouble(0), body1.getJSONArray("f").getDouble(1)).distanceTo(
					new	Vector2D(body2.getJSONArray("f").getDouble(0), body2.getJSONArray("f").getDouble(1))) > eps)
				return false;
		}
		return true;
	}
}
