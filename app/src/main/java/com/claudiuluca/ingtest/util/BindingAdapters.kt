package com.claudiuluca.ingtest.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.data.api.SelectedCampaign

interface BindableAdapter<T> {
    fun setData(list: T)
}

@SuppressWarnings("UNCHECKED_CAST")
@BindingAdapter("app:items")
fun <T> setItemsListAdapter(listView: RecyclerView, items: T?) {
    items?.let {
        if (listView.adapter is BindableAdapter<*>) {
            (listView.adapter as BindableAdapter<T>).setData(items)
        }
    }
}

@BindingAdapter("channelsNames")
fun channelsNames(view: TextView, channelsNames: List<String>) {
    view.text =
        if (channelsNames.isNotEmpty()) channelsNames.joinToString(separator = ", ") else "-"
}

@BindingAdapter("channelName")
fun loadImage(view: ImageView, channelName: String) {
    Glide.with(view.getContext())
        .load(parseChannelNameToDrawable(channelName))
        .placeholder(R.drawable.ic_target)
        .into(view);
}

@BindingAdapter("campaignsDetails")
fun campaignsDetails(view: TextView, campaignsDetails: List<String>) {
    view.text =
        if (campaignsDetails.isNotEmpty()) campaignsDetails.joinToString(separator = "\n") else "-"
}

@BindingAdapter("reviewInfo")
fun reviewInfo(view: TextView, selectedCampaigns: List<SelectedCampaign>) {
    val collector = StringBuilder()
    selectedCampaigns.forEach { selectedCampaign ->
        collector.append(parseSelectedCampaignToText(view, selectedCampaign)).append("\n")
    }

    view.text = collector.toString()
}

private fun parseSelectedCampaignToText(
    view: TextView,
    selectedCampaign: SelectedCampaign,
): String {
    val collector = StringBuilder()
    collector.append(view.context.getString(R.string.target)).append(selectedCampaign.targetName)
        .append("\n")
    collector.append(view.context.getString(R.string.channel)).append(selectedCampaign.channelName)
        .append("\n")
    collector.append(view.context.getString(R.string.price)).append(selectedCampaign.campaign.price)
        .append("\n")
    collector.append(view.context.getString(R.string.includes)).append("\n")
    collector.append(selectedCampaign.campaign.details.joinToString(separator = "\n")).append("\n")
    return collector.toString()
}

private fun parseChannelNameToDrawable(channelName: String): Int {
    return when (channelName) {
        "Facebook" -> {
            R.drawable.ic_facebook
        }
        "Linkedin" -> {
            R.drawable.ic_linkedin
        }
        "Twitter" -> {
            R.drawable.ic_twitter
        }
        "Instagram" -> {
            R.drawable.ic_instagram
        }
        "Google AdWords" -> {
            R.drawable.ic_google_ads_adwords
        }
        "SEO" -> {
            R.drawable.ic_seo
        }
        else -> {
            R.drawable.ic_target
        }
    }
}