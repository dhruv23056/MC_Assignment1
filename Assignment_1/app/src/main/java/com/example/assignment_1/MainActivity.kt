package com.example.assignment_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import androidx.compose.material.MaterialTheme


import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment_1.ui.theme.Assignment_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment_1Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TravelProgressScreen()
                }
            }
        }
    }
}


// Step 2: Create Stop data class
data class Stop(val name: String, val distanceKm: Double)

val stops = listOf(
    Stop("NEW DELHI", 0.0),
    Stop("MATHURA ", 3.0),
    Stop("KOTA", 10.0),
    Stop("SHAMGARH", 4.0),
    Stop("VIKRAMGARH", 8.0),
    Stop("MEGHNAGAR", 6.0),
    Stop("DAHOD", 2.0),
    Stop("GODHRA", 1.0),
    Stop("ANAND", 9.0),
    Stop("AHMEDABAD", 7.0),
)
val stops5 = listOf(
    Stop("NEW DELHI", 0.0),
    Stop("MATHURA ", 1.0),
    Stop("KOTA ", 2.0),
    Stop("ANAND", 3.0),
    Stop("AHMEDABAD", 4.0),
)
@Composable
fun TravelProgressScreen() {
    var distanceUnit by remember { mutableStateOf(DistanceUnit.Kilometers) }
    var currentStopIndex by remember { mutableStateOf(0) }
    var totalDistanceCovered by remember { mutableStateOf(0.0) }
    var stopsData by remember { mutableStateOf(stops) }
    val isLastStop = currentStopIndex >= stops.size - 1

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color(0xEE000000)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        ResponsiveButton(
            onClick = {
                // Swap data between stops and stops5
                stopsData = if (stopsData == stops) {
                    stops5
                } else {
                    stops
                }
                // Reset current stop index and total distance covered
                currentStopIndex = 0
                totalDistanceCovered = calculateTotalDistanceCovered(stopsData, 0)
            },
            text = "Change No. of Stations",
            backgroundColor = Color(0xFF4CAF50), // Green color
            contentColor = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        ResponsiveButton(
            onClick = {
                distanceUnit = if (distanceUnit == DistanceUnit.Kilometers) {
                    DistanceUnit.Miles
                } else {
                    DistanceUnit.Kilometers
                }
            },
            text = "Change unit",
            backgroundColor = Color(0xFF4CAF50), // Green color
            contentColor = Color.White,
           // textColor = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))



        Text("Current Stop: ${stopsData[currentStopIndex].name}")



        Spacer(modifier = Modifier.height(10.dp))

        Text("Total Distance Covered from station 1 is: ${formatDistance(totalDistanceCovered, distanceUnit)}")
        val remainingDistance = calculateRemainingDistance(stopsData, currentStopIndex, totalDistanceCovered)
        Text("Total Distance Left for last station is: ${formatDistance(remainingDistance, distanceUnit)}")

        Spacer(modifier = Modifier.height(16.dp))




        // Progress Bar
        LinearProgressIndicator(
            progress = calculateProgress(currentStopIndex, totalDistanceCovered, stopsData),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))


        ResponsiveButton(
            onClick = {
                // Increment current stop index
                currentStopIndex++

                // If reached the last stop, reset to the first stop
                if (currentStopIndex >= stopsData.size) {
                    currentStopIndex = 0
                    totalDistanceCovered = 0.0
                } else {
                    // Update total distance covered
                    totalDistanceCovered = calculateTotalDistanceCovered(stopsData, currentStopIndex)
                }
            },
            text = if (currentStopIndex == stopsData.lastIndex) "GO to first station" else "Next Station.",
            textColor = Color.White
        )


        Spacer(modifier = Modifier.height(12.dp))

        // List of stops
        LazyColumn {
            items(stopsData.size) { index ->
                val stop = stopsData[index]
                val stationColor = if (index < currentStopIndex) {
                    Color.Gray
                } else {
                    Color.Transparent
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        text = "${stop.name} - ${formatDistance(stop.distanceKm, distanceUnit)}",
                        modifier = Modifier.padding(16.dp),
                        color = if (index <= currentStopIndex) Color.Yellow else Color.Green
                    )
                }
            }
        }
    }
}

@Composable
fun ResponsiveButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color = Color.Unspecified
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(text, color = if (textColor != Color.Unspecified) textColor else Color.Black)
    }
}

@Composable
fun formatDistance(distance: Double, unit: DistanceUnit): String {
    return if (unit == DistanceUnit.Kilometers) {
        "%.2f km".format(distance)
    } else {
        val miles = distance / 1.6
        "%.2f miles".format(miles)
    }
}

fun calculateTotalDistanceCovered(stops: List<Stop>, currentStopIndex: Int): Double {
    var totalDistance = 0.0
    for (i in 0..currentStopIndex) {
        totalDistance += stops[i].distanceKm
    }
    return totalDistance
}

fun calculateRemainingDistance(stops: List<Stop>, currentStopIndex: Int, totalDistanceCovered: Double): Double {
    val totalDistance = calculateTotalDistance(stops)
    val remainingDistance = totalDistance - totalDistanceCovered
    return if (remainingDistance < 0.0) 0.0 else remainingDistance
}

fun calculateTotalDistance(stops: List<Stop>): Double {
    return stops.sumOf { it.distanceKm }
}
fun calculateProgress(currentStopIndex: Int, totalDistanceCovered: Double, stops: List<Stop>): Float {
    val totalDistance = calculateTotalDistance(stops)
    val progress = if (totalDistance > 0) {
        (totalDistanceCovered / totalDistance).coerceIn(0.0, 1.0)
    } else {
        0.0
    }

    return when {
        currentStopIndex == stops.lastIndex -> 1.0f
        currentStopIndex == 0 -> 0.0f
        else -> progress.toFloat()
    }
}

@Composable
fun ResponsiveButton(
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color = Color.Blue,
    contentColor: Color = Color.White
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = backgroundColor), // Set the background color here
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}

enum class DistanceUnit {
    Kilometers,
    Miles
}
