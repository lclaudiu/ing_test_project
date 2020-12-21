package com.claudiuluca.ingtest.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.claudiuluca.ingtest.data.api.*
import com.claudiuluca.ingtest.data.api.Target
import com.claudiuluca.ingtest.util.campaignComparator
import com.claudiuluca.ingtest.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IngTestRepository(
    private val ingInfoApi: NetworkInterface,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RepositoryInterface {
    private var _localTargets = MutableLiveData<List<Target>>()
    override var localTargets: LiveData<List<Target>> = _localTargets

    private var _localChannels = MutableLiveData<List<Channel>>()
    override var localChannels: LiveData<List<Channel>> = _localChannels

    override suspend fun getInfoFromNetwork(): Result<*> {
        wrapEspressoIdlingResource {
            var responseTargets: Response<TargetDTO>? = null
            var responseChannels: Response<ChannelsDTO>? = null

            withContext(ioDispatcher) {
                try {
                    responseTargets =
                        ingInfoApi.getTargetsFromNetwork()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (responseTargets != null
                && responseTargets!!.isSuccessful
            ) {
                withContext(ioDispatcher) {
                    try {
                        responseChannels =
                            ingInfoApi.getChannelsFromNetwork()

                        if (responseChannels!!.isSuccessful) {
                            responseChannels!!.body()?.fees?.forEach { channel ->
                                val sortedList = channel.campaigns.sortedWith(campaignComparator)
                                channel.campaigns = sortedList
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            if (responseTargets == null
                || responseChannels == null
            ) {
                return Result.Error(
                    code = ErrorCode.DEFAULT.code
                )
            }

            return if (responseTargets!!.isSuccessful
                && responseChannels!!.isSuccessful
            ) {
                _localTargets.postValue(responseTargets!!.body()?.targets)
                _localChannels.postValue(responseChannels!!.body()?.fees)
                Result.Success(Any())
            } else {
                Result.Error(
                    code = if (!responseTargets!!.isSuccessful) responseTargets!!.code()
                    else responseChannels!!.code()
                )
            }
        }
    }
}