package com.claudiuluca.ingtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.api.SelectedCampaign
import com.claudiuluca.ingtest.databinding.CampaignsFragmentBinding
import kotlinx.android.synthetic.main.campaigns_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CampaignsFragment : Fragment() {
    private val args: CampaignsFragmentArgs by navArgs()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var campaignsFragmentBinding: CampaignsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        campaignsFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.campaigns_fragment, container, false)

        campaignsFragmentBinding.rvCampaigns.adapter = CampaignsAdapter(
            viewModel = viewModel,
            channel = args.channel,
            target = args.target
        )

        return campaignsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val campaigns = viewModel.channels.value?.first { channel ->
            channel.channelName == args.channel
        }?.campaigns

        campaignsFragmentBinding.viewmodel = viewModel
        campaignsFragmentBinding.channel = args.channel
        campaignsFragmentBinding.campaigns = campaigns
        campaignsFragmentBinding.lifecycleOwner = this

        btClear.setOnClickListener {
            (campaignsFragmentBinding.rvCampaigns.adapter as CampaignsAdapter).clearSelection()
        }

        btSelect.setOnClickListener {
            val initialSelectedCampaign =
                viewModel.getSelectedCampaign(args.channel, args.target)?.campaign
            val currentSelectedCampaign =
                (campaignsFragmentBinding.rvCampaigns.adapter as CampaignsAdapter).getSelection()

            if (initialSelectedCampaign != null
                && currentSelectedCampaign != null
            ) {
                if (initialSelectedCampaign != currentSelectedCampaign) {
                    viewModel.addOrRemoveASelectedCampaign(
                        SelectedCampaign(args.target, args.channel, initialSelectedCampaign)
                    )
                    viewModel.addOrRemoveASelectedCampaign(
                        SelectedCampaign(args.target, args.channel, currentSelectedCampaign)
                    )
                }
            } else {
                if (initialSelectedCampaign != null
                    && currentSelectedCampaign == null
                ) {
                    viewModel.addOrRemoveASelectedCampaign(
                        SelectedCampaign(args.target, args.channel, initialSelectedCampaign)
                    )
                } else if (initialSelectedCampaign == null
                    && currentSelectedCampaign != null
                ) {
                    viewModel.addOrRemoveASelectedCampaign(
                        SelectedCampaign(args.target, args.channel, currentSelectedCampaign)
                    )
                }
            }

            findNavController().popBackStack()
        }
    }
}