package com.cheise_proj.parentapp.di.module

import com.cheise_proj.parentapp.di.module.data.DataModule
import com.cheise_proj.parentapp.di.module.domain.DomainModule
import com.cheise_proj.parentapp.di.module.local.LocalModule
import com.cheise_proj.parentapp.di.module.presentation.PresentationModule
import com.cheise_proj.parentapp.di.module.remote.RemoteModule
import dagger.Module

@Module(includes = [DomainModule::class, PresentationModule::class, DataModule::class, RemoteModule::class, LocalModule::class])
class AppModule