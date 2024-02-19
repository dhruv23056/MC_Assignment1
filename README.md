# MC_Assignment1
1. MainActivity:
The main entry point of the app.
Sets up the Compose content using setContent and applies the theme.
2. Stop Data:
The app defines a Stop data class to represent each stop in the journey, containing the stop's name and distance in kilometers.
3. Journey Data:
Two lists of Stop objects (stops and stops5) represent different journeys. The app allows switching between these journeys.
4. startApp() Composable:
The main Composable function that defines the UI structure.
Uses Jetpack Compose components such as Column, Button, Text, LinearProgressIndicator, and LazyColumn to create the UI layout.
5. Buttons:
The app has three buttons:
Load Data: Switches between different journeys (stops and stops5).
Next Station: Increments the current station index, updating the displayed information.
Convert Unit: Toggles between distance units (Kilometers and Miles).
6. Text Views:
Display information about the current stop, total distance covered, and total distance left.
The LinearProgressIndicator visually represents the progress of the journey.
7. LazyColumn:
Displays a list of stops, each represented by a Card with the stop's name and formatted distance.
The color of each stop's text changes based on whether the stop has been reached or not.
8. Helper Functions:
formatDistance: Formats the distance based on the chosen distance unit.
calculateTotalDistanceCovered: Calculates the total distance covered up to the current stop.
calculateRemainingDistance: Calculates the remaining distance to the last stop.
calculateTotalDistance: Calculates the total distance of the entire journey.
calculateProgress: Calculates the progress percentage of the journey.
9. DistanceUnit Enum:
Represents the available distance units: Kilometers and Miles.
10. Mutable State Variables:
Uses mutableStateOf to manage and observe the state changes in variables like distanceUnit, currentStopIndex, totalDistanceCovered, and stopsData.
11. Theming:
Applies a basic theme using MaterialTheme.
12. Color Usage:
Colors are used to differentiate between different UI elements, and the text color changes based on the station's status (reached or upcoming).
13. Interaction Flow:
Users can load different journey data, navigate to the next station, and switch distance units interactively.
