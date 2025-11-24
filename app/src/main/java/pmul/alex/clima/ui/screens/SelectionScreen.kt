package pmul.alex.clima.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pmul.alex.clima.R

@Composable
fun SelectionScreen(onGeolocationClick: () -> Unit, onCoordinatesClick: () -> Unit, onCityClick: () -> Unit) {
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
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.selection_title),
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            val buttonModifier = Modifier
                .fillMaxWidth()
                .height(50.dp)

            Button(
                onClick = onGeolocationClick, 
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.3f))
            ) {
                Text(stringResource(R.string.selection_button_geolocation), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCoordinatesClick, 
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.3f))
            ) {
                Text(stringResource(R.string.selection_button_coordinates), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCityClick, 
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.3f))
            ) {
                Text(stringResource(R.string.selection_button_city), fontSize = 16.sp)
            }
        }
    }
}
