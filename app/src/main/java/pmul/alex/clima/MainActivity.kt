package pmul.alex.clima

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import pmul.alex.clima.network.RetrofitInstance
import pmul.alex.clima.network.WeatherResponse
import pmul.alex.clima.ui.theme.ClimaTheme
import pmul.alex.clima.ui.WeatherApp

class MainActivity : ComponentActivity() {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClimaTheme {
                WeatherApp(
                    requestLocation = ::requestLocation,
                    getWeatherByCoords = ::getWeatherByCoords,
                    getWeatherByCity = ::getWeatherByCity
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation(onSuccess: (lat: Double, lon: Double) -> Unit, onError: (String) -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            onError(getString(R.string.error_permission_not_granted_at_request))
            return
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    onSuccess(location.latitude, location.longitude)
                } else {
                    onError(getString(R.string.error_location_null))
                }
            }.addOnFailureListener {
                onError(getString(R.string.error_location_failure_generic, it.message ?: ""))
            }
    }

    private fun getWeatherByCoords(lat: Double, lon: Double, onResult: (Result<WeatherResponse>) -> Unit) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
                )
                onResult(Result.success(response))
            } catch (e: Exception) {
                onResult(Result.failure(e))
            }
        }
    }

    private fun getWeatherByCity(cityName: String, onResult: (Result<WeatherResponse>) -> Unit) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getWeatherByCity(
                    cityName = cityName,
                    apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
                )
                onResult(Result.success(response))
            } catch (e: Exception) {
                onResult(Result.failure(e))
            }
        }
    }
}
