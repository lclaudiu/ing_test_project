package com.claudiuluca.ingtest.data.api

import com.claudiuluca.ingtest.BuildConfig
import com.claudiuluca.ingtest.injection.NetworkApi
import retrofit2.Response

class NetworkRepository(private val networkApi: NetworkApi) : NetworkInterface {
    override suspend fun getTargetsFromNetwork(): Response<TargetDTO> {
        return networkApi.networkApiService.getTargets(BuildConfig.TARGETINGS_ID)
    }

    override suspend fun getChannelsFromNetwork(): Response<ChannelsDTO> {
        return networkApi.networkApiService.getChannels(BuildConfig.CHANNELS_ID)
    }
}