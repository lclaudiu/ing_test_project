package com.claudiuluca.ingtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.api.ChannelEntity
import com.claudiuluca.ingtest.databinding.ChannelsFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChannelsFragment : Fragment() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var channelsFragmentBinding: ChannelsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        channelsFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.channels_fragment, container, false)

        channelsFragmentBinding.rvChannels.adapter = ChannelsAdapter()

        createAdapterListSource()

        return channelsFragmentBinding.root
    }

    private fun createAdapterListSource() {
        val selectedChannels = mutableListOf<ChannelEntity>()
        viewModel.selectedTargets.map { target ->
            target.channels.forEach { channel ->
                selectedChannels.add(
                    ChannelEntity(
                        channelName = channel,
                        targetName = target.title,
                        selected = viewModel.getSelectedCampaign(channel, target.title) != null
                    )
                )
            }
        }

        viewModel.setSelectedChannels(selectedChannels)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        channelsFragmentBinding.viewmodel = viewModel
        channelsFragmentBinding.lifecycleOwner = this

        channelsFragmentBinding.btClear.setOnClickListener {
            (channelsFragmentBinding.rvChannels.adapter as ChannelsAdapter).clearSelection()
            viewModel.clearCampaigns()
            setFabVisibility()
        }

        channelsFragmentBinding.fbSendEmail.setOnClickListener {
            it.findNavController().navigate(R.id.action_channelsFragment_to_reviewFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        setFabVisibility()
    }

    private fun setFabVisibility() {
        fbSendEmail.visibility =
            if (viewModel.selectedCampaigns.isNotEmpty()) View.VISIBLE else View.INVISIBLE
    }
}