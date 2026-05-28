package com.cipecma.trainup

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!AuthManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupère le NavController une seule fois
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Configuration de l'AppBar pour le drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_program,
                R.id.nav_home,
                R.id.nav_friends,
                R.id.nav_profil,
                R.id.nav_settings
            ),
            binding.drawerLayout
        )

        //Bouton + pour ajouter un programme
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_program) {
                binding.appBarMain.fab?.show()

                binding.appBarMain.fab?.setOnClickListener { view ->
                    Snackbar.make(view, "Ajout d'un Program", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .show()
                }
            } else {
                binding.appBarMain.fab?.hide()
            }
        }

        // Lien AppBar + NavController
        setSupportActionBar(binding.appBarMain.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Drawer NavigationView
        binding.navView?.let { navView ->
            navView.setupWithNavController(navController)

            // Interception uniquement du logout
            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_logout -> {
                        logout()
                        true
                    }
                    else -> {
                        val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                        if (handled) binding.drawerLayout?.closeDrawers()
                        handled
                    }
                }
            }
        }

        // BottomNavigationView
        binding.appBarMain.contentMain.bottomNavView?.let { bottomNav ->
            bottomNav.setupWithNavController(navController)
        }
    }

    // Options menu si nécessaire
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val navView: NavigationView? = findViewById(R.id.nav_view)
        if (navView == null) {
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        return when (item.itemId) {
            R.id.nav_settings -> { navController.navigate(R.id.nav_settings); true }
            R.id.nav_program -> { navController.navigate(R.id.nav_program); true }
            R.id.nav_friends -> { navController.navigate(R.id.nav_friends); true }
            R.id.nav_home -> { navController.navigate(R.id.nav_home); true }
            R.id.nav_profil -> { navController.navigate(R.id.nav_profil); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Support pour la flèche “up”
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Logout propre
    private fun logout() {
        AuthManager.setToken("")
        AuthManager.setUserId(0)

        getSharedPreferences("auth", MODE_PRIVATE).edit {
            remove("token")
            apply()
        }

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show()
    }
}