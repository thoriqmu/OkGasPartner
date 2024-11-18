package com.kewirausahaan.okgaspartner

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.kewirausahaan.okgaspartner.databinding.ActivityMainBinding
import com.kewirausahaan.okgaspartner.ui.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Periksa status login
        if (auth.currentUser == null) {
            // Arahkan ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Tutup MainActivity agar tidak bisa kembali dengan tombol back
            return // Hentikan eksekusi onCreate()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_move,
                R.id.navigation_history,
                R.id.navigation_profile -> {
                    binding.navView.visibility = View.VISIBLE
                }
                else -> {
                    binding.navView.visibility = View.GONE
                }
            }
        }

        binding.navView.setupWithNavController(navController)
    }
}