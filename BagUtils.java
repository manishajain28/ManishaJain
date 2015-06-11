package com.manisha.assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class BagUtils {

	public static Map<String,Map<Gate,Gate>> getMapOfEntryAndExitGatesForBag(Set<Bag> setOfBags, Set<Flight> setOfFlights) {
		
		Map<String,Map<Gate,Gate>> mapOfEntryAndExitGatesForBag = new TreeMap<String,Map<Gate,Gate>>(); // we use TreeMap to keep the Map entries in sorted order acc to bagNum
		for(Bag b: setOfBags) {
			for(Flight f : setOfFlights) {
				if(b.getFlightId().equals(f.getId())) {
					Map<Gate,Gate> entryExitForBag = new HashMap<Gate,Gate>();
					entryExitForBag.put(b.getEntryPt(), f.getExitGate());
					mapOfEntryAndExitGatesForBag.put(b.getNum(), entryExitForBag);
				}
				if(b.getFlightId().equals("ARRIVAL")) {                // because when the flight Id is "ARRIVAL", exit gate for the bag would be "BaggageClaim"
					Map<Gate,Gate> entryExitForBag = new HashMap<Gate,Gate>();
					entryExitForBag.put(b.getEntryPt(), new Gate("BaggageClaim"));
					mapOfEntryAndExitGatesForBag.put(b.getNum(), entryExitForBag);
				}
			}		
	}
		return mapOfEntryAndExitGatesForBag;
	}
}

