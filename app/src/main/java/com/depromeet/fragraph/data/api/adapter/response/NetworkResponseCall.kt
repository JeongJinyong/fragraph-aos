package com.depromeet.fragraph.data.api.adapter.response

import com.depromeet.fragraph.data.api.response.ErrorResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<FragraphNetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<FragraphNetworkResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(FragraphNetworkResponse.Success(body))
                    )
                } else {
                    Timber.d("response is not successful, errorBody: $error")
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            val eBody = errorConverter.convert(error)
                            Timber.d("convert success, error body: $eBody")
                            eBody
                        } catch (ex: Exception) {
                            Timber.e(ex, "convert Error")
                            null
                        }
                    }
                    if (errorBody != null) {
                        Timber.d("error response has body")
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(FragraphNetworkResponse.ApiError(errorBody, code))
                        )
                    } else {
                        Timber.d("error response does not have body")
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(FragraphNetworkResponse.ApiError(ErrorResponse("UNKNOWN", "error response does not have body", null) as E, code))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                when (throwable) {
                    is IOException -> FragraphNetworkResponse.NetworkError(throwable)
                    else -> FragraphNetworkResponse.UnknownError(throwable)
                }
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<FragraphNetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}