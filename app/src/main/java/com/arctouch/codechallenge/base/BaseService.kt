package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.api.TmdbApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

open class BaseService {

    companion object {

        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "en-US"

        private var httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url: HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("language", DEFAULT_LANGUAGE)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()

            chain.proceed(request)
        }.build()

        val api: TmdbApi = Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
    }
}
