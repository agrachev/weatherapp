package ru.agrachev.feature.location.composables

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop

@Composable
internal fun RequestCurrentLocationIcon(
    locationToggleProvider: () -> Boolean,
    onLocationRequestedCallback: (Boolean) -> Unit,
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
                    onLocationRequestedCallback(true)
                }
            }
    }
    RequestCurrentLocationIconUi(
        locationToggleProvider = locationToggleProvider,
        onLocationButtonClick = {
            if (!hasPermission) {
                permissionLauncher.launch(locationPermissions)
            } else {
                onLocationRequestedCallback(!locationToggleProvider())
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun RequestCurrentLocationIconUi(
    locationToggleProvider: () -> Boolean,
    onLocationButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onLocationButtonClick,
        modifier = modifier,
    ) {
        locationToggleProvider().also { isToggledOn ->
            Icon(
                imageVector = with(Icons.Filled) {
                    if (isToggledOn) LocationOn else LocationOff
                },
                contentDescription = null,
                modifier = Modifier
                    .animatedScale(isToggledOn),
            )
        }
    }
}

@Composable
private fun Modifier.animatedScale(toggledOn: Boolean) = if (toggledOn) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "LocationIconInfiniteTransition",
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = TARGET_SCALE,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = EaseInOut,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "LocationIconScaleAnimation",
    )
    this then Modifier
        .graphicsLayer {
            transformOrigin = TransformOrigin.Center
            scaleX = scale
            scaleY = scale
        }
} else {
    this
}

private val locationPermissions = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

private const val TARGET_SCALE = 1.25f
