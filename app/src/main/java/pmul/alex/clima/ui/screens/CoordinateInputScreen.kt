package pmul.alex.clima.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pmul.alex.clima.R

@Composable
fun CoordinateInputScreen(onGetWeatherClick: (String, String) -> Unit, onBack: () -> Unit) {
    var lat by remember { mutableStateOf("") }
    var lon by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = lat, onValueChange = { lat = it }, label = { Text(stringResource(R.string.coordinates_input_latitude_label)) })
            OutlinedTextField(value = lon, onValueChange = { lon = it }, label = { Text(stringResource(R.string.coordinates_input_longitude_label)) })
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onGetWeatherClick(lat, lon) }) { Text(stringResource(R.string.common_get_weather)) }
            Button(onClick = onBack) { Text(stringResource(R.string.common_back)) }
        }
    }
}
