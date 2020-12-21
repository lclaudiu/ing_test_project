package com.claudiuluca.ingtest.data.api


data class TargetEntity(
    var title: String,
    val channels: List<String>,
    var selected: Boolean = false
)

fun fromTargetToTargetEntity(target: Target): TargetEntity {
    return TargetEntity(
        title = target.title,
        channels = target.channels
    )
}

fun toEntitiesList(initial: List<Target>): List<TargetEntity>{
    return initial.map { fromTargetToTargetEntity(it) }
}