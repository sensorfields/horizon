package com.sensorfields.horizon.android.license.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sensorfields.horizon.android.R
import com.sensorfields.horizon.android.databinding.LicenseListFragmentBinding
import com.sensorfields.horizon.android.producer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LicenseListFragment : Fragment(R.layout.license_list_fragment) {

    @Inject
    lateinit var factory: Provider<LicenseListViewModel>

    private val viewModel by viewModels<LicenseListViewModel> { producer { factory.get() } }

    private val adapter = LicenseListAdapter(
        onItemClickListener = { license -> viewModel.onLicenseClicked(license) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(LicenseListFragmentBinding.bind(view)) {
            setupViews()
            viewModel.state.observe(viewLifecycleOwner) { onState(it) }
        }
        viewModel.action.observe(viewLifecycleOwner, ::onAction)
    }

    private fun LicenseListFragmentBinding.setupViews() {
        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshLicenses() }

        licensesRecyclerView.layoutManager = LinearLayoutManager(root.context)
        licensesRecyclerView.adapter = adapter
    }

    private fun LicenseListFragmentBinding.onState(state: LicenseListState) {
        swipeRefreshLayout.isRefreshing = state.isRefreshing
        adapter.submitList(state.licenses)
    }

    private fun onAction(action: LicenseListAction) {
        when (action) {
            is LicenseListAction.NavigateToLicenseView -> {
                findNavController().navigate(
                    LicenseListFragmentDirections.licenseView(action.license)
                )
            }
        }
    }
}
