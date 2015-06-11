package com.manisha.assignment;

import java.util.Set;

public class Airport {
private final Conveyor conveyor;
private final Set<Flight> flightsList;
private final Set<Bag> bagsList;

public Airport(Conveyor conveyor, Set<Flight> flightsList, Set<Bag> bagsList){
    this.conveyor = conveyor;
    this.flightsList = flightsList;
    this.bagsList = bagsList;
  }
  
  public Conveyor getConveyor() {
    return conveyor;
  }
  public Set<Flight> getFlightsList() {
    return flightsList;
  }

  public Set<Bag> getBagsList() {
    return bagsList;
  }
}
