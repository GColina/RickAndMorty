package com.gcolina.rickandmorty.utils

import retrofit2.Response
import javax.inject.Inject

open class BaseDataSource @Inject constructor() {
    suspend fun <T> processResponse(
        service: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = service()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: run {
                    Result.failure(Throwable(response.message()))
                }
            } else {
                Result.failure(Throwable(response.message()))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}