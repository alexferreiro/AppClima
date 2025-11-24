package pmul.alex.clima.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pmul.alex.clima.R
import pmul.alex.clima.data.model.ForecastItem
import pmul.alex.clima.data.model.ForecastResponse
import pmul.alex.clima.data.model.WeatherResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDisplayScreen(weather: WeatherResponse, forecast: ForecastResponse, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.weather_display_city, weather.name)) },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = onBack) {
                        Text(stringResource(R.string.weather_display_back_button))
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            WeatherCard(weather)
            Spacer(modifier = Modifier.height(24.dp))
            ForecastSection(forecast)
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Icono y temperatura principal ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val iconUrl = "https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@4x.png"
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = weather.weather.firstOrNull()?.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.weather_temp_celsius, weather.main.temp),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))
            Divider()
            Spacer(modifier = Modifier.height(24.dp))

            // --- Detalles adicionales ---
            WeatherDetails(weather)
        }
    }
}

@Composable
fun WeatherDetails(weather: WeatherResponse) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        DetailRow(
            label = stringResource(R.string.weather_details_feels_like),
            value = stringResource(R.string.weather_temp_celsius, weather.main.feelsLike)
        )
        DetailRow(
            label = stringResource(R.string.weather_details_min_max),
            value = stringResource(R.string.weather_temp_min_max_celsius, weather.main.tempMin, weather.main.tempMax)
        )
        DetailRow(
            label = stringResource(R.string.weather_details_humidity),
            value = stringResource(R.string.weather_humidity_percentage, weather.main.humidity)
        )
        DetailRow(
            label = stringResource(R.string.weather_details_wind),
            value = stringResource(R.string.weather_wind_speed_ms, weather.wind.speed)
        )
        DetailRow(
            label = stringResource(R.string.weather_details_visibility),
            value = stringResource(R.string.weather_visibility_km, weather.visibility / 1000.0)
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ForecastSection(forecast: ForecastResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.forecast_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(forecast.list) { forecastItem ->
                ForecastItemView(item = forecastItem)
            }
        }
    }
}

@Composable
fun ForecastItemView(item: ForecastItem) {
    // Solución simple y compatible para obtener la hora: "2023-10-27 18:00:00" -> "18"
    val hour = item.dateTime.substring(11, 13)

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.forecast_time_format, hour),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            val iconUrl = "https://openweathermap.org/img/wn/${item.weather.firstOrNull()?.icon}@2x.png"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.weather.firstOrNull()?.description,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = stringResource(R.string.weather_temp_celsius, item.main.temp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
