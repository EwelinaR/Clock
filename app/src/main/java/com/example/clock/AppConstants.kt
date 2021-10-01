package com.example.clock

object AppConstants {
    private const val CITY_ID = "3099434"
    private const val API_KEY = "a34b12b7c0ae913e48bd719c585b30c5"
    const val REQUEST_URL = "https://api.openweathermap.org/data/2.5/weather?id=$CITY_ID&units=metric&appid=$API_KEY"
}
