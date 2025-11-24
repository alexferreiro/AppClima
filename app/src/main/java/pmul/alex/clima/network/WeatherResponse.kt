package pmul.alex.clima.network

import com.google.gson.annotations.SerializedName

// Clase principal que engloba toda la respuesta
data class WeatherResponse(
    val weather: List<WeatherDescription>,
    val main: Main,
    val wind: Wind,
    val visibility: Int, // en metros
    val name: String
)

// Descripción principal del clima y el icono
data class WeatherDescription(
    val main: String, // Ej: "Clouds", "Rain"
    val description: String, // Ej: "broken clouds"
    val icon: String // Código del icono, ej: "04d"
)

// Contiene todos los datos principales de temperatura
data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val humidity: Int
)

// Contiene los datos del viento
data class Wind(
    val speed: Double
)
