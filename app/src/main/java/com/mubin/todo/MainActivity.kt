package com.mubin.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mubin.todo.databinding.ActivityMainBinding
import com.mubin.todo.workManger.DataSync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavGraph()
        initToolbar()
        initWorker()

    }

    private fun initNavGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    } // initializing Navigation Controller

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setToolbarTitle()
    } // initializing toolbar with Navigation Controller

    private fun setToolbarTitle() {
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.baseline_menu)
        binding.toolbarTitle.text = getString(R.string.toolbar_title)
    }

    private fun onClicks() {

        binding.toolbar.setNavigationOnClickListener {



        }

    }

    private fun initWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val databaseUpdateRequest = OneTimeWorkRequestBuilder<DataSync>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(databaseUpdateRequest)

    }

}