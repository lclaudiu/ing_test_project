package com.claudiuluca.ingtest.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.claudiuluca.ingtest.data.api.Channel
import com.claudiuluca.ingtest.data.api.Target

class FakeIngTestRepository : RepositoryInterface {
    private var _localTargetsFakeSource = MutableLiveData(
        fakeTargetsList
    )

    private var _localChannelsFakeSource = MutableLiveData(
        fakeChannelsList
    )

    override var localTargets: LiveData<List<Target>> = _localTargetsFakeSource
    override var localChannels: LiveData<List<Channel>> = _localChannelsFakeSource

    override suspend fun getInfoFromNetwork(): Result<*> {
        return Result.Success(Any())
    }
}