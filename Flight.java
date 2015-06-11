package com.manisha.assignment;

public class Flight  {
	private final String id; 
	private final Gate exitGate;
	private final String destination;
	private final String arrivalTime;

	public Flight(String id, Gate exitGate, String destination, String arrivalTime){
		this.id = id;
		this.exitGate = exitGate;
		this.destination = destination;
		this.arrivalTime = arrivalTime;
	}

	public String getId() {
		return id;
	}
	public String getDestination() {
		return destination;
	}

	public Gate getExitGate() {
		return exitGate;
	}
	public String getArrivaltime() {
		return arrivalTime;
	}
} 
