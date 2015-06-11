package com.manisha.assignment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.manisha.assignment.FibonacciHeap.Entry;
import com.manisha.assignment.*;


public class SingleSourceShortestPathAlgo {
	private final Map<Gate,Set<Belt>> mapOfGateToConnectedBelts;
	private final Map<Gate,Map<Gate,Entry<PathInfo>>> sourceGatesToDestPathInfoMap;
	
	private Map<Gate,Entry<PathInfo>> destinationToPathInfoMap;
	private Set<Gate> visitedGates;
	private FibonacciHeap<PathInfo> heap;   // use fibonacciHeap as it takes minimum time i.e. O(1) to insert, delete the elements 
	private Set<Gate> setOfGates;		// its needed to check whether the gates are visited or not
	
	public SingleSourceShortestPathAlgo(Conveyor conveyer) {
		this.sourceGatesToDestPathInfoMap = new HashMap<Gate,Map<Gate,Entry<PathInfo>>>();
		this.setOfGates = conveyer.getGates();
		this.mapOfGateToConnectedBelts = ConveyorUtils.getMapOfGateToConnectedBelts(conveyer.getBelts());
	}

	
	public String findShortestPathFromSourceToDest(Gate source, Gate dest) {	
		String pathString = getRouteAndDistanceFromSorceToDest(source,dest);
		if(pathString == null) {
			pathString = PathConstants.PATH_NOT_FOUND;
		}
		return pathString;
	}
	
	private String getRouteAndDistanceFromSorceToDest(Gate source, Gate dest) {
		if(sourceGatesToDestPathInfoMap.get(source) == null) {
			getRouteAndDistFromSorceToAllGates(source);
		}
		Entry<PathInfo> path = sourceGatesToDestPathInfoMap.get(source).get(dest);
		List<Gate> fullPath = getShortestPathTo(source, dest);
		String pathString = "";
		for(Gate gate : fullPath) {
			pathString += " " + gate.getName();
		}
		pathString += " : " + (int)path.getPriority();
		return pathString;
	}
	
	private List<Gate> getShortestPathTo(Gate src, Gate dest) {
		Entry<PathInfo> pathInfo = sourceGatesToDestPathInfoMap.get(src).get(dest);
		List<Gate> listOfGates = new ArrayList<Gate>();
		listOfGates.add(dest);
        Gate g = pathInfo.getValue().getPrevGate();
        while(!g.equals(src) && g!= null) {
        	listOfGates.add(g);
        	pathInfo = sourceGatesToDestPathInfoMap.get(src).get(g);
        	g = pathInfo.getValue().getPrevGate();
        }
        listOfGates.add(src);
        Collections.reverse(listOfGates);
        return listOfGates;
	}
	
	
	
	private void getRouteAndDistFromSorceToAllGates(Gate source) {
		getDirectPathInfoFromSource(source);
		findShortestRouteFromSourceToAllGates(source);
		sourceGatesToDestPathInfoMap.put(source, destinationToPathInfoMap);		
	}
	
	private void getDirectPathInfoFromSource(Gate source) {
		destinationToPathInfoMap = new HashMap<Gate,Entry<PathInfo>>();
		visitedGates = new HashSet<Gate>();
		heap = new FibonacciHeap<PathInfo>();
		
		for(Belt belt : mapOfGateToConnectedBelts.get(source)) {
			PathInfo path = new PathInfo(belt.getDestinationForSrc(source),source);
			Entry<PathInfo> e = new Entry<PathInfo>(path,belt.getTime());
			destinationToPathInfoMap.put(belt.getDestinationForSrc(source), e);
			heap.enqueue(path, belt.getTime());
		}
		visitedGates.add(source);
	}
	
	private void findShortestRouteFromSourceToAllGates(Gate source) {
		while (!(areAllGatesVisited() || heap.isEmpty())) {
			Entry<PathInfo> path = heap.dequeueMin();
			Gate currentGate = path.getValue().getDestGate();
			
			if(!isGateVisited(currentGate)) {
				visitedGates.add(currentGate);
				findPathForUnvisitedGates(currentGate, path);
			}
		}
	}
	
	private boolean areAllGatesVisited() {
		return visitedGates.containsAll(setOfGates);
	}
	
	private boolean isGateVisited(Gate gate) {
		return visitedGates.contains(gate);
	}
	
	private void findPathForUnvisitedGates(Gate currentGate, Entry<PathInfo> currentPath) {
		if(mapOfGateToConnectedBelts.get(currentGate) != null) {
			for(Belt belt : mapOfGateToConnectedBelts.get(currentGate)) {
				Gate adjacentGate = belt.getDestinationForSrc(currentGate);

				if(!isGateVisited(adjacentGate)) {
					Entry<PathInfo> oldPathInfoEntry = destinationToPathInfoMap.get(adjacentGate);
					int newPathLength = (int) (currentPath.getPriority() + belt.getTime());
					if(oldPathInfoEntry == null || oldPathInfoEntry.getPriority() > newPathLength) {
						PathInfo newPath = PathInfo.getNewPath(currentPath.getValue(),belt);
						Entry<PathInfo> e = new Entry<PathInfo>(newPath, newPathLength);
						destinationToPathInfoMap.put(adjacentGate, e);
						heap.enqueue(newPath, newPathLength);
					}
				}
			}
		}
	}

}

