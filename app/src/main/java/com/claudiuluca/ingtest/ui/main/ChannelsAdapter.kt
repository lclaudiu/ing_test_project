package com.claudiuluca.ingtest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.api.ChannelEntity
import com.claudiuluca.ingtest.databinding.ListItemChannelBinding
import com.claudiuluca.ingtest.util.BindableAdapter
import kotlinx.android.synthetic.main.list_item_target.view.*

class ChannelsAdapter :
    ListAdapter<ChannelEntity, ChannelsAdapter.ViewHolder>(ChannelsDiffCallback()),
    BindableAdapter<List<ChannelEntity>>,
    AdapterInterface {

    override fun setData(list: List<ChannelEntity>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_channel, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { channel ->
            with(holder) {
                bind(createOnClickListener(channel), channel)
            }
        }
    }

    private fun createOnClickListener(channel: ChannelEntity): View.OnClickListener {
        return View.OnClickListener { card ->
            val action = ChannelsFragmentDirections.actionChannelsFragmentToCampaignsFragment(
                channel.targetName,
                channel.channelName
            )
            card.findNavController().navigate(action)
        }
    }

    class ViewHolder(
        private val binding: ListItemChannelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, channel: ChannelEntity) {
            with(binding) {
                clickListener = listener
                this.channel = channel
                executePendingBindings()
                binding.root.cvContainer.setOnLongClickListener {
                    false
                }
                binding.root.cvContainer.isChecked = channel.selected
            }
        }
    }

    override fun clearSelection() {
        currentList.forEach { channel ->
            channel.selected = false
        }

        notifyDataSetChanged()
    }
}

private class ChannelsDiffCallback : DiffUtil.ItemCallback<ChannelEntity>() {

    override fun areItemsTheSame(
        oldItem: ChannelEntity,
        newItem: ChannelEntity
    ): Boolean {
        return oldItem.channelName == newItem.channelName
                && oldItem.targetName == newItem.targetName
    }

    override fun areContentsTheSame(
        oldItem: ChannelEntity,
        newItem: ChannelEntity
    ): Boolean {
        return oldItem == newItem
    }
}