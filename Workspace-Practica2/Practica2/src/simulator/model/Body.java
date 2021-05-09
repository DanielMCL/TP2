package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	public static final Vector2D INITIAL_FORCE = new Vector2D();
	
	protected String id;
	protected Vector2D velocity;
	protected Vector2D force;
	protected Vector2D position;
	protected double mass;
	
	//Constructor normal
	public Body(String id, Vector2D velocity, Vector2D position, double mass){
		this.id = id;
		this.velocity = velocity;
		this.force = INITIAL_FORCE;
		this.position = position;
		this.mass = mass;
	}
	// Getters
	
	public String getId() {
		return id;
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
	
	public Vector2D getForce() {
		return force;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public double getMass() {
		return mass;
	}
	
	void addForce(Vector2D f) {
		force = force.plus(f);
	}
	
	void resetForce() {
		force = INITIAL_FORCE;
	}
	
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo
		Vector2D acceleration;
		if(mass != 0) {
			acceleration = force.scale(1/mass); // a = f/m
		}else {
			acceleration = new Vector2D();
		}
		position = position.plus(velocity.scale(t).plus(acceleration.scale(t*t/2))); // p = p + vt + 1/2 * a*t*t
		velocity = velocity.plus(acceleration.scale(t)); // v = vt
	}
	
	public JSONObject getState() { // A�adimos todos los atributos del cuerpo al json
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("m", mass);
		json.put("p", position.asJSONArray());
		json.put("v", velocity.asJSONArray());
		json.put("f", force.asJSONArray());
		
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}