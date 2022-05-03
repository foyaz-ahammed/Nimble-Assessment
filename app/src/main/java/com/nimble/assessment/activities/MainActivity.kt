package com.nimble.assessment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}