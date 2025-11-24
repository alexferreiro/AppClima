package pmul.alex.clima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pmul.alex.clima.ui.WeatherApp
import pmul.alex.clima.ui.theme.ClimaTheme
import pmul.alex.clima.ui.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClimaTheme {
                WeatherApp(viewModel = viewModel)
            }
        }
    }
}
