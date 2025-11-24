package pmul.alex.clima.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

@Composable
fun WeatherDisplayScreen(weather: WeatherResponse, forecast: ForecastResponse, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF87CEEB), // Sky Blue
                        Color(0xFF4682B4)  // Steel Blue
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // --- Back Button ---
            Row(modifier = Modifier.fillMaxWidth().padding(start = 4.dp)) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_content_description), tint = Color.White)
                }
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()), // Makes the column scrollable
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // --- Main Weather Info ---
                WeatherSummary(weather)

                Spacer(modifier = Modifier.height(24.dp))

                // --- Forecast ---
                ForecastSection(forecast)

                Spacer(modifier = Modifier.height(24.dp))

                // --- Detailed Info Card ---
                WeatherDetailsCard(weather)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun WeatherSummary(weather: WeatherResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val iconUrl = "https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@4x.png"
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = weather.weather.firstOrNull()?.description,
            modifier = Modifier.size(160.dp) // Large icon
        )

        Text(
            text = weather.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = stringResource(R.string.weather_temp_celsius, weather.main.temp),
            fontSize = 80.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
        Text(
            text = weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "",
            fontSize = 22.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
        Text(
            text = stringResource(R.string.weather_temp_min_max_celsius, weather.main.tempMin, weather.main.tempMax),
            fontSize = 18.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun WeatherDetailsCard(weather: WeatherResponse) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.3f)),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailItem(label = stringResource(R.string.weather_details_feels_like), value = stringResource(R.string.weather_temp_celsius, weather.main.feelsLike))
            DetailItem(label = stringResource(R.string.weather_details_humidity), value = stringResource(R.string.weather_humidity_percentage, weather.main.humidity))
            DetailItem(label = stringResource(R.string.weather_details_wind), value = stringResource(R.string.weather_wind_speed_ms, weather.wind.speed))
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
        Text(text = value, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ForecastSection(forecast: ForecastResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.forecast_title),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(forecast.list.take(8)) { forecastItem -> // Show first 24h
                ForecastItemView(item = forecastItem)
            }
        }
    }
}

@Composable
fun ForecastItemView(item: ForecastItem) {
    val hour = item.dateTime.substring(11, 13)
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.3f)),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp), // More compact padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp) // Reduced spacing
        ) {
            Text(
                text = stringResource(R.string.forecast_time_format, hour),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            val iconUrl = "https://openweathermap.org/img/wn/${item.weather.firstOrNull()?.icon}@2x.png"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(iconUrl).crossfade(true).build(),
                contentDescription = item.weather.firstOrNull()?.description,
                modifier = Modifier.size(40.dp) // Smaller icon for landscape
            )
            Text(
                text = stringResource(R.string.weather_temp_celsius, item.main.temp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
