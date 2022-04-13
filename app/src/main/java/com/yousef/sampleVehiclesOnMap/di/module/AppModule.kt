package com.yousef.sampleVehiclesOnMap.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yousef.sampleVehiclesOnMap.data.AppDataManager
import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.data.local.prefs.AppPreferencesHelper
import com.yousef.sampleVehiclesOnMap.data.local.prefs.PreferencesHelper
import com.yousef.sampleVehiclesOnMap.data.remote.ApiHelper
import com.yousef.sampleVehiclesOnMap.data.remote.Apis
import com.yousef.sampleVehiclesOnMap.data.remote.AppApiHelper
import com.yousef.sampleVehiclesOnMap.di.PreferenceInfo
import com.yousef.sampleVehiclesOnMap.utils.CommonUtils
import com.yousef.sampleVehiclesOnMap.utils.Const
import com.yousef.sampleVehiclesOnMap.utils.rx.AppSchedulerProvider
import com.yousef.sampleVehiclesOnMap.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Apis {
        return retrofit.create(Apis::class.java)
    }


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return Const.PREF_NAME
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideUtils(context: Context?): CommonUtils {
        return CommonUtils
    }
}