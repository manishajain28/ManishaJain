package com.manisha.assignment;

public class Bag implements Comparable<Bag>{
	private final String num; 
	private final Gate entryPt;
	private final String flightId;

	public Bag(String num, Gate entryPt, String flightId){
		this.num = num;
		this.entryPt = entryPt;
		this.flightId = flightId;
	}

	public String getNum() {
		return num;
	}
	public Gate getEntryPt() {
		return entryPt;
	}

	public String getFlightId() {
		return flightId;
	}

	@Override
	public int compareTo(Bag b) {
		return Integer.compare(Integer.parseInt(this.getNum()), Integer.parseInt(b.getNum()));
	}

} 

