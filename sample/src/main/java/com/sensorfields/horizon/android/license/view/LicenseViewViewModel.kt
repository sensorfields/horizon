package com.sensorfields.horizon.android.license.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sensorfields.horizon.android.domain.License
import com.sensorfields.horizon.android.domain.LicenseRepository
import com.sensorfields.horizon.android.reduceValue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class LicenseViewViewModel(
    private val licenseRepository: LicenseRepository,
    private val license: License
) : ViewModel() {

    private val _state = MutableLiveData<LicenseViewState>(LicenseViewState(license = license))
    val state: LiveData<LicenseViewState> = _state

    init {
        observeLicense()
        refreshLicense()
    }

    private fun observeLicense() {
        licenseRepository.observeLicense(license.key)
            .onEach { license -> _state.reduceValue { copy(license = license) } }
            .launchIn(viewModelScope)
    }

    private fun refreshLicense() {
        viewModelScope.launch {
            licenseRepository.syncLicense(license.key)
        }
    }

    class Factory @Inject constructor(private val licenseRepository: LicenseRepository) {
        fun create(license: License): LicenseViewViewModel {
            return LicenseViewViewModel(licenseRepository, license)
        }
    }
}
