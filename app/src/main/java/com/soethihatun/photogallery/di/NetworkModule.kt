package com.soethihatun.photogallery.di

import android.content.Context
import com.google.gson.Gson
import com.soethihatun.photogallery.BuildConfig
import com.soethihatun.photogallery.util.interceptor.InternetConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("InternetConnectivityInterceptor")
    fun provideInternetConnectivityInterceptor(@ApplicationContext context: Context): Interceptor {
        return InternetConnectivityInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(@Named("InternetConnectivityInterceptor") internetConnectivityInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.networkInterceptors().add(loggingInterceptor)
        }

        builder.addInterceptor(internetConnectivityInterceptor)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(okHttpClient)
            .build()
    }
}
