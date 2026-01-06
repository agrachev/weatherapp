package ru.agrachev.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.agrachev.core.presentation.theme.WeatherAppTheme
import ru.agrachev.feature.weather.forecast.composables.screen.WeatherScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}
