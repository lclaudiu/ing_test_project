package com.claudiuluca.ingtest.data

import androidx.lifecycle.LiveData
import com.claudiuluca.ingtest.data.api.Channel
import com.claudiuluca.ingtest.data.api.Target

interface RepositoryInterface {
    var localTargets: LiveData<List<Target>>
    var localChannels: LiveData<List<Channel>>

    suspend fun getInfoFromNetwork(): Result<*>
}