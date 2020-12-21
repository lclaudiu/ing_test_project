package com.claudiuluca.ingtest.data.api

import retrofit2.Response

interface NetworkInterface {
    suspend fun getTargetsFromNetwork(): Response<TargetDTO>

    suspend fun getChannelsFromNetwork(): Response<ChannelsDTO>
}