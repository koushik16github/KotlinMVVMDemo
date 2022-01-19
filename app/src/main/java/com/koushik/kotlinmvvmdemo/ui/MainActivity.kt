package com.koushik.kotlinmvvmdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.koushik.kotlinmvvmdemo.R
import com.koushik.kotlinmvvmdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.fragment)
    }

    override fun onBackPressed() {
        if (navController.currentDestination!!.id == R.id.coinListFragment) {
            finish()
        } else {
            navController.navigateUp()
        }
    }
}