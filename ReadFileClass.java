package com.manisha.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReadFileClass {
	static BufferedReader bufferedReader = null;
	static String line = null;			// we make the variable "line" static so that all the functions can access the updated line.
    
    public Airport populateAirportObjFromFileInput() throws IOException {
    	Set<Flight> setOfFlights = new HashSet<Flight>();
    	Set<Bag> setOfBags = new HashSet<Bag>();
    	Conveyor conveyorObj = null;
    	Airport airport = null;
    	try {
    		FileReader fileReader = new FileReader("Common/InputDataFile.txt");
    		bufferedReader = new BufferedReader(fileReader);
    		line = bufferedReader.readLine();
    		while(line!= null) {
    			if(line.startsWith("# Section")) {
    				if(line.contains("Conveyor System")) {
    					conveyorObj = populateConveyorSystemObj();
    				} else if(line.contains("Departures")) {
    					setOfFlights = populateFlightObj();
    				} else if(line.contains("Bags")) {
    					setOfBags = populateBagObj();
    				}
    			} 
    		}
    		airport = new Airport(conveyorObj,setOfFlights,setOfBags);
    		bufferedReader.close(); 
    	} catch (FileNotFoundException e) {
    		List<String> errString = new ArrayList<String>();
    		errString.add(e.getMessage());
    		WriteToFileClass writeFile = new WriteToFileClass();
    		writeFile.writeOutputToFile(errString);
    		e.printStackTrace();
    	} finally {
    		bufferedReader.close();   // to close the bufferedReader also in case of exception
    	}
    	return airport;
    }
    
    private Conveyor populateConveyorSystemObj() throws IOException {
		Set<Gate> gates = new HashSet<Gate>();
		Set<Belt> belts = new HashSet<Belt>();
    	line = bufferedReader.readLine();
		do {        					
			String[] arr = line.split(" ");
			Gate src = new Gate(arr[0]);
			Gate dest = new Gate(arr[1]);
			int time = Integer.parseInt(arr[2]);
			gates.add(src);
			gates.add(dest);
			Belt belt = new Belt(src, dest, time);
			belts.add(belt); 
			line = bufferedReader.readLine();
		}while(line != null && !line.startsWith("# Section"));
		Conveyor conveyerObj = new Conveyor(gates,belts);
		return conveyerObj;
    }
    
    private Set<Flight> populateFlightObj() throws IOException {
    	Set<Flight> setOfFlights = new HashSet<Flight>();
     	line = bufferedReader.readLine();
		do {        					
			String[] arr = line.split(" ");
			String flightId = arr[0];
			Gate exitGate = new Gate(arr[1]);
			String dest = arr[2];
			String arrivalTime = arr[3];
			Flight f = new Flight(flightId,exitGate,dest,arrivalTime);
			setOfFlights.add(f);
			line = bufferedReader.readLine();
		}while(line != null && !line.startsWith("# Section"));
		return setOfFlights;
    }
    
    
    private Set<Bag> populateBagObj() throws IOException {
    	Set<Bag> setOfBags = new HashSet<Bag>();    
    	line = bufferedReader.readLine();
		do {        					
			String[] arr = line.split(" ");
			String bagId = arr[0];
			Gate entryPt = new Gate(arr[1]);
			String flightId = arr[2];
			Bag b = new Bag(bagId,entryPt,flightId);
			setOfBags.add(b);
			line = bufferedReader.readLine();
		}while(line != null && !line.startsWith("# Section"));
		return setOfBags;
    }
    
    
}

