package com.claudiuluca.ingtest.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApiService {
    @GET("{id}")
    suspend fun getTargets(@Path("id") targetsJsonId: String): Response<TargetDTO>

    @GET("{id}")
    suspend fun getChannels(@Path("id") channelsJsonId: String): Response<ChannelsDTO>
}
