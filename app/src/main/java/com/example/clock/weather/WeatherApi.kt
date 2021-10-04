package com.example.clock.weather

import com.example.clock.utils.AppConstants.REQUEST_URL
import com.example.clock.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class WeatherApi @Inject constructor(private val okHttpClient: OkHttpClient,
                                     @IoDispatcher private val dispatcher: CoroutineDispatcher) {

    suspend fun getWeather(): Response<Weather> = withContext(dispatcher)  {
        val request = Request.Builder().url(REQUEST_URL).build()

        return@withContext try {
            val response = okHttpClient.newCall(request).execute()
            Response.Success(WeatherParser(response.body!!.string()).getResult()!!)
        } catch (e: Exception) {
            Response.Failure(e.message ?: "Unknown exception while connecting to weather API")
        }
    }
}
