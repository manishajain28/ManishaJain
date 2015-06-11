package com.manisha.assignment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ConveyorUtils {

	
	public static Map<Gate,Set<Belt>> getMapOfGateToConnectedBelts(Set<Belt> setOfBelts) {
		
		Map<Gate,Set<Belt>> mapOfEntryGatesForBelts = new HashMap<Gate,Set<Belt>>();
		for(Belt b: setOfBelts) {
			if(mapOfEntryGatesForBelts.containsKey(b.getSource())) {
				mapOfEntryGatesForBelts.get(b.getSource()).add(b);
			} else {
				Set<Belt> belts = new HashSet<Belt>();
				belts.add(b);
				mapOfEntryGatesForBelts.put(b.getSource(), belts);
			}
			if(mapOfEntryGatesForBelts.containsKey(b.getDestination())) { 
				mapOfEntryGatesForBelts.get(b.getDestination()).add(b);				
			} else {
				Set<Belt> belts = new HashSet<Belt>();
				belts.add(b);
				mapOfEntryGatesForBelts.put(b.getDestination(), belts);
			}
		}
		return mapOfEntryGatesForBelts;
	}
}

