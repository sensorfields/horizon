package com.sensorfields.horizon.android.license.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.sensorfields.horizon.android.R
import com.sensorfields.horizon.android.databinding.LicenseViewFragmentBinding
import com.sensorfields.horizon.android.producer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LicenseViewFragment : Fragment(R.layout.license_view_fragment) {

    @Inject
    lateinit var factory: LicenseViewViewModel.Factory

    private val args by navArgs<LicenseViewFragmentArgs>()

    private val viewModel by viewModels<LicenseViewViewModel> {
        producer { factory.create(args.license) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(LicenseViewFragmentBinding.bind(view)) {
            setupViews()
            viewModel.state.observe(viewLifecycleOwner) { onState(it) }
        }
    }

    private fun LicenseViewFragmentBinding.setupViews() {
        NavigationUI.setupWithNavController(toolbarView, findNavController())
    }

    private fun LicenseViewFragmentBinding.onState(state: LicenseViewState) {
        toolbarView.title = state.license.name
        licenseBodyView.text = state.license.body
    }
}
