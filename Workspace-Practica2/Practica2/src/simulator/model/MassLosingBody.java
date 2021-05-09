package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body{
	
	private double lossFactor;
	private double lossFrequency;
	private double counter;

	// Constructor
	public MassLosingBody(String id, Vector2D velocity, Vector2D position, double mass, double lossFactor, double lossFrequency) {
		super(id, velocity, position, mass);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		counter = 0;
	}
	
	
	public void move(double t) {
		super.move(t);// Mismo move que Body

		counter += t; // Cada lossFrecuency ciclos se pierde lossFactor masa
		if(counter>=lossFrequency) {
			mass = mass * (1-lossFactor);
			counter = 0;
		}
	}
}