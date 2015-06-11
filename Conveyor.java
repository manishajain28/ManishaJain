package com.manisha.assignment;

import java.util.Set;

public class Conveyor {
private Set<Gate> gates;
private Set<Belt> belts;

public Conveyor(Set<Gate> gates, Set<Belt> belts) {
  this.gates = gates;
  this.belts = belts;
}

public Set<Gate> getGates() {
  return gates;
}

public Set<Belt> getBelts() {
  return belts;
}

public void setGates(Set<Gate> gates) {
	    this.gates = gates;
	  }

public void setBelts(Set<Belt> belts) {
	  this.belts = belts;
	  }



} 

