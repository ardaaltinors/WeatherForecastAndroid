package com.example.weatherforecast.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDecimals
import com.example.weatherforecast.widgets.HumidityWindPressureRow
import com.example.weatherforecast.widgets.SunsetSunriseRow
import com.example.weatherforecast.widgets.WeatherAppBar
import com.example.weatherforecast.widgets.WeatherDetailRow
import com.example.weatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel(), city: String?) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>
            >(initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city = city.toString())
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        //Text(text = "main screen ${weatherData.data!!.city.country}")
        MainScaffold(weather = weatherData.data!!, navController)

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                elevation = 5.dp,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        }

    ) {
        MainContent(data = weather)
    }

}

@Composable
fun MainContent(data: Weather) {

    val weatherItem = data!!.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "º",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)

            }

        }

        HumidityWindPressureRow(weather = weatherItem)
        Divider()
        SunsetSunriseRow(weather = weatherItem)
        Text(
            text = "This Week",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {

            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {

                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }

            }

        }

    }
}

