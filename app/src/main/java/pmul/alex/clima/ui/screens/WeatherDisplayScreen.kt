package pmul.alex.clima.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pmul.alex.clima.R
import pmul.alex.clima.network.WeatherResponse

@Composable
fun WeatherDisplayScreen(weather: WeatherResponse, onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(R.string.weather_display_city, weather.name))
            Text(stringResource(R.string.weather_display_temperature, weather.main.temp))
            Text(stringResource(R.string.weather_display_description, weather.weather.getOrNull(0)?.description ?: stringResource(R.string.weather_description_not_available)))
            Button(onClick = onBack) { Text(stringResource(R.string.weather_display_back_button)) }
        }
    }
}
