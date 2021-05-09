package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double stepTime;
	private ForceLaws forceLaw;
	private List<Body> bodyList;
	private double currentTime;
	
	public PhysicsSimulator(double t, ForceLaws f) throws IllegalArgumentException{ 
		if(t > 0) // Solo se permite que se pase un tiempo positivo
			stepTime = t;
		else
			throw new IllegalArgumentException("Invalid time value");
		if(f != null) // La fuerza tiene que haberse pasado
			forceLaw = f;
		else
			throw new IllegalArgumentException("ForceLaws not initialized");
		
		currentTime = 0;
		bodyList = new ArrayList<Body>();
	}
	
	public void advance() { // Aplica un paso de simulacion
		for (Body body: bodyList) {
			body.resetForce();
		}
		forceLaw.apply(bodyList);
		for (Body body: bodyList) {
			body.move(stepTime);
		}
		currentTime += stepTime;
	}
	
	public void addBody(Body b) throws IllegalArgumentException{ // A�ade el cuerpo b al simulador
		for (Body body: bodyList) {
			if(body.getId()==b.getId()) {//CAMBIAR a equals
				throw new IllegalArgumentException("Duplicated body ID");
			}
		}
		bodyList.add(b);
	}
	
	public JSONObject getState() { // A�ade los atributos que indican el estado del simulador a un json
		JSONObject json = new JSONObject();
		json.put("time", currentTime);
		
		JSONArray jsons = new JSONArray();
		for (Body body: bodyList) {
			jsons.put(body.getState());
		}		
		json.put("bodies", jsons);
		
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public void reset() {
		currentTime = 0.0;
		bodyList.clear();
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException{
		if(dt <= 0) throw new IllegalArgumentException("Delta time must be grater than 0");
		stepTime = dt;
	}
	
	public void setForceLawsLaws(ForceLaws forceLaws) throws IllegalArgumentException{
		if(forceLaws == null) throw new IllegalArgumentException("Null forceLaw received");
		forceLaw = forceLaws;
	}
}