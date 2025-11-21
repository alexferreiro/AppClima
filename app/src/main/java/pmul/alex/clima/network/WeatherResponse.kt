package pmul.alex.clima.network

data class WeatherResponse(
    val weather: List<WeatherDescription>,
    val main: Main,
    val name: String
)

data class WeatherDescription(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double
)
