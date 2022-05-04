package com.nimble.assessment.modules

import com.nimble.assessment.repository.NimbleRepository
import com.nimble.assessment.repository.apis.PharmacyAPI
import com.nimble.assessment.util.Constants
import com.nimble.assessment.viewmodels.DetailViewModel
import com.nimble.assessment.viewmodels.MainViewModel
import com.nimble.assessment.viewmodels.OrderViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { NimbleRepository(androidContext(), get()) }
}

val networkModule = module {
    single { provideAPI(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideOkHttpClient() }
    single { provideMoshi() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { OrderViewModel(get()) }
}

/**
 * @return [Retrofit] instance
 */
private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(okHttpClient)
    .build()

/**
 * @return [OkHttpClient] instance
 */
private fun provideOkHttpClient() = OkHttpClient.Builder()
    .readTimeout(10L, TimeUnit.SECONDS)
    .readTimeout(15L, TimeUnit.SECONDS)
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    )
    .build()

/**
 * @return [Moshi] instance
 */
private fun provideMoshi(): Moshi = Moshi.Builder().build()

/**
 * @return [PharmacyAPI] instance
 */
private fun provideAPI(retrofit: Retrofit): PharmacyAPI = retrofit.create(PharmacyAPI::class.java)