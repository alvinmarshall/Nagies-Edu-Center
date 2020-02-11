package com.wNagiesEducationalCenterj_9905.di.module.workmanager

import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.cheise_proj.presentation.job.UploadReceiptWorker
import com.wNagiesEducationalCenterj_9905.di.key.WorkerKey
import com.wNagiesEducationalCenterj_9905.di.module.domain.DomainModule
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
        @WorkerKey(UploadReceiptWorker::class)
        fun bindUploadReceipt(uploadReceiptWorker: UploadReceiptWorker): RxWorker
    }
}