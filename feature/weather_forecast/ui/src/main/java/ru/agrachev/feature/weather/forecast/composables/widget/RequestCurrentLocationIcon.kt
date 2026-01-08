package ru.agrachev.feature.weather.forecast.composables.widget

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import ru.agrachev.feature.weather.forecast.R

@Composable
internal fun RequestCurrentLocationIcon(
    onLocationRequestedCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            locationPermissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { map ->
        hasPermission = map.all { it.value }
    }
    LaunchedEffect(Unit) {
        snapshotFlow {
            hasPermission
        }
            .distinctUntilChanged()
            .drop(1)
            .collect { hasJustGotLocationPermissions ->
                if (hasJustGotLocationPermissions) {
                    onLocationRequestedCallback()
                }
            }
    }
    Icon(
        painter = painterResource(R.drawable.my_location_24dp),
        contentDescription = null,
        modifier = modifier then Modifier
            .clickable {
                if (!hasPermission) {
                    permissionLauncher.launch(locationPermissions)
                } else {
                    onLocationRequestedCallback()
                }
            }
    )
}

private val locationPermissions = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)
