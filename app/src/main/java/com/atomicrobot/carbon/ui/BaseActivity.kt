package com.atomicrobot.carbon.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.atomicrobot.carbon.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onSupportNavigateUp() =
            Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigateUp()
}
