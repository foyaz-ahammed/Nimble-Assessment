package com.nimble.assessment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nimble.assessment.R
import com.nimble.assessment.databinding.ActivityMainBinding

/**
 * Main activity for home screen
 */
class MainActivity : AppCompatActivity() {
    // Binding variable
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}