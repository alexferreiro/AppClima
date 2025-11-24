package pmul.alex.clima.data.repository

import pmul.alex.clima.data.model.ForecastResponse
import pmul.alex.clima.data.model.WeatherResponse
import pmul.alex.clima.network.RetrofitInstance
import pmul.alex.clima.network.WeatherApiService
import pmul.alex.clima.BuildConfig

class WeatherRepository {

    private val weatherApiService: WeatherApiService by lazy {
        RetrofitInstance.api
    }

    suspend fun getWeatherByCoords(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            val response = weatherApiService.getCurrentWeather(
                lat = lat,
                lon = lon,
                apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse> {
        return try {
            val response = weatherApiService.getWeatherByCity(
                cityName = cityName,
                apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getForecastByCoords(lat: Double, lon: Double): Result<ForecastResponse> {
        return try {
            val response = weatherApiService.getForecast(
                lat = lat,
                lon = lon,
                apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getForecastByCity(cityName: String): Result<ForecastResponse> {
        return try {
            val response = weatherApiService.getForecastByCity(
                cityName = cityName,
                apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
