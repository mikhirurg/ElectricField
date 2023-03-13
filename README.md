# ElectricField

[![Build](https://github.com/mikhirurg/ElectricField/actions/workflows/maven.yml/badge.svg)](https://github.com/mikhirurg/ElectricField/actions/workflows/maven.yml)

## Project description
ElectricField is an interactive physics demonstration environment that allows the simulation of the lines of force produced by two charged particles.

## Installation guide
The project requires Java version 13+ in order to run the simulation. 
There are a few different ways how a user can obtain the jar file to execute the project:
- The latest stable version of the project is available in the [Releases](https://github.com/mikhirurg/ElectricField/releases) section of the repository
- The user can build the executable jar from sources by using the script ```build.sh```

After obtaining the executable jar, user can just run the following command:

```
java -jar ElectricField.jar
```

## How does the simulation work
The project objectives:
- Calculation of the equations of force lines for a charged particle 
- Calculation of trajectories of lines of force for two charged particles 

### Algorithm for constructing electrostatic field lines:
In order to calculate and simulate the lines of force I was using an Euler method to solve the system of differential equations.
The main idea of the algorithm is quite simple: for every line of force we are choosing the starting point (in the current implementation the starting points are evenly distributed over the circle which is used as a particle graphical model).
In the starting point we are calculating the the projections of electrostatic field intensity vector. After that, we are calculating the next point: we will obtain the position of this next point by moving the current considering point by electric field vector at the current point. So, step by step the algorithm will continue calculating the points and constructing the segments of the line of force. The stopping criterion for the algorithm is quite dependent on the particular implementation, in the case of the "ElectricField" project the algorithm stops rendering the line at the point when the line length reaches some constant.

#### The code which is calculating the segment of the line of force 

```java
double Ex = (q1 * (xi0 - e1.x) / (r1 * r1 * r1) + q2 * (xi0 - e2.x) / (r2 * r2 * r2));
double Ey = (q1 * (yi0 - e1.y) / (r1 * r1 * r1) + q2 * (yi0 - e2.y) / (r2 * r2 * r2));

double E = Math.sqrt(Ex * Ex + Ey * Ey);

double dx = dl * Ex / E;
double dy = dl * Ey / E;

g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + dx), (int) getScrY(yi0 + dy));
```

## How to use the project

On the left side of the application window, there is a big simulation area where are charged particles located and where the lines of force are displayed.
In this simulation area user can drag particles with a mouse and the lines of force will be dynamically re-render.

And, on the left side there are three panels that are used to control the simulation:
- The particles panel
  This panel is used to set the coordinates of the particles (cm), set the charges values of particles (nc) and set the number of the lines of force.
  Also user can reset the particles by pressing the "Reset" button.

- The information panel
  Here user can see the value of the voltage and potential value at some point in the electric field. In order to get all this information for the particular point, the user can click the right mouse button in the desired location of the electric field. 
  
- The graphs panel
  Using the controls of this panel users can access the graphs of dependence between voltage and the coordinate x, and the graph of the dependence between potential and the coordinate x.

![Screenshot](/images/screen.png)

## Examples

|Particle 1       | Particle 2            | Lines of force                                        |
|-----------------|-----------------------|-------------------------------------------------------|
|X=-10, Y=0, Q=-10| X=10, Y=0, Q=10       | <img src="/images/demo_1.png" alt="Demo 1" width=200> |
|-----------------|-----------------------|-------------------------------------------------------|
|X=-10, Y=0, Q=10 | X=10, Y=0, Q=10       | <img src="/images/demo_2.png" alt="Demo 2" width=200> |
|-----------------|-----------------------|-------------------------------------------------------|
|X=-10, Y=0, Q=10 | X=10, Y=0, Q=-20      | <img src="/images/demo_3.png" alt="Demo 3" width=200> |
|-----------------|-----------------------|-------------------------------------------------------|
|X=0, Y=0, Q=10   | X=10, Y=0, Q=0        | <img src="/images/demo_4.png" alt="Demo 4" width=200> |

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](/LICENSE.txt)

