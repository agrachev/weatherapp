package ru.agrachev.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.android.getKoin
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.weatherapp.util.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val features = getKoin().getAll<Feature<*>>()
        enableEdgeToEdge()
        setContent {
            MainScreen(features)
        }
    }
}
