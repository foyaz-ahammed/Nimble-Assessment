package com.nimble.assessment.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nimble.assessment.adapters.MedicationListAdapter
import com.nimble.assessment.databinding.ActivityOrderBinding
import com.nimble.assessment.repository.entities.LoadResult
import com.nimble.assessment.repository.entities.Medication
import com.nimble.assessment.viewmodels.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for order page
 */
class OrderActivity: AppCompatActivity() {
    // Binding variable
    private lateinit var binding: ActivityOrderBinding

    // View model
    private val viewModel by viewModel<OrderViewModel>()

    // Adapters
    private val selectedAdapter = MedicationListAdapter()
    private val remainingAdapter = MedicationListAdapter()

    // Selected List
    private val selectedList = ArrayList<Medication>()

    // Remaining List
    private val remainingList = ArrayList<Medication>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set adapters to recycler views
        binding.selectedRecyclerView.adapter = selectedAdapter
        binding.remainingRecyclerView.adapter = remainingAdapter

        selectedAdapter.setItemCheckChangedListener {
            remainingList.add(it)
            remainingList.sortBy { item -> item.name }
            selectedList.remove(it)
            selectedAdapter.submitList(selectedList.clone() as ArrayList<Medication>)
            remainingAdapter.submitList(remainingList.clone() as ArrayList<Medication>)
        }
        remainingAdapter.setItemCheckChangedListener {
            selectedList.add(it)
            selectedList.sortBy { item -> item.name }
            remainingList.remove(it)
            selectedAdapter.submitList(selectedList.clone() as ArrayList<Medication>)
            remainingAdapter.submitList(remainingList.clone() as ArrayList<Medication>)
        }

        binding.btnOrder.setOnClickListener {
            if (selectedList.isEmpty()) {
                Toast.makeText(this, "You should select at least one medication", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            finish()
        }

        // Observe medication list data
        viewModel.medicationList.observe(this) {
            selectedList.clear()
            remainingList.clear()
            remainingList.addAll(it)
            remainingAdapter.submitList(remainingList.clone() as ArrayList<Medication>)
            selectedAdapter.submitList(selectedList.clone() as ArrayList<Medication>)
        }

        // Observe loading status
        viewModel.loading.observe(this) {
            binding.contentViews.isVisible = it == LoadResult.SUCCESS
            binding.progressBar.isVisible = it == LoadResult.LOADING
        }

        // Load medications
        if (savedInstanceState == null) {
            viewModel.loadMedications()
        }
    }
}