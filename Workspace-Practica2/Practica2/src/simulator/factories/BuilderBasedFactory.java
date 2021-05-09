package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> builderList;
	private List<JSONObject> infoList;

	public BuilderBasedFactory(List<Builder<T>> builders) { // Constructor
		this.builderList = builders; // Se recibe una lista de builders
		infoList = new ArrayList<>(); // Se cre una lista de info
		for (Builder<T> b : builderList) { // Se anyade a la lista la info de cada builder
			infoList.add(b.getBuilderInfo());
		}
	}

	public T createInstance(JSONObject info) throws IllegalArgumentException {
		for (Builder<T> b : builderList) { // Se busca cual es el objeto del que se quiere crear una instancia y se
											// devuelve null si no hay ninguno
			if (b.createInstance(info) != null)
				return b.createInstance(info);
		}
		throw new IllegalArgumentException("Object not found!");
	}

	public List<JSONObject> getInfo() { // Se devuelve una lista no modificable con toda la informacion de todos los
										// builders
		return Collections.unmodifiableList(infoList);
	}
}