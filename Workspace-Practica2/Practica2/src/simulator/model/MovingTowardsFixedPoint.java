package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	public double _g;
	public Vector2D _c;
	
	// Constructor
	public MovingTowardsFixedPoint(double g, Vector2D c) {
		_g = g;
		_c = c; 
	}
	
	public void apply(List<Body> bs) {
		for (int i = 0; i < bs.size(); i++) {
			bs.get(i).addForce(bs.get(i).getPosition().minus(_c).direction().scale(bs.get(i).getMass()*-_g)); 
			// Fi = mi * (-g*di) (mi, di vectores)
		} 
	}
	
	public String toString() {
		return "Moving towards " + _c + " with constant acceleration " + "_g";
	}
}