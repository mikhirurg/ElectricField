# ElectricityField

[![Build](https://github.com/mikhirurg/ElectricityField/actions/workflows/maven.yml/badge.svg)](https://github.com/mikhirurg/ElectricityField/actions/workflows/maven.yml)

## Project description
ElectricityField is an interactive physics demonstration environment that allows the simulation of the lines of force produced by two charged particles.

## Installation guide
The project requires Java version 13+ in order to run the simulation. 
There are a few different ways how a user can obtain the jar file to execute the project:
- The latest stable version of the project is available in the [Releases](https://github.com/mikhirurg/ElectricityField/releases) section of the repository
- The user can build the executable jar from sources by using the script ```build.sh```

After obtaining the executable jar, user can just run the following command:

```
java -jar ElectricityField.jar
```

## How does the simulation work
The project objectives:
- Calculation of the equations of force lines for a charged particle 
- Calculation of trajectories of lines of force for two charged particles 

### Algorithm for constructing electrostatic field lines:
In order to calculate and simulate the lines of force I was using an Euler method to solve the system of differential equations.
The main idea of the algorithm is quite simple: for every line of force we are choosing the starting point (in the current implementation the starting points are evenly distributed over the circle which is used as a particle graphical model).
In the starting point we are calculating the the projections of electrostatic field intensity vector 

## How to use the project

![Screenshot](/images/screen.png)

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](/LICENSE.txt)

