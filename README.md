# ManishaJain
Barclays_Assignment
"First.java" is the first file of the project where "main()" function is present.
My project is reading an input from the file namely "InputDataFile.txt" and populate the objects accordingly.
"WriteToFileClass.java" is writing an output to the file namely "OutputDataFile.txt".
"PathConstants" is the file to store all the constants.
Create objects namely "Airport", "Conveyor", "Flight" and "Bag" where Conveyor is the set of "Gate" and "Belt" objects.
Calculated shortest path from Dijkstra's algorithm.
Dijkstra's algo is implemented through "Fibonacci Heap" as it takes only O(1) time to insert and delete the elements.
Storing "PathInfo" object in Heap which has only 2 fields "destinationGate" and "previousGate".
For storing distance, I am using the field "priority" of the Heap. So no need to store the distance separately in PathInfo. 
