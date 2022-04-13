package com.yousef.sampleVehiclesOnMap.ui.mapFragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.data.testData.TestVehicleList
import com.yousef.sampleVehiclesOnMap.data.testData.TestWrongVehicleList
import com.yousef.sampleVehiclesOnMap.utils.Const
import com.yousef.sampleVehiclesOnMap.utils.CoroutineTestRule
import com.yousef.sampleVehiclesOnMap.utils.TestCoroutineRule
import com.yousef.sampleVehiclesOnMap.utils.rx.TestSchedulerProvider
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.doReturn


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapFragmentViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    var mSchedulesCallback: MapNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null
    private var mSchedulesViewModel: MapViewModel? = null
    private var mTestScheduler: TestScheduler? = null

    @Before
    @Throws(java.lang.Exception::class)
    open fun setUp(): Unit {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mSchedulesViewModel = MapViewModel(mMockDataManager, testSchedulerProvider)
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
    fun testSearchByBounds(){
        testCoroutineRule.runBlockingTest {
            val vehicleListResponse = Gson().fromJson(TestVehicleList().data, VehiclesPOJO::class.java)
            lenient().doReturn(vehicleListResponse)
                .`when`(mMockDataManager)
                ?.requestVehicleList(24.8465103, 39.7816502,
                    44.0318908, 63.3332704)
            mSchedulesViewModel?.searchByBounds(24.8465103, 39.7816502,
                44.0318908, 63.3332704)
            verify(mSchedulesCallback)?.setMarkers(vehicleListResponse.poiList)
        }
    }

    @Test
    fun testSearchByBoundsWithError(){
        testCoroutineRule.runBlockingTest {
            val vehicleListResponse = Gson().fromJson(TestWrongVehicleList().data, VehiclesPOJO::class.java)
            lenient().doReturn(vehicleListResponse)
                .`when`(mMockDataManager)
                ?.requestVehicleList(24.8465103, 39.7816502,
                    44.0318908, 63.3332704)
            mSchedulesViewModel?.searchByBounds(24.8465103, 39.7816502,
                44.0318908, 63.3332704)
            verify(mSchedulesCallback)?.setMarkers(null)
        }
    }
}