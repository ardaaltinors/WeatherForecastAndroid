package com.example.weatherforecast.repository

import android.util.Log
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String)
    :DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)

        } catch (e: Exception) {
            Log.d("GET API exc", "getWeather $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE API", "response $response")

        return DataOrException(data = response)
    }

}