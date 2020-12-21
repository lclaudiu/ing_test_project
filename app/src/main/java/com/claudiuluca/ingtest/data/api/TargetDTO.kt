package com.claudiuluca.ingtest.data.api

import com.squareup.moshi.Json

data class TargetDTO(
    @Json(name = "targeting_specifics")
    val targets: List<Target>
)

data class Target(
    @Json(name = "title")
    var title: String,

    @Json(name = "channels")
    val channels: List<String>
)