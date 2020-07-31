package com.sensorfields.horizon.android.license.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sensorfields.horizon.android.ActionLiveData
import com.sensorfields.horizon.android.domain.License
import com.sensorfields.horizon.android.domain.LicenseRepository
import com.sensorfields.horizon.android.reduceValue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


class LicenseListViewModel @Inject constructor(
    private val licenseRepository: LicenseRepository
) : ViewModel() {

    private val _state = MutableLiveData<LicenseListState>(LicenseListState())
    val state: LiveData<LicenseListState> = _state

    private val _action = ActionLiveData<LicenseListAction>()
    val action: LiveData<LicenseListAction> = _action

    init {
        observeLicenses()
        refreshLicenses()
    }

    private fun observeLicenses() {
        licenseRepository.observeLicenses()
            .onEach { licenses -> _state.reduceValue { copy(licenses = licenses) } }
            .launchIn(viewModelScope)
    }

    fun refreshLicenses() {
        viewModelScope.launch {
            _state.reduceValue { copy(isRefreshing = true) }
            licenseRepository.syncLicenses()
            _state.reduceValue { copy(isRefreshing = false) }
        }
    }

    fun onLicenseClicked(license: License) {
        _action.postValue(LicenseListAction.NavigateToLicenseView(license))
    }
}
