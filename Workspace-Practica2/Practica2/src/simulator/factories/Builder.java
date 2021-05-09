package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {

	// Metodos abstractos
	public abstract T createTheInstance(JSONObject data);
	public abstract JSONObject createData();
	public abstract String getType();
	public abstract String getDesc()
;	
	public T createInstance(JSONObject info) throws IllegalArgumentException { 
		if (info.getString("type").equals(getType())) // Comprueba que el tipo coincide con una clase y crea una instancia de esa clase 
			return createTheInstance(info.getJSONObject("data"));
		else return null;
	}
	
	public JSONObject getBuilderInfo() { // Devuelve un json con las claves de cada clase
		JSONObject json = new JSONObject();
		json.put("type", getType());
		json.put("data", createData());
		json.put("desc", getDesc());
		
		return json;
		
	}
}