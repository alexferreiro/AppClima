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

@Composable
fun SelectionScreen(onGeolocationClick: () -> Unit, onCoordinatesClick: () -> Unit, onCityClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(stringResource(R.string.selection_title))
            Button(onClick = onGeolocationClick) { Text(stringResource(R.string.selection_button_geolocation)) }
            Button(onClick = onCoordinatesClick) { Text(stringResource(R.string.selection_button_coordinates)) }
            Button(onClick = onCityClick) { Text(stringResource(R.string.selection_button_city)) }
        }
    }
}
