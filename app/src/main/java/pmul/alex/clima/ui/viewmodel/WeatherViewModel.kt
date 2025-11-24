package pmul.alex.clima.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pmul.alex.clima.R
import pmul.alex.clima.data.model.ForecastResponse
import pmul.alex.clima.data.model.WeatherResponse
import pmul.alex.clima.data.repository.WeatherRepository
import pmul.alex.clima.ui.model.ScreenState
import retrofit2.HttpException

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Selection)
    val uiState: StateFlow<ScreenState> = _uiState.asStateFlow()

    // --- Métodos de Navegación --- //
    fun onNavigateToCoordinates() {
        _uiState.value = ScreenState.CoordinateInput
    }

    fun onNavigateToCity() {
        _uiState.value = ScreenState.CityInput
    }

    fun onBackToSelection() {
        _uiState.value = ScreenState.Selection
    }

    // --- Métodos de Lógica de Negocio --- //
    fun onPermissionResult(isGranted: Boolean, context: Context) {
        if (isGranted) {
            fetchLocationAndWeather(context)
        } else {
            _uiState.value = ScreenState.Error(context.getString(R.string.error_permission_denied))
        }
    }

    fun onCoordinatesSubmit(lat: String, lon: String, context: Context) {
        val latDouble = lat.toDoubleOrNull()
        val lonDouble = lon.toDoubleOrNull()
        if (latDouble == null || lonDouble == null) {
            _uiState.value = ScreenState.Error(context.getString(R.string.error_invalid_coordinates))
            return
        }
        fetchWeatherAndForecast(latDouble, lonDouble, context)
    }

    fun onCitySubmit(cityName: String, context: Context) {
        if (cityName.isBlank()) return
        fetchWeatherAndForecast(cityName, context)
    }
    
    @SuppressLint("MissingPermission")
    fun fetchLocationAndWeather(context: Context) {
        _uiState.value = ScreenState.Loading
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    fetchWeatherAndForecast(location.latitude, location.longitude, context)
                } else {
                    _uiState.value = ScreenState.Error(context.getString(R.string.error_location_null))
                }
            }.addOnFailureListener { e ->
                _uiState.value = ScreenState.Error(context.getString(R.string.error_location_failure_generic, e.message ?: ""))
            }
    }

    private fun fetchWeatherAndForecast(lat: Double, lon: Double, context: Context) {
        viewModelScope.launch {
            _uiState.value = ScreenState.Loading
            val weatherResult = repository.getWeatherByCoords(lat, lon)
            val forecastResult = repository.getForecastByCoords(lat, lon)
            handleResults(weatherResult, forecastResult, context)
        }
    }

    private fun fetchWeatherAndForecast(cityName: String, context: Context) {
        viewModelScope.launch {
            _uiState.value = ScreenState.Loading
            val weatherResult = repository.getWeatherByCity(cityName)
            val forecastResult = repository.getForecastByCity(cityName)
            handleResults(weatherResult, forecastResult, context)
        }
    }

    private fun handleResults(weatherResult: Result<WeatherResponse>, forecastResult: Result<ForecastResponse>, context: Context) {
        if (weatherResult.isSuccess && forecastResult.isSuccess) {
            _uiState.value = ScreenState.WeatherDisplay(weatherResult.getOrThrow(), forecastResult.getOrThrow())
        } else {
            val error = weatherResult.exceptionOrNull() ?: forecastResult.exceptionOrNull()
            _uiState.value = ScreenState.Error(error.toUserFriendlyMessage(context))
        }
    }

    private fun Throwable?.toUserFriendlyMessage(context: Context): String {
        this ?: return context.getString(R.string.error_unexpected, "")
        return when (this) {
            is HttpException -> when (code()) {
                401 -> context.getString(R.string.error_api_key)
                404 -> context.getString(R.string.error_city_not_found)
                in 500..599 -> context.getString(R.string.error_server)
                else -> context.getString(R.string.error_network_code, code().toString())
            }
            is java.net.UnknownHostException -> context.getString(R.string.error_no_internet)
            else -> context.getString(R.string.error_unexpected, message ?: "")
        }
    }
}
