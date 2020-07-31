package com.sensorfields.horizon.android.license.list

import com.sensorfields.horizon.android.domain.License

sealed class LicenseListAction {
    data class NavigateToLicenseView(val license: License) : LicenseListAction()
}
