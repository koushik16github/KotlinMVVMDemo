package com.koushik.kotlinmvvmdemo.data.repository

import com.koushik.kotlinmvvmdemo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseApiRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Error(throwable.localizedMessage ?: "An unexpected error occurred")
                    }
                    else -> {
                        Resource.Error("Couldn't reach server. Check your internet connection.")
                    }
                }
            }
        }
    }
}