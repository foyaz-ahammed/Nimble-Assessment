package com.nimble.assessment.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nimble.assessment.databinding.ActivityDetailBinding
import com.nimble.assessment.extensions.getFormattedAddress
import com.nimble.assessment.repository.entities.Response
import com.nimble.assessment.viewmodels.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity used for detail screen
 */
class DetailActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_PHARMACY_ITEM = "extra_pharmacy_item"
    }

    // Binding variable used to inflate layout
    private lateinit var binding: ActivityDetailBinding

    // View model
    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Observe pharmacy detail data
        viewModel.pharmacyInfo.observe(this) {
            updateViews(it)
        }

        // Observe load status
        viewModel.loading.observe(this) {

        }

        intent?.getStringExtra(EXTRA_PHARMACY_ITEM)?.let {
            viewModel.loadPharmacy(it)
        }
    }

    /**
     * Update UI when we get Pharmacy info
     */
    private fun updateViews(item: Response.PharmacyDetail) {
        binding.name.text = item.value.name
        binding.address.text = item.getFormattedAddress()
        binding.hours.text = item.value.pharmacyHours?.replace("\\n", "\n") ?: "N/A"
        binding.phoneNo.text = item.value.primaryPhoneNumber ?: "N/A"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}