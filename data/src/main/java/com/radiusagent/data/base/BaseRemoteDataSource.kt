package com.radiusagent.data.base


import com.google.gson.Gson
import com.radiusagent.domain.model.network.Result
import com.radiusagent.domain.model.network.ErrorResponse
import com.radiusagent.domain.model.network.Failure
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:20 pm */
abstract class BaseRemoteDataSource {

    suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String = "Some error occurred"
    ): Result<T, Failure> {
        return try {
            val result = request.invoke()
            Timber.d("base remote data source : ${result.body().toString()}")
            if (result.isSuccessful) {
                if (result.body() == null) {
                    return Result.Error(
                        Failure.NetworkConnection,
                        ErrorResponse("Response Body is empty ", null)
                    )
                } else {
                    return com.radiusagent.domain.model.network.Result.Success(result.body()!!)
                }
            } else {
                val (failure, errorResponse) = parseError(result)
                Result.Error(
                    failure,
                    errorResponse
                )
            }
        } catch (e: Throwable) {
            Timber.e(
                Exception(e),
                " -------------------------------- *****************  E R R O R      I N      B A S E     R E M O T E      D A T A S O U R C E  ***************** ------------------------ ${e.message}"
            )
            Result.Error(Failure.ParsingError, ErrorResponse(e.message, Exception(e)))
        }
    }

    /**
     * Method to convert the error response of API Service to requested type
     * @param response the response of requested api
     * @return the ApiError of the request
     */
    private fun parseError(response: Response<*>): Pair<Failure, ErrorResponse> {
        try {
            response.let {
                when (response.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> {
                        val json = response.errorBody()?.string()
                        val error = Gson().fromJson(json, ErrorResponse::class.java)
                        error.exception = Exception(json)
                        return Pair(
                            Failure.BadRequest,
                            error,
                        )
                    }

                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        val json = response.errorBody()?.string()
                        val error = Gson().fromJson(json, ErrorResponse::class.java)
                        error.exception = Exception(json)
                        return Pair(
                            Failure.UnauthorizedError,
                            error,
                        )
                    }

                    HttpURLConnection.HTTP_NOT_FOUND -> {
                        val json = response.errorBody()?.string()
                        val error = Gson().fromJson(json, ErrorResponse::class.java)
                        error.exception = Exception(json)
                        return Pair(
                            Failure.Error404NotFound,
                            error,
                        )
                    }

                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        val json = response.errorBody()?.string()
                        val error = Gson().fromJson(json, ErrorResponse::class.java)
                        error.exception = Exception(json)
                        return Pair(
                            Failure.ServerError,
                            error,
                        )
                    }

                    HttpURLConnection.HTTP_NOT_ACCEPTABLE->{
                        val json = response.errorBody()?.string()
                        val error = Gson().fromJson(json, ErrorResponse::class.java)
                        error.exception = Exception(json)
                        Timber.d("error parsed = $error and json = $json")
                        return Pair(
                            Failure.NotAcceptable,
                            error,
                        )
                    }

                    else -> {
                        val json = response.errorBody()?.string()
                        val error = Gson().fromJson(json, ErrorResponse::class.java)
                        error.exception = Exception(json)
                        return Pair(
                            Failure.NetworkConnection,
                            error,
                        )

                    }
                }
            }
        } catch (e: IOException) {
            return Pair(Failure.NetworkConnection, ErrorResponse(e.message, e))
        }
    }
}