package com.claudiuluca.ingtest.data.api

data class ChannelEntity(
    var targetName: String,
    val channelName: String,
    var selected: Boolean = false
)