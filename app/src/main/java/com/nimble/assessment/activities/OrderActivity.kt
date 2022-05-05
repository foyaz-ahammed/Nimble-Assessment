package com.nimble.assessment.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nimble.assessment.adapters.MedicationListAdapter
import com.nimble.assessment.databinding.ActivityOrderBinding
import com.nimble.assessment.repository.entities.LoadResult
import com.nimble.assessment.repository.entities.Medication
import com.nimble.assessment.repository.entities.Response
import com.nimble.assessment.viewmodels.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for order page
 */
class OrderActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_PHARMACY_LIST = "extra_pharmacy_list"
    }

    // Binding variable
    private lateinit var binding: ActivityOrderBinding

    // View model
    private val viewModel by viewModel<OrderViewModel>()

    // Adapters
    private val selectedAdapter = MedicationListAdapter()
    private val remainingAdapter = MedicationListAdapter()

    // Selected Medication List
    private val selectedMedicationList = ArrayList<Medication>()

    // Remaining Medication List
    private val remainingMedicationList = ArrayList<Medication>()

    // Pharmacy List
    private var pharmacyList: ArrayList<Response.SimplePharmacy>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set adapters to recycler views
        binding.selectedRecyclerView.adapter = selectedAdapter
        binding.remainingRecyclerView.adapter = remainingAdapter

        selectedAdapter.setItemCheckChangedListener {
            remainingMedicationList.add(it)
            remainingMedicationList.sortBy { item -> item.name }
            selectedMedicationList.remove(it)
            selectedAdapter.submitList(selectedMedicationList.clone() as ArrayList<Medication>)
            remainingAdapter.submitList(remainingMedicationList.clone() as ArrayList<Medication>)
        }
        remainingAdapter.setItemCheckChangedListener {
            selectedMedicationList.add(it)
            selectedMedicationList.sortBy { item -> item.name }
            remainingMedicationList.remove(it)
            selectedAdapter.submitList(selectedMedicationList.clone() as ArrayList<Medication>)
            remainingAdapter.submitList(remainingMedicationList.clone() as ArrayList<Medication>)
        }

        binding.btnOrder.setOnClickListener {
            if (selectedMedicationList.isEmpty()) {
                Toast.makeText(this, "You should select at least one medication", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Order medications
            viewModel.orderMedications(pharmacyList ?: emptyList(), selectedMedicationList)
            finish()
        }
        binding.btnRetry.setOnClickListener {
            viewModel.loadMedications()
        }

        // Observe medication list data
        viewModel.medicationList.observe(this) {
            selectedMedicationList.clear()
            remainingMedicationList.clear()
            remainingMedicationList.addAll(it)
            remainingAdapter.submitList(remainingMedicationList.clone() as ArrayList<Medication>)
            selectedAdapter.submitList(selectedMedicationList.clone() as ArrayList<Medication>)
        }

        // Observe loading status
        viewModel.loading.observe(this) {
            binding.contentViews.isVisible = it == LoadResult.SUCCESS
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.errorViews.isVisible = it == LoadResult.FAILURE
        }

        pharmacyList = intent?.getParcelableArrayListExtra(EXTRA_PHARMACY_LIST)

        // Load medications
        if (savedInstanceState == null) {
            viewModel.loadMedications()
        }
    }
}