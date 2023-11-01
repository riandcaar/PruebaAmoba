package com.felipecoronado.pruebaamoba.data.network.di

import com.felipecoronado.pruebaamoba.data.network.service.RetrofitService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {



    const val BEARER_TOKEN =
        "Bearer oeWCzmxKoBahDH99f-rsqKfEWPHqg5jrzlXMOoJNfoFuHe1SzUX7tGc2HTzZ_EyoIM0wGV-RGay7-6woRxRrcMN7p9dueoFe8Xc9MO_STEmje_Og-qoVDzihyYRwkrwKbRfDeVz0NfQMOx-DP7jiUdCzPjITQpue8Uvs7hhYZihAxUS1-IsDx7PJnwbDUbjVkh5TIDqeQA3I0b3nVoz2GdEFYrrqMQCsjdTYBFLgCF8zjnqOZUbV24LQ-KmL_7ECTGgzJ4K3C7OnzXZO1jAbnZB-qBrkejhxyZOgZSo-ZExQZFAJipcCq7I0o_ba-hKqV5uBCQ"



    private val json: Json = Json {
        ignoreUnknownKeys = true
    }
    private const val BASE_URL = "https://pay.payphonetodoesposible.com/api/"

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}
