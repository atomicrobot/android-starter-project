package com.atomicrobot.carbon.ui.devsettings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.atomicrobot.carbon.R
import com.atomicrobot.carbon.ui.BaseFragment
import com.atomicrobot.carbon.ui.devsettings.DevSettingsFragmentBinding

class DevSettingsFragment : BaseFragment() {
    interface DevSettingsFragmentHost

    private lateinit var viewModel: DevSettingsViewModel
    private lateinit var binding: DevSettingsFragmentBinding
    private var host: DevSettingsFragmentHost? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as DevSettingsFragmentHost
    }

    override fun onDetach() {
        host = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = getViewModel(DevSettingsViewModel::class)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev_settings, container, false)
        binding.vm = viewModel

        return binding.root
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
