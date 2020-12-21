package com.claudiuluca.ingtest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.api.TargetEntity
import com.claudiuluca.ingtest.databinding.ListItemTargetBinding
import com.claudiuluca.ingtest.util.BindableAdapter
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.list_item_target.view.*

class TargetsAdapter :
    ListAdapter<TargetEntity, TargetsAdapter.ViewHolder>(TargetsDiffCallback()),
    BindableAdapter<List<TargetEntity>>,
    AdapterInterface {

    override fun setData(list: List<TargetEntity>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_target, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { target ->
            with(holder) {
                bind(createOnClickListener(target), target)
            }
        }
    }

    private fun createOnClickListener(target: TargetEntity): View.OnClickListener {
        return View.OnClickListener {card ->
            (card as MaterialCardView).isChecked = !card.isChecked
            target.selected = card.isChecked
        }
    }

    class ViewHolder(
        private val binding: ListItemTargetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, target: TargetEntity) {
            with(binding) {
                clickListener = listener
                this.target = target
                executePendingBindings()
                binding.root.cvContainer.setOnLongClickListener {
                    false
                }
                binding.root.cvContainer.isChecked = target.selected
            }
        }
    }

    override fun clearSelection() {
        currentList.forEach {target ->
            target.selected = false
        }

        notifyDataSetChanged()
    }

    fun getSelections(): List<TargetEntity> {
        return currentList.filter { target ->
            target.selected
        }
    }
}

private class TargetsDiffCallback : DiffUtil.ItemCallback<TargetEntity>() {

    override fun areItemsTheSame(
        oldItem: TargetEntity,
        newItem: TargetEntity
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: TargetEntity,
        newItem: TargetEntity
    ): Boolean {
        return oldItem == newItem
    }
}