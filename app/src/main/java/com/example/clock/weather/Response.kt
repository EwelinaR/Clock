package com.example.clock.weather

sealed class Response<out T> {
    class Success<out T>(val data: T) : Response<T>()
    class Failure<out T>(val errorMessage: String) : Response<T>()
}
