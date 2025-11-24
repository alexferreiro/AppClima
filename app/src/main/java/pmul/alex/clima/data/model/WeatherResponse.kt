package pmul.alex.clima.data.model

import com.google.gson.annotations.SerializedName

// --- CLASES PARA EL CLIMA ACTUAL ---

data class WeatherResponse(
    val weather: List<WeatherDescription>,
    val main: Main,
    val wind: Wind,
    val visibility: Int,
    val name: String
)

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val humidity: Int
)

data class Wind(
    val speed: Double
)

// --- CLASES PARA EL PRONÓSTICO ---

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val main: Main,
    val weather: List<WeatherDescription>,
    @SerializedName("dt_txt") val dateTime: String
)
