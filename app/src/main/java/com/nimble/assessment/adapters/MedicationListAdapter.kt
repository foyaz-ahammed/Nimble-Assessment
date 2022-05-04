package com.nimble.assessment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nimble.assessment.databinding.RowItemMedicationBinding
import com.nimble.assessment.repository.entities.Medication

/**
 * [ListAdapter] used to show a list of medications
 */
class MedicationListAdapter(private var itemCheckChangedListener: ((item: Medication) -> Unit)? = null)
    : ListAdapter<Medication, MedicationListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemMedicationBinding.inflate(
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

    fun setItemCheckChangedListener(listener: ((item: Medication) -> Unit)?) {
        itemCheckChangedListener = listener
    }

    inner class ViewHolder(private val binding: RowItemMedicationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Medication) {
            binding.name.text = item.name

            binding.root.setOnClickListener {
                itemCheckChangedListener?.invoke(item)
            }
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<Medication>() {
        override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem.name == newItem.name
        }
    }
}