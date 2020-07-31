package com.sensorfields.horizon.android.domain.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sensorfields.horizon.android.domain.License
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class LicenseDto(
    @PrimaryKey val key: String,
    val name: String,
    val body: String = ""
)

fun LicenseDto.toModel(): License {
    return License(
        key = key,
        name = name,
        body = body
    )
}
