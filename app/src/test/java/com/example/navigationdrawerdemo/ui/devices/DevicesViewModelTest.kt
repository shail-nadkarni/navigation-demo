package com.example.navigationdrawerdemo.ui.devices

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.navigationdrawerdemo.repository.DevicesRepository
import com.example.navigationdrawerdemo.repository.models.Device
import com.example.navigationdrawerdemo.ui.UiState
import com.example.navigationdrawerdemo.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DevicesViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: DevicesRepository

    @Mock
    private lateinit var uiStateObserver: Observer<UiState>

    @Test
    fun `when onLoad() is called, should show loading`() = runBlockingTest {
        doReturn(emptyList<Device>())
            .`when`(repository)
            .getDevices()

        val viewModel = DevicesViewModel(repository)
        viewModel.onLoad()

        viewModel.uiState.observeForever(uiStateObserver)
        verify(repository, times(1)).getDevices()
        verify(uiStateObserver).onChanged(UiState.Loading)
        viewModel.uiState.removeObserver(uiStateObserver)
    }

    @Test
    fun `when onLoad(), is called, given api success, should show devices`() = runBlockingTest {
        doReturn(getDevices())
            .`when`(repository)
            .getDevices()

        val viewModel = DevicesViewModel(repository)
        viewModel.onLoad()

        viewModel.uiState.observeForever(uiStateObserver)

        verify(repository, times(1)).getDevices()
        verify(uiStateObserver).onChanged(UiState.Loading)
        viewModel.uiState.removeObserver(uiStateObserver)
    }

    private fun getDevices(): List<Device> {
        return listOf(
            Device(
                "id",
                "type",
                20,
                "USD",
                false,
                "imageUrl",
                "title",
                "description"
            )
        )
    }
}