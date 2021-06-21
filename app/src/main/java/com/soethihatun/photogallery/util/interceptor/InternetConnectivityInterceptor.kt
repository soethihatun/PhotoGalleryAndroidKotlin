package com.soethihatun.photogallery.util.interceptor

import android.content.Context
import com.soethihatun.photogallery.util.extension.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class InternetConnectivityInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!context.isNetworkConnected) {
            throw NetworkException("No Internet Connection")
        }
        return chain.proceed(originalRequest)
    }
}

class NetworkException(message: String) : IOException(message)
