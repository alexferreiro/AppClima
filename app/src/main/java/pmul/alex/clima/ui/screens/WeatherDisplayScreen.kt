package pmul.alex.clima.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import pmul.alex.clima.ui.theme.AccentBlue
import pmul.alex.clima.ui.theme.AccentBlueDark

@Composable
fun WeatherDisplayScreen(weather: WeatherResponse, forecast: ForecastResponse, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_content_description), tint = MaterialTheme.colorScheme.onBackground)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MainWeatherCard(weather)
        Spacer(modifier = Modifier.height(32.dp))
        ForecastSection(forecast)
    }
}

@Composable
fun MainWeatherCard(weather: WeatherResponse) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.verticalGradient(colors = listOf(AccentBlue, AccentBlueDark))
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = weather.weather.firstOrNull()?.main ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@4x.png")
                        .crossfade(true)
                        .build(),
                    contentDescription = weather.weather.firstOrNull()?.description,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = "${weather.main.temp.toInt()}°",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 90.sp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    DetailIconItem(icon = Icons.Default.Air, value = "${weather.wind.speed} m/s", label = "Wind")
                    DetailIconItem(icon = Icons.Default.WaterDrop, value = "${weather.main.humidity}%", label = "Humidity")
                    DetailIconItem(icon = Icons.Default.Visibility, value = "${weather.visibility / 1000} km", label = "Visibility")
                }
            }
        }
    }
}

@Composable
fun DetailIconItem(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.White.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold)
        Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
    }
}

@Composable
fun ForecastSection(forecast: ForecastResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.forecast_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(forecast.list.take(8)) { forecastItem ->
                ForecastItemView(item = forecastItem)
            }
        }
    }
}

@Composable
fun ForecastItemView(item: ForecastItem) {
    val hour = item.dateTime.substring(11, 13)
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f) // Fondo gris claro sutil
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "${hour}:00", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://openweathermap.org/img/wn/${item.weather.firstOrNull()?.icon}@2x.png")
                    .crossfade(true).build(),
                contentDescription = item.weather.firstOrNull()?.description,
                modifier = Modifier.size(50.dp)
            )
            Text(text = "${item.main.temp.toInt()}°", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}
