package com.fansymasters.fancysmarthome.di

import com.fansymasters.fancysmarthome.util.DevicesState
import com.fansymasters.fancysmarthome.util.DevicesStateReader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MainModule {

    @Binds
    fun bindsDevicesStateReader(impl: DevicesState): DevicesStateReader
}