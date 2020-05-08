Version 1.0 04/16/2020

High-level Description
-----------------------
This project is an attempt to develop core software for the Weather Vantage Pro2 Integrated Sensor 
Suite manufactured by Davis Instruments. The program is able to generate it's own sample data and
provides an interactive graphical user interface, which changes the contents on the interface
based upon the button operation selected by the user. The submitted version will generate sample
data for a two day period. Once the data is read completely, then the application will stop.

Components Created
-------------------
- Weather Vantage Pro2 Integrated Sensor Suite (ISS)
- Weather Vantage Pro2 Console
- Wireless Weather Envoy
- Random Sensor Data Generator (for data simulation)

Running in Eclipse
-------------------
1. Import the project into the Eclipse IDE
2. Double left-click the "project1" folder (in the Package Explorer on left side)
3. Double left-click the "src" folder
4. Double left-click the "ISS" package
5. Double left-click "Driver.java"
6. Run "Driver.java"

Graphical User Interface
-------------------------
The graph that is initially being plotted on the interface is the average barometric pressure
over the last 24 hours. The general functionality of a button click on the interface is to change
content being displayed on this graph. For example, a button click on the "Wind" button would change
the graphical content from barometric pressure over the last 24 hours to wind speed over the last 24
hours.

Operational Buttons:
- Temp 
- Hum
- Wind
- Bar
- Graph
