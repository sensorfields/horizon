package com.sensorfields.horizon.android.domain.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sensorfields.horizon.android.domain.data.dto.LicenseDto

@Database(entities = [LicenseDto::class], version = 1)
abstract class ApplicationDb : RoomDatabase() {

    abstract fun licenseDao(): LicenseDao
}
