package pmul.alex.clima.ui.screens

import androidx.compose.foundation.layout.*
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
import pmul.alex.clima.network.WeatherResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDisplayScreen(weather: WeatherResponse, onBack: () -> Unit) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherCard(weather)
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
                    text = "${weather.main.temp}°C",
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
        DetailRow("Sensación térmica", "${weather.main.feelsLike}°C")
        DetailRow("Mín / Máx", "${weather.main.tempMin}°C / ${weather.main.tempMax}°C")
        DetailRow("Humedad", "${weather.main.humidity}%")
        DetailRow("Viento", "${weather.wind.speed} m/s")
        DetailRow("Visibilidad", "${weather.visibility / 1000.0} km")
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
