package com.nimble.assessment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nimble.assessment.databinding.RowItemPharmacyBinding
import com.nimble.assessment.repository.entities.Response

/**
 * [ListAdapter] used to show a list of Pharmacy
 */
class PharmacyListAdapter(
    private var itemClickListener: ((item: Response.SimplePharmacy) -> Unit)? = null,
    private var itemCheckChangeListener: ((item: Response.SimplePharmacy, checked: Boolean) -> Unit)? = null
): ListAdapter<Response.SimplePharmacy, PharmacyListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemPharmacyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun setItemClickListener(listener: ((item: Response.SimplePharmacy) -> Unit)?) {
        this.itemClickListener = listener
    }

    fun setItemCheckChangeListener(listener: ((item: Response.SimplePharmacy, checked: Boolean) -> Unit)?) {
        this.itemCheckChangeListener = listener
    }

    inner class ViewHolder(private val binding: RowItemPharmacyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Response.SimplePharmacy) {
            binding.name.text = item.name
            binding.check.isInvisible = item.isOrdered
            binding.ordered.isVisible = item.isOrdered

            binding.check.setOnCheckedChangeListener { _, checked ->
                itemCheckChangeListener?.invoke(item, checked)
            }
            binding.root.setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<Response.SimplePharmacy>() {
        override fun areItemsTheSame(
            oldItem: Response.SimplePharmacy,
            newItem: Response.SimplePharmacy
        ): Boolean {
            return oldItem.pharmacyId == newItem.pharmacyId
        }

        override fun areContentsTheSame(
            oldItem: Response.SimplePharmacy,
            newItem: Response.SimplePharmacy
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.selected == newItem.selected && oldItem.isOrdered == newItem.isOrdered
        }
    }
}