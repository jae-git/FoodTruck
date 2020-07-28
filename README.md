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

The main program "FoodTruckFinder" has two different implementations.  The first one calls Socrata API for each pagination.  
```
	DirectAPICallPresenter presenter = new DirectAPICallPresenter();

	presenter.interactivePresentation();
```

The second one
