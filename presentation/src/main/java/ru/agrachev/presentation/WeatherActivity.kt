package ru.agrachev.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.agrachev.presentation.composables.screen.WeatherScreen
import ru.agrachev.presentation.theme.WeatherAppTheme

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
