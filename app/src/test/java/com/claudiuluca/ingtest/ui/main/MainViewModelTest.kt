package com.claudiuluca.ingtest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.claudiuluca.ingtest.MainCoroutineRule
import com.claudiuluca.ingtest.data.FakeIngTestRepository
import com.claudiuluca.ingtest.data.Result
import com.claudiuluca.ingtest.data.selectedCampaign
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var infoRepository: FakeIngTestRepository

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setupViewModel() {
        infoRepository = FakeIngTestRepository()

        mainViewModel = MainViewModel(infoRepository)
    }

    @Test
    fun getInfoSuccessPath() {
        runBlockingTest {
            mainViewModel.getInfoFromNetwork()
            assertThat(mainViewModel._loadingInfoFromNetworkEvent.value?.peekContent(),
                `is`(IsInstanceOf.instanceOf(Result.Success(Any()).javaClass)))
        }
    }

    @Test
    fun addASelectedCampaign() {

        assertThat(mainViewModel.selectedCampaigns, `is`(emptyList()))
        mainViewModel.addOrRemoveASelectedCampaign(selectedCampaign)
        assertThat(mainViewModel.selectedCampaigns, CoreMatchers.hasItem(selectedCampaign))
    }

    @Test
    fun removeASelectedCampaign() {
        assertThat(mainViewModel.selectedCampaigns, `is`(emptyList()))
        mainViewModel.addOrRemoveASelectedCampaign(selectedCampaign)
        assertThat(mainViewModel.selectedCampaigns, CoreMatchers.hasItem(selectedCampaign))
        mainViewModel.addOrRemoveASelectedCampaign(selectedCampaign)
        assertThat(mainViewModel.selectedCampaigns, `is`(emptyList()))
    }
}