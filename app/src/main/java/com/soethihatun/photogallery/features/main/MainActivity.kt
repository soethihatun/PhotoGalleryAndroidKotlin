package com.soethihatun.photogallery.features.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.soethihatun.photogallery.R
import com.soethihatun.photogallery.core.platform.BaseActivity
import com.soethihatun.photogallery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        // Supports Android Up Button in ActionBar with NavController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    /**
     * Supports Android Up Button
     * */
    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}
