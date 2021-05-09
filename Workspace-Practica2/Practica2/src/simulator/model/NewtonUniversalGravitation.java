package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	public double _G;

	// Constructor
	public NewtonUniversalGravitation(double valor) {
		_G = valor;
	}
	
	public void apply(List<Body> bs) {
		double aux;
		for(int i = 0; i < bs.size(); ++i) {
			Vector2D sum = new Vector2D();
			for(int j = 0 ;j < bs.size(); ++j) {
				if(i!=j) {
					aux = (bs.get(i).position.distanceTo(bs.get(j).position)); // aux = |pi -pj|
					if(aux!=0)
						sum = sum.plus(bs.get(j).position.minus(bs.get(i).position).direction().scale(bs.get(j).mass/(aux*aux)));
					    // sum = mj / |pi -pj|*|pi -pj| (pi, pj vectores)
				}
			}
			bs.get(i).addForce(sum.scale(bs.get(i).mass*_G)); // fij = G*mi*mj / |pi -pj|*|pi -pj| (pi, pj vectores)
		}
	}	
	
	public String toString() {
		return "Newton’s Universal Gravitation with G = " + _G;
	}
}
