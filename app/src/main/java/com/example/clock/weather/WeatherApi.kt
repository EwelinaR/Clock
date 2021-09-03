package com.example.clock.weather

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class WeatherApi(private val observer: WeatherObserver): Callback {

    fun getWeather() = runBlocking {
        launch {
            callWeatherApi()
        }
    }

    private fun callWeatherApi() {
        val cityId = "3099434"
        val apiKey = "a34b12b7c0ae913e48bd719c585b30c5"
        val request = Request
            .Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?id=$cityId&units=metric&appid=$apiKey")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        throw e
    }

    override fun onResponse(call: Call, response: Response) {
        response.use {
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            if (response.body() == null) throw IOException("Body of response is null. $response")
            val parser = WeatherParser(response.body()!!.string())
            parser.getResult()?.let {
                observer.updateWeatherValues(it)
            }
        }
    }
}
