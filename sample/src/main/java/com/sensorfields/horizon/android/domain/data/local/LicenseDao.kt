package com.sensorfields.horizon.android.domain.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sensorfields.horizon.android.domain.data.dto.LicenseDto
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LicenseDao {

    @Query("SELECT * FROM LicenseDto ORDER BY name")
    abstract fun observeLicenses(): Flow<List<LicenseDto>>

    @Query("SELECT * FROM LicenseDto WHERE `key` = :key")
    abstract fun observeLicense(key: String): Flow<LicenseDto>

    @Transaction
    open suspend fun replaceLicenses(licenses: List<LicenseDto>) {
        clearLicenses()
        insertLicenses(licenses)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateLicense(license: LicenseDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertLicenses(licenses: List<LicenseDto>)

    @Query("DELETE FROM LicenseDto")
    protected abstract suspend fun clearLicenses()
}
