package com.sensorfields.horizon.android

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sensorfields.horizon.android.domain.data.local.ApplicationDb
import com.sensorfields.horizon.android.domain.data.local.LicenseDao
import com.sensorfields.horizon.android.domain.data.remote.LicenseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun json(): Json = Json { ignoreUnknownKeys = true }
}

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun applicationDb(@ApplicationContext context: Context): ApplicationDb {
        return Room.databaseBuilder(context, ApplicationDb::class.java, "application").build()
    }

    @Singleton
    @Provides
    fun licenseDao(applicationDb: ApplicationDb): LicenseDao = applicationDb.licenseDao()
}

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun retrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun licenseApi(retrofit: Retrofit): LicenseApi = retrofit.create()
}
