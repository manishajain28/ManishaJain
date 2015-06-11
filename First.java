package com.manisha.assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class First {

    public static void main(String args[]) throws IOException
    {
    	try {
    		ReadFileClass readFile = new ReadFileClass();
    		Airport airport = readFile.populateAirportObjFromFileInput();

    		List<String> listOfPathString = new ArrayList<String>();
    		SingleSourceShortestPathAlgo real = new SingleSourceShortestPathAlgo(airport.getConveyor());
    		Map<String,Map<Gate,Gate>> mapOfEntryAndExitGatesForBag = BagUtils.getMapOfEntryAndExitGatesForBag(airport.getBagsList(), airport.getFlightsList());
    		for(String bagNum : mapOfEntryAndExitGatesForBag.keySet()) {
    			Map<Gate,Gate> entryExitForBag = mapOfEntryAndExitGatesForBag.get(bagNum);
    			String pathString = "";
    			for(Gate src : entryExitForBag.keySet()) {
    				Gate dest = entryExitForBag.get(src);
    				if(src == null || dest == null) {
    					pathString = PathConstants.INVALID_GATE;
    				} else if(src.equals(dest)) {
    					pathString = src.getName() + " " + src.getName() + " : " + "0";
    				} else {
    					pathString = real.findShortestPathFromSourceToDest(src, dest);
    				}    				
    			}
    			listOfPathString.add(bagNum + " " + pathString);
    		}

    		WriteToFileClass writeFile = new WriteToFileClass();
    		writeFile.writeOutputToFile(listOfPathString);
    		
    	} catch(IOException io) {
    		List<String> errString = new ArrayList<String>();
    		errString.add(io.getMessage());
    		WriteToFileClass writeFile = new WriteToFileClass();
    		writeFile.writeOutputToFile(errString);
    		io.printStackTrace();
    	} catch(Exception e) {
    		List<String> errString = new ArrayList<String>();
    		if(e.getMessage() != null)
    			errString.add(e.getMessage());
    		else 
    			errString.add(PathConstants.NULL_POINTER_EXCEPTION);
    		WriteToFileClass writeFile = new WriteToFileClass();
    		writeFile.writeOutputToFile(errString);
    		e.printStackTrace();
    	}
    }    
}


