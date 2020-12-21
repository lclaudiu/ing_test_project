package com.claudiuluca.ingtest.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.claudiuluca.ingtest.data.RepositoryInterface
import com.claudiuluca.ingtest.data.Result
import com.claudiuluca.ingtest.data.api.ChannelEntity
import com.claudiuluca.ingtest.data.api.SelectedCampaign
import com.claudiuluca.ingtest.data.api.TargetEntity
import com.claudiuluca.ingtest.data.api.toEntitiesList
import com.claudiuluca.ingtest.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RepositoryInterface
) : ViewModel() {

    val targetings: LiveData<List<TargetEntity>> =
        Transformations.map(repository.localTargets) { list ->
            toEntitiesList(list)
        }

    val channels = liveData(context = Dispatchers.Default) {
        emitSource(repository.localChannels)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var _loadingInfoFromNetworkEvent: MutableLiveData<Event<Result<*>>> = MutableLiveData(
        Event(
            Result.None
        )
    )

    val loadingInfoFromNetworkEvent: LiveData<Event<Result<*>>> = _loadingInfoFromNetworkEvent

    init {
        getInfoFromNetwork()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getInfoFromNetwork() {
        _loadingInfoFromNetworkEvent.value = Event(Result.Loading)
        var result: Result<*> = Result.None

        viewModelScope.launch {
            result = repository.getInfoFromNetwork()
        }

        _loadingInfoFromNetworkEvent.value = Event(result)
    }

    private val _selectedTargets = mutableListOf<TargetEntity>()
    val selectedTargets: List<TargetEntity> = _selectedTargets

    fun setSelectedTargets(listTargets: List<TargetEntity>) {
        _selectedTargets.addAll(listTargets)
    }

    fun clearSelectedTargets() {
        _selectedTargets.clear()
    }

    private val _selectedChannels = MutableLiveData<List<ChannelEntity>>()
    val selectedChannels: LiveData<List<ChannelEntity>> = _selectedChannels

    fun setSelectedChannels(listChannels: List<ChannelEntity>) {
        _selectedChannels.postValue(listChannels)
    }

    fun clearSelectedChannels() {
        _selectedChannels.postValue(emptyList())
    }

    private var _selectedCampaigns = mutableListOf<SelectedCampaign>()
    val selectedCampaigns: List<SelectedCampaign> = _selectedCampaigns

    fun addOrRemoveASelectedCampaign(selectedCampaign: SelectedCampaign?) {
        selectedCampaign?.let {
            if (_selectedCampaigns.contains(selectedCampaign)) {
                _selectedCampaigns.remove(selectedCampaign)
            } else {
                _selectedCampaigns.add(selectedCampaign)
            }
        }
    }

    fun getSelectedCampaign(channel: String, target: String): SelectedCampaign? {
        return _selectedCampaigns.firstOrNull {
            it.channelName == channel && it.targetName == target
        }
    }

    fun clearCampaigns() {
        _selectedCampaigns.clear()
    }
}