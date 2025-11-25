package pmul.alex.clima.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pmul.alex.clima.R
import pmul.alex.clima.ui.theme.LightGray

@Composable
fun SelectionScreen(onGeolocationClick: () -> Unit, onCoordinatesClick: () -> Unit, onCityClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.WbCloudy,
                contentDescription = stringResource(id = R.string.logo_content_description),
                modifier = Modifier.size(120.dp),
                tint = LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = R.string.selection_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            Button(
                onClick = onGeolocationClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.selection_button_geolocation), color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCoordinatesClick,
                    modifier = Modifier.weight(1f).height(50.dp),
                    border = BorderStroke(1.dp, LightGray) // Color gris claro
                ) {
                    Text(stringResource(R.string.selection_button_coordinates), color = LightGray, textAlign = TextAlign.Center) // Color gris claro
                }

                OutlinedButton(
                    onClick = onCityClick,
                    modifier = Modifier.weight(1f).height(50.dp),
                    border = BorderStroke(1.dp, LightGray) // Color gris claro
                ) {
                    Text(stringResource(R.string.selection_button_city), color = LightGray, textAlign = TextAlign.Center) // Color gris claro
                }
            }
        }
    }
}
