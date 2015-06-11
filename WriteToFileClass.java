package com.manisha.assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class WriteToFileClass {

    public void writeOutputToFile(List<String> listOfPathString) throws IOException {
    	File file = new File("Common/OutputDataFile.txt");

    	// if file doesnt exists, then create it
    	if (!file.exists()) {
    		file.createNewFile();
    	}
    	FileWriter fw = new FileWriter(file.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	for(String pathStr : listOfPathString) {
    		bw.write(pathStr);
    		bw.write("\n");
    	}
    	bw.close();
    }
}

