package com.manisha.assignment;

public class Belt  {
	private final Gate source;
	private final Gate destination;
	private final int time;

	public Belt(Gate source, Gate destination, int time){;
	this.source = source;
	this.destination = destination;
	this.time = time;
	}

	public Gate getDestination() {
		return destination;
	}

	public Gate getSource() {
		return source;
	}
	public int getTime() {
		return time;
	}

	public Gate getDestinationForSrc(Gate source) {
		if(this.getSource().equals(source)) {
			return destination;
		}	  
		return this.getSource();
	}


	@Override
	public String toString() {
		return source + " " + destination;
	}


} 
