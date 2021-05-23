package com.kate.app.educationhelp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    private val drawerLayout by lazy {
        binding.drawerLayout
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.navView.setupWithNavController(navController)

        setupDrawerLayout()
    }

    private fun setupDrawerLayout() {
        val topLevelFragments =
            mutableSetOf(R.id.allTopicsFragment, R.id.allQuizesFragment, R.id.profileFragment, R.id.passedQuizesFragment, R.id.favoritesTopicsFragment)

        appBarConfiguration = AppBarConfiguration.Builder(topLevelFragments)
            .setDrawerLayout(drawerLayout)
            .build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) binding.drawerLayout.closeDrawer(
            GravityCompat.START
        ) else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}