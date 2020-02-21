package com.cheise_proj.teacherapp.di.module.workmanager

import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.cheise_proj.presentation.job.UploadAssignmentWorker
import com.cheise_proj.presentation.job.UploadReportWorker
import com.cheise_proj.teacherapp.di.key.WorkerKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Subcomponent(modules = [WorkerSubComponent.Binders::class])
interface WorkerSubComponent {

    fun workers(): Map<Class<out RxWorker>, Provider<RxWorker>>

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun workerParameters(param: WorkerParameters): Builder

        fun build(): WorkerSubComponent
    }

    @Module
    interface Binders {

        @Binds
        @IntoMap
        @WorkerKey(UploadReportWorker::class)
        fun bindUploadReport(uploadReportWorker: UploadReportWorker): RxWorker

        @Binds
        @IntoMap
        @WorkerKey(UploadAssignmentWorker::class)
        fun bindUploadAssignment(uploadAssignmentWorker: UploadAssignmentWorker): RxWorker
    }
}