package pmul.alex.clima.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import pmul.alex.clima.R
import pmul.alex.clima.network.WeatherResponse
import pmul.alex.clima.ui.model.ScreenState
import pmul.alex.clima.ui.screens.*
import retrofit2.HttpException
import kotlin.Result

@Composable
fun WeatherApp(
    requestLocation: (onSuccess: (lat: Double, lon: Double) -> Unit, onError: (String) -> Unit) -> Unit,
    getWeatherByCoords: (lat: Double, lon: Double, onResult: (Result<WeatherResponse>) -> Unit) -> Unit,
    getWeatherByCity: (cityName: String, onResult: (Result<WeatherResponse>) -> Unit) -> Unit
) {
    var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Selection) }
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                screenState = ScreenState.Loading
                requestLocation(
                    { lat, lon ->
                        getWeatherByCoords(lat, lon) { result ->
                            result.fold(
                                onSuccess = { screenState = ScreenState.WeatherDisplay(it) },
                                onFailure = { screenState = ScreenState.Error(it.toUserFriendlyMessage(context)) }
                            )
                        }
                    },
                    { errorMessage -> screenState = ScreenState.Error(errorMessage) }
                )
            } else {
                screenState = ScreenState.Error(context.getString(R.string.error_permission_denied))
            }
        }
    )

    when (val state = screenState) {
        is ScreenState.Selection -> SelectionScreen(
            onGeolocationClick = {
                when (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    PackageManager.PERMISSION_GRANTED -> {
                        screenState = ScreenState.Loading
                        requestLocation(
                            { lat, lon ->
                                getWeatherByCoords(lat, lon) { result ->
                                    result.fold(
                                        onSuccess = { screenState = ScreenState.WeatherDisplay(it) },
                                        onFailure = { screenState = ScreenState.Error(it.toUserFriendlyMessage(context)) }
                                    )
                                }
                            },
                            { errorMessage -> screenState = ScreenState.Error(errorMessage) }
                        )
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                }
            },
            onCoordinatesClick = { screenState = ScreenState.CoordinateInput },
            onCityClick = { screenState = ScreenState.CityInput }
        )
        is ScreenState.CoordinateInput -> CoordinateInputScreen(
            onGetWeatherClick = { lat, lon ->
                screenState = ScreenState.Loading
                val latDouble = lat.toDoubleOrNull()
                val lonDouble = lon.toDoubleOrNull()
                if (latDouble == null || lonDouble == null) {
                    screenState = ScreenState.Error(context.getString(R.string.error_invalid_coordinates))
                } else {
                    getWeatherByCoords(latDouble, lonDouble) { result ->
                        result.fold(
                            onSuccess = { screenState = ScreenState.WeatherDisplay(it) },
                            onFailure = { screenState = ScreenState.Error(it.toUserFriendlyMessage(context)) }
                        )
                    }
                }
            },
            onBack = { screenState = ScreenState.Selection }
        )
        is ScreenState.CityInput -> CityInputScreen(
            onGetWeatherClick = { cityName ->
                screenState = ScreenState.Loading
                getWeatherByCity(cityName) { result ->
                    result.fold(
                        onSuccess = { screenState = ScreenState.WeatherDisplay(it) },
                        onFailure = { screenState = ScreenState.Error(it.toUserFriendlyMessage(context)) }
                    )
                }
            },
            onBack = { screenState = ScreenState.Selection }
        )
        is ScreenState.Loading -> LoadingScreen()
        is ScreenState.WeatherDisplay -> WeatherDisplayScreen(state.weather, onBack = { screenState = ScreenState.Selection })
        is ScreenState.Error -> ErrorScreen(state.message, onBack = { screenState = ScreenState.Selection })
    }
}

private fun Throwable.toUserFriendlyMessage(context: Context): String {
    return when (this) {
        is HttpException -> {
            when (code()) {
                401 -> context.getString(R.string.error_api_key)
                404 -> context.getString(R.string.error_city_not_found)
                in 500..599 -> context.getString(R.string.error_server)
                else -> context.getString(R.string.error_network_code, code().toString())
            }
        }
        is java.net.UnknownHostException -> context.getString(R.string.error_no_internet)
        else -> context.getString(R.string.error_unexpected, this.message ?: "")
    }
}
