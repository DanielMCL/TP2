package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.model.Body;
import simulator.model.PhysicsSimulator;
import simulator.exceptions.CompareStatesException;
import simulator.factories.*;

public class Controller {
	private PhysicsSimulator _simulator;
	private Factory<Body> _factory;
	
	public Controller(Factory<Body> factory, PhysicsSimulator simulator) { // Constructor
		_simulator = simulator; 
		_factory = factory;
	}
	
	public void loadBodies(InputStream in) { // Creamos el jsn a partir de un fichero de entrada
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for(int i = 0; i < bodies.length(); ++i) { // Añadimos todos los cuerpos al simulador mediante la factoria
			_simulator.addBody(_factory.createInstance(bodies.getJSONObject(i)));
		}
	}
	
	public void run(int steps, OutputStream out, InputStream expOut, StateComparator cmp) throws CompareStatesException{
		JSONObject expected = null;
		if(expOut != null)
			expected = new JSONObject(new JSONTokener(expOut)); // Creamos el json a partir de expOut
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		p.println(_simulator); // Imprimimos por pantalla lo que ocurre en el simulador
		if(expected != null &&  // Se comprueba que se haya cargado bien el fichero y que las variables del simulador coinciden entre ls ficheros
				!cmp.equal(expected.getJSONArray("states").getJSONObject(0), _simulator.getState())) {
			throw new CompareStatesException("Expected output not matched");
		}
		for (int i = 1; i <= steps; ++i) {
			_simulator.advance(); // Avanzamos un ciclo en el simulador
			p.println("," + _simulator); // Imprimimos por pantalla lo que ocurre en el simulador en cada paso
			if(expected != null && !cmp.equal(expected.getJSONArray("states").getJSONObject(i), _simulator.getState())) { // // Se comprueba que se haya cargado bien el fichero y que las variables del simulador coinciden entre los ficheros en cada paso
				throw new CompareStatesException("Expected output not matched");
			}
		}
		p.println("]");
		p.println("}");
	}
}
