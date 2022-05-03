package com.nimble.assessment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        // Observe live data
        viewModel.liveData.observe(this) {
            Log.w("MainActivity", it.toString())
        }

        viewModel.loadPharmacyList()
    }
}