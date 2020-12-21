package com.claudiuluca.ingtest.data.api

import com.squareup.moshi.Json

data class ChannelsDTO(
    @Json(name = "fees")
    var fees: List<Channel>
)

data class Channel(
    @Json(name = "channel_name")
    var channelName: String,

    @Json(name = "campaigns")
    var campaigns: List<Campaign>
)

data class Campaign(
    @Json(name = "price")
    var price: String,

    @Json(name = "details")
    val details: List<String>
)
