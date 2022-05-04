package com.nimble.assessment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nimble.assessment.adapters.PharmacyListAdapter
import com.nimble.assessment.databinding.ActivityMainBinding
import com.nimble.assessment.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main activity for home screen
 */
class MainActivity : AppCompatActivity() {
    // Binding variable
    lateinit var binding: ActivityMainBinding

    // View model
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PharmacyListAdapter()
        binding.recyclerView.adapter = adapter

        // Add click listener when clicking order button (Show order page)
        binding.btnOrder.setOnClickListener {

        }

        // Add item click listener (Show detail page)
        adapter.setItemClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_PHARMACY_ITEM, it.pharmacyId)
            }
            startActivity(intent)
        }

        // Observe live data
        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }

        viewModel.loadPharmacyList()
    }
}