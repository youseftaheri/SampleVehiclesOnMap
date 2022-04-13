package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.data.testData.TestEmptyVehicleList
import com.yousef.sampleVehiclesOnMap.data.testData.TestVehicleList
import com.yousef.sampleVehiclesOnMap.data.testData.TestWrongVehicleList
import com.yousef.sampleVehiclesOnMap.utils.CoroutineTestRule
import com.yousef.sampleVehiclesOnMap.utils.TestCoroutineRule
import com.yousef.sampleVehiclesOnMap.utils.rx.TestSchedulerProvider
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VehicleListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    var mSchedulesCallback: VehicleListNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null
    private var mSchedulesViewModel: VehicleListViewModel? = null
    private var mTestScheduler: TestScheduler? = null

    @Before
    @Throws(java.lang.Exception::class)
    open fun setUp(): Unit {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mSchedulesViewModel = VehicleListViewModel(mMockDataManager, testSchedulerProvider)
        mSchedulesViewModel!!.setNavigator(mSchedulesCallback)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mTestScheduler = null
        mSchedulesViewModel = null
        mSchedulesCallback = null
    }

    @Test
    fun mockSuspendingSetCardsFunction() = runBlocking {
        val setCards : suspend () -> Unit = mock()
        setCards()
        verify(setCards).invoke()
    }

    @Test
    fun testFetchVehiclesData(){
        testCoroutineRule.runBlockingTest {
            val vehicleListResponse = Gson().fromJson(TestVehicleList().data, VehiclesPOJO::class.java)
            lenient().doReturn(vehicleListResponse)
                .`when`(mMockDataManager)
                ?.requestVehicleList(24.8465103, 39.7816502,
                    44.0318908, 63.3332704)
            mSchedulesViewModel?.fetchVehiclesData(24.8465103, 39.7816502,
                44.0318908, 63.3332704)
            verify(mSchedulesCallback, atLeastOnce())?.showLoading()
            verify(mSchedulesCallback, atLeastOnce())?.hideLoading()
            verify(mMockDataManager)!!.vehicleList =  vehicleListResponse
            verify(mSchedulesCallback)?.setCards()
        }
    }

    @Test
    fun testFetchEmptyVehiclesData(){
        testCoroutineRule.runBlockingTest {
            val vehicleListResponse = Gson().fromJson(TestEmptyVehicleList().data, VehiclesPOJO::class.java)
            lenient().doReturn(vehicleListResponse)
                .`when`(mMockDataManager)
                ?.requestVehicleList(24.8465103, 39.7816502,
                    44.0318908, 63.3332704)
            mSchedulesViewModel?.fetchVehiclesData(24.8465103, 39.7816502,
                44.0318908, 63.3332704)
            verify(mSchedulesCallback, atLeastOnce())?.showLoading()
            verify(mSchedulesCallback, atLeastOnce())?.hideLoading()
        }
    }

    @Test
    fun testFetchVehiclesDataWithError(){
        testCoroutineRule.runBlockingTest {
            val vehicleListResponse = Gson().fromJson(TestWrongVehicleList().data, VehiclesPOJO::class.java)
            lenient().doReturn(vehicleListResponse)
                .`when`(mMockDataManager)
                ?.requestVehicleList(24.8465103, 39.7816502,
                    44.0318908, 63.3332704)
            mSchedulesViewModel?.fetchVehiclesData(24.8465103, 39.7816502,
                44.0318908, 63.3332704)
            verify(mSchedulesCallback, atLeastOnce())?.showLoading()
            verify(mSchedulesCallback, atLeastOnce())?.hideLoading()
            verify(mSchedulesCallback)?.handleError(ArgumentMatchers.any())
        }
    }
}
