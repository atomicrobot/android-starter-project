package com.mycompany.myapp.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.mycompany.myapp.R
import com.mycompany.myapp.ui.BaseActivity
import com.mycompany.myapp.ui.SimpleSnackbarMessage
import com.mycompany.myapp.ui.main.MainFragment.MainFragmentHost
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainFragmentHost {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: MainActivityBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.restoreState(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.executePendingBindings()

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = applicationContext.packageName

        viewModel.snackbarMessage.observe(this, object : SimpleSnackbarMessage.SnackbarObserver {
            override fun onNewMessage(message: String) {
                Snackbar.make(binding.rootLayout, message, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}