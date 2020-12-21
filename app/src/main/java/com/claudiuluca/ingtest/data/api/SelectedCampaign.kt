package com.claudiuluca.ingtest.data.api

data class SelectedCampaign(
    var targetName: String,
    val channelName: String,
    var campaign: Campaign
)