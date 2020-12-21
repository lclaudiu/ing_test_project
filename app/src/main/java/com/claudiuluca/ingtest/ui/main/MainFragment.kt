package com.claudiuluca.ingtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.Result
import com.claudiuluca.ingtest.data.api.Channel
import com.claudiuluca.ingtest.data.api.TargetEntity
import com.claudiuluca.ingtest.databinding.MainFragmentBinding
import com.claudiuluca.ingtest.util.Event
import com.claudiuluca.ingtest.util.toast
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MainFragment : Fragment() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var mainFragmentBinding: MainFragmentBinding

    private var targetingsSourceObserver: Observer<List<TargetEntity>> =
        Observer { targetingsList ->
            Timber.i("$targetingsList.size")
            if (!targetingsList.isNullOrEmpty()) {
                mainFragmentBinding.groupDataShow.visibility = View.VISIBLE
                mainFragmentBinding.pbLoading.visibility = View.GONE
            } else {
                mainFragmentBinding.groupDataShow.visibility = View.GONE
                mainFragmentBinding.pbLoading.visibility = View.VISIBLE
            }
        }

    private var channelsSourceObserver: Observer<List<Channel>> = Observer { channels ->
        Timber.i("$channels.size")
    }

    private var loadingInfoObserver: Observer<Event<Result<*>>> = Observer { }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.main_fragment, container, false)

        mainFragmentBinding.rvTargetings.adapter = TargetsAdapter()

        return mainFragmentBinding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.clearSelectedTargets()
    }

    override fun onResume() {
        super.onResume()

        setFabVisibility()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFragmentBinding.viewmodel = viewModel
        mainFragmentBinding.lifecycleOwner = this

        viewModel.targetings.observe(
            viewLifecycleOwner,
            targetingsSourceObserver
        )

        viewModel.channels.observe(
            viewLifecycleOwner,
            channelsSourceObserver
        )

        viewModel.loadingInfoFromNetworkEvent.observe(
            viewLifecycleOwner,
            loadingInfoObserver
        )

        btClear.setOnClickListener {
            (mainFragmentBinding.rvTargetings.adapter as TargetsAdapter).clearSelection()
            viewModel.clearSelectedTargets()
            viewModel.clearSelectedChannels()
            viewModel.clearCampaigns()
            setFabVisibility()
        }

        btShowChannels.setOnClickListener {
            val list = (mainFragmentBinding.rvTargetings.adapter as TargetsAdapter).getSelections()
            if (list.isNotEmpty()) {
                viewModel.setSelectedTargets(list)
                it.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToChannelsFragment())
            } else {
                context?.toast(getString(R.string.please_make_a_selection))
            }
        }

        fbSendEmail.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_reviewFragment)
        }
    }

    private fun setFabVisibility() {
        fbSendEmail.visibility =
            if (viewModel.selectedCampaigns.isNotEmpty()) View.VISIBLE else View.INVISIBLE
    }
}