package pmul.alex.clima.ui.model

import pmul.alex.clima.data.model.ForecastResponse
import pmul.alex.clima.data.model.WeatherResponse

// Define los diferentes estados/pantallas de la aplicación
sealed class ScreenState {
    object Selection : ScreenState()
    data class WeatherDisplay(val weather: WeatherResponse, val forecast: ForecastResponse) : ScreenState()
    data class Error(val message: String) : ScreenState()
    object Loading : ScreenState()
    object CoordinateInput : ScreenState()
    object CityInput : ScreenState()
}
