package pmul.alex.clima.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import pmul.alex.clima.ui.model.ScreenState
import pmul.alex.clima.ui.screens.*
import pmul.alex.clima.ui.viewmodel.WeatherViewModel

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            viewModel.onPermissionResult(isGranted, context)
        }
    )

    when (val state = uiState) {
        is ScreenState.Selection -> SelectionScreen(
            onGeolocationClick = {
                // The View is responsible for handling Android-specific logic like permissions
                when (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    PackageManager.PERMISSION_GRANTED -> {
                        viewModel.fetchLocationAndWeather(context)
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                }
            },
            onCoordinatesClick = viewModel::onNavigateToCoordinates,
            onCityClick = viewModel::onNavigateToCity
        )
        is ScreenState.CoordinateInput -> CoordinateInputScreen(
            onGetWeatherClick = { lat, lon ->
                viewModel.onCoordinatesSubmit(lat, lon, context)
            },
            onBack = viewModel::onBackToSelection
        )
        is ScreenState.CityInput -> CityInputScreen(
            onGetWeatherClick = { cityName ->
                viewModel.onCitySubmit(cityName, context)
            },
            onBack = viewModel::onBackToSelection
        )
        is ScreenState.Loading -> LoadingScreen()
        is ScreenState.WeatherDisplay -> WeatherDisplayScreen(
            weather = state.weather,
            forecast = state.forecast,
            onBack = viewModel::onBackToSelection
        )
        is ScreenState.Error -> ErrorScreen(
            message = state.message,
            onBack = viewModel::onBackToSelection
        )
    }
}
