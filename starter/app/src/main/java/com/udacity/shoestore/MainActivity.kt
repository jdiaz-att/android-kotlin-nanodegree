package com.udacity.shoestore

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.models.ShoeModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var shoeModel: ShoeModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var logoutMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.loginFragment, R.id.welcomeFragment,
                R.id.instructionsFragment, R.id.shoeListFragment)
                .build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        shoeModel = ViewModelProvider(this).get(ShoeModel::class.java)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, args: Bundle? ->
            if (nd.id == R.id.shoeListFragment) {
                enableMenu()
            } else {
                disableMenu()
            }
        }

        Timber.plant(Timber.DebugTree())
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        if (menu != null) {
            logoutMenuItem = menu.findItem(R.id.loginFragment)
            if (navController.graph.startDestination == navController.currentDestination?.id) {
                disableMenu()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navResult = NavigationUI.onNavDestinationSelected(item, navController)
        if (navResult) shoeModel.removeAllShoes()
        return navResult || super.onOptionsItemSelected(item)
    }

    private fun enableMenu() {
        if (logoutMenuItem == null) return
        Timber.d("${navController.currentDestination?.label} - Enable login menu")
        logoutMenuItem!!.isEnabled = true
        logoutMenuItem!!.isVisible = true
    }

    private fun disableMenu() {
        if (logoutMenuItem == null) return
        Timber.d("${navController.currentDestination?.label} - Disable login menu")
        logoutMenuItem!!.isEnabled = false
        logoutMenuItem!!.isVisible = false
    }
}
