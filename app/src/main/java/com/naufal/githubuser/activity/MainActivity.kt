package com.naufal.githubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.naufal.githubuser.R
import com.naufal.githubuser.databinding.ActivityMainBinding
import com.naufal.githubuser.fragment.FavoriteFragment
import com.naufal.githubuser.fragment.FavoriteFragment.Companion.EXTRA_STATE

class MainActivity : AppCompatActivity() {


    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private var favFragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bottomNavSettings()

    }

    private fun bottomNavSettings() {
        val navController = findNavController(R.id.fragment)
        binding.btmNavigation.setupWithNavController(navController)
    }


}