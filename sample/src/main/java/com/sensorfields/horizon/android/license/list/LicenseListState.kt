package com.sensorfields.horizon.android.license.list

import com.sensorfields.horizon.android.domain.License

data class LicenseListState(
    val isRefreshing: Boolean = false,
    val licenses: List<License> = listOf()
)
