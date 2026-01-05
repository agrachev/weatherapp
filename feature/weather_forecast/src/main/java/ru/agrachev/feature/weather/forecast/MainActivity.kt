package ru.agrachev.feature.weather.forecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.agrachev.feature.weather.forecast.composables.screen.WeatherScreen
import ru.agrachev.feature.weather.forecast.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModel<WeatherViewModel>()
        setContent {
            WeatherAppTheme {
                WeatherScreen(viewModel)
            }
        }
    }
}
