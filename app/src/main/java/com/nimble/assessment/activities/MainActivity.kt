package com.nimble.assessment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nimble.assessment.adapters.PharmacyListAdapter
import com.nimble.assessment.database.entities.Order
import com.nimble.assessment.databinding.ActivityMainBinding
import com.nimble.assessment.repository.entities.Response
import com.nimble.assessment.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main activity for home screen
 */
class MainActivity : AppCompatActivity() {
    // Binding variable
    private lateinit var binding: ActivityMainBinding

    // View model
    private val viewModel by viewModel<MainViewModel>()

    private val pharmacyList = ArrayList<Response.SimplePharmacy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PharmacyListAdapter()
        binding.recyclerView.adapter = adapter

        // Add click listener when clicking order button (Show order page)
        binding.btnOrder.setOnClickListener {
            val selectedPharmacyList = pharmacyList.filter { it.selected } as ArrayList<Response.SimplePharmacy>
            if (selectedPharmacyList.isEmpty()) {
                Toast.makeText(this, "You should select at least one pharmacy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, OrderActivity::class.java).apply {
                putParcelableArrayListExtra(OrderActivity.EXTRA_PHARMACY_LIST, selectedPharmacyList)
            }
            startActivity(intent)
        }

        // Add item click listener (Show detail page)
        adapter.setItemClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_PHARMACY_ITEM, it.pharmacyId)
            }
            startActivity(intent)
        }

        // Add item check change listener
        adapter.setItemCheckChangeListener { item, checked ->
            val index = pharmacyList.indexOf(item)
            if (index >= 0) {
                pharmacyList[index].selected = checked
            }
        }

        // Observe pharmacy list
        viewModel.pharmacyList.observe(this) {
            pharmacyList.clear()
            pharmacyList.addAll(it)

            Log.w("MainActivity", pharmacyList.toString())
            adapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadPharmacyList()
    }
}