# FoodTruck
This is a very simple command-line program that will print out a list of food trucks, given a source of food truck data from the San Francisco government’s API.

## Dependency

Java version used : openjdk version "1.8.0_252"

A very small JSON library ORG.JSON was used.  The library jar file is located at "/lib/json-20200518.jar" <br/>
The original download https://mvnrepository.com/artifact/org.json/json  The github for ORG.JSON https://github.com/stleary/JSON-java

## Build

Once git cloned, just compile with the following command
```
javac -cp ".:./lib/json-20200518.jar" src/*.java
```

## Run 

```
java -cp ".:./lib/json-20200518.jar:./src" FoodTruckFinder
```
