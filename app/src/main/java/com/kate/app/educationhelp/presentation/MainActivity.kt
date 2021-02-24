package com.kate.app.educationhelp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDrawerLayout()

        lifecycleScope.launch {
            Log.d("KEK", MyBackendRepository.getAllTopics().toString())
        }

    }

    private fun setupDrawerLayout() {
        binding.navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }
}