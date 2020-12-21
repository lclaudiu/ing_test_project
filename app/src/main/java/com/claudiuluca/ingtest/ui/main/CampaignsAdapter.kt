package com.claudiuluca.ingtest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.api.Campaign
import com.claudiuluca.ingtest.databinding.ListItemCampaignBinding
import com.claudiuluca.ingtest.util.BindableAdapter
import kotlinx.android.synthetic.main.list_item_target.view.*

class CampaignsAdapter(val viewModel: MainViewModel, val channel: String, val target: String) :
    ListAdapter<Campaign, CampaignsAdapter.ViewHolder>(CampaignsDiffCallback()),
    BindableAdapter<List<Campaign>>,
    AdapterInterface {

    private var selectedCampaign: Campaign? = null

    override fun setData(list: List<Campaign>) {
        selectedCampaign = viewModel.getSelectedCampaign(channel, target)?.campaign
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_campaign, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { campaign ->
            with(holder) {
                bind(createOnClickListener(campaign), campaign, selectedCampaign)
            }
        }
    }

    private fun createOnClickListener(campaign: Campaign): View.OnClickListener {
        return View.OnClickListener {
            val localSelectedCampaign = selectedCampaign
            localSelectedCampaign?.let {
                selectedCampaign = if (localSelectedCampaign == campaign) {
                    null
                } else {
                    campaign
                }
            } ?: kotlin.run {
                selectedCampaign = campaign
            }

            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        private val binding: ListItemCampaignBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, campaign: Campaign, selectedCampaign: Campaign?) {
            with(binding) {
                clickListener = listener
                this.campaign = campaign
                selectedCampaign?.let {
                    binding.cvContainer.isChecked = it == campaign
                } ?: run {
                    binding.cvContainer.isChecked = false
                }

                executePendingBindings()
                binding.root.cvContainer.setOnLongClickListener {
                    false
                }
            }
        }
    }

    override fun clearSelection() {
        selectedCampaign?.let {
            selectedCampaign = null
            notifyDataSetChanged()
        }
    }

    fun getSelection(): Campaign? {
        return selectedCampaign
    }
}

private class CampaignsDiffCallback : DiffUtil.ItemCallback<Campaign>() {

    override fun areItemsTheSame(
        oldItem: Campaign,
        newItem: Campaign
    ): Boolean {
        return oldItem.price == newItem.price
                && oldItem.details == newItem.details
    }

    override fun areContentsTheSame(
        oldItem: Campaign,
        newItem: Campaign
    ): Boolean {
        return oldItem == newItem
    }
}