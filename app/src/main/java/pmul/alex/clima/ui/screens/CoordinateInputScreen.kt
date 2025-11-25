package pmul.alex.clima.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_content_description), tint = MaterialTheme.colorScheme.onBackground)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn, // <-- Icono cambiado
                contentDescription = null, // Decorative icon
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = lat,
                onValueChange = { lat = it },
                label = { Text(stringResource(R.string.coordinates_input_latitude_label)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = lon,
                onValueChange = { lon = it },
                label = { Text(stringResource(R.string.coordinates_input_longitude_label)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onGetWeatherClick(lat, lon) },
                modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.common_get_weather), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
