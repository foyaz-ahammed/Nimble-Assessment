package com.nimble.assessment.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nimble.assessment.databinding.ActivityDetailBinding
import com.nimble.assessment.extensions.getFormattedAddress
import com.nimble.assessment.repository.entities.LoadResult
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

    //
    private var _pharmacyId: String? = null

    // View model
    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Load data again when we fail loading data
        binding.btnRetry.setOnClickListener {
            _pharmacyId?.let { viewModel.loadPharmacy(it) }
        }

        // Observe pharmacy detail data
        viewModel.pharmacyInfo.observe(this) {
            updateViews(it)
        }

        // Observe load status
        viewModel.loading.observe(this) {
            binding.contentViews.isVisible = it == LoadResult.SUCCESS
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.errorViews.isVisible = it == LoadResult.FAILURE
        }

        intent?.getStringExtra(EXTRA_PHARMACY_ITEM)?.let {
            _pharmacyId = it

            // We need to avoid loading data again when the activity is recreated
            if (savedInstanceState == null) viewModel.loadPharmacy(it)
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