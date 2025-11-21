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
fun CityInputScreen(onGetWeatherClick: (String) -> Unit, onBack: () -> Unit) {
    var city by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text(stringResource(R.string.city_input_label)) })
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onGetWeatherClick(city) }) { Text(stringResource(R.string.common_get_weather)) }
            Button(onClick = onBack) { Text(stringResource(R.string.common_back)) }
        }
    }
}
