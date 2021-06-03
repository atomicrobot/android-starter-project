package com.atomicrobot.carbon

import android.os.Bundle
import com.atomicrobot.carbon.ui.BaseActivity
import com.atomicrobot.carbon.ui.main.MainViewModel
import com.atomicrobot.carbon.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModel()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)
    }
}