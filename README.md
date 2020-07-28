# FoodTruck
This is a very simple command-line program that will print out a list of food trucks, given a source of food truck data from the San Francisco government’s API.

## Dependency

Java version used : openjdk version "1.8.0_252"

A very small JSON library ORG.JSON was used. It is included in '/lib' folder. [For more information, please the document here](lib/README.md)

## Build

Firstly, git clone the project. <br/>
Once git cloned, just compile with the following command
```
javac -cp ".:./lib/json-20200518.jar" src/*.java
```

## Run 

```
java -cp ".:./lib/json-20200518.jar:./src" FoodTruckFinder
```

![Sample run capture](img/foodtruck.jpg)

## Design considerations
- Food Trucks are sorted alphabetically  
- There are clear comments or self-documenting code  
- Clear naming conventions  
- Concise and readable code  
- Code is broken up into multiple classes and methods based on responsibility  
- Clean output from program  
- Error cases addressed and properly handled  
- Code is testable  
- The Socrata API is properly used  
- Business Logic is not included in the DTOs  
- Think about a solution that can be scaled for bigger application
