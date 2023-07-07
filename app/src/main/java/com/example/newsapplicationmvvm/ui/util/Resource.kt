package com.example.newsapplicationmvvm.ui.util


//sealed class --> Similar to abstract class but we can control which classes are allowed to inherit from these sealed class
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}