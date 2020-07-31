package com.sensorfields.horizon.android.license.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sensorfields.horizon.android.R
import com.sensorfields.horizon.android.databinding.LicenseListItemBinding
import com.sensorfields.horizon.android.domain.License

class LicenseListAdapter(
    private val onItemClickListener: (License) -> Unit
) : ListAdapter<License, LicenseViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseViewHolder {
        return LicenseViewHolder(parent) { position -> onItemClickListener(getItem(position)) }
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LicenseViewHolder(
    parent: ViewGroup,
    onClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.license_list_item, parent, false)
) {
    private val binding = LicenseListItemBinding.bind(itemView).apply {
        root.setOnClickListener { onClickListener(adapterPosition) }
    }

    fun bind(license: License) {
        binding.licenseNameView.text = license.name
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<License>() {

    override fun areItemsTheSame(oldItem: License, newItem: License): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: License, newItem: License): Boolean {
        return oldItem == newItem
    }
}
