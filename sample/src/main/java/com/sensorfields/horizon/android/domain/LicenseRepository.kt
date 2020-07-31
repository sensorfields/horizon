package com.sensorfields.horizon.android.domain

import com.sensorfields.horizon.android.domain.data.dto.toModel
import com.sensorfields.horizon.android.domain.data.local.LicenseDao
import com.sensorfields.horizon.android.domain.data.remote.LicenseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LicenseRepository @Inject constructor(
    private val licenseDao: LicenseDao,
    private val licenseApi: LicenseApi
) {
    fun observeLicenses(): Flow<List<License>> {
        return licenseDao.observeLicenses().map { licenses ->
            licenses.map { license -> license.toModel() }
        }
    }

    fun observeLicense(key: String): Flow<License> {
        return licenseDao.observeLicense(key).map { license -> license.toModel() }
    }

    suspend fun syncLicenses() {
        licenseDao.replaceLicenses(licenseApi.getLicenses())
    }

    suspend fun syncLicense(key: String) {
        licenseDao.updateLicense(licenseApi.getLicense(key))
    }
}
