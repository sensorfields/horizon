package com.sensorfields.horizon.android.domain.data.remote

import com.sensorfields.horizon.android.domain.data.dto.LicenseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LicenseApi {

    @GET("licenses")
    suspend fun getLicenses(): List<LicenseDto>

    @GET("licenses/{key}")
    suspend fun getLicense(@Path("key") key: String): LicenseDto
}
