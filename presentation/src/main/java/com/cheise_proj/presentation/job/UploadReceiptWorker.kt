package com.cheise_proj.presentation.job

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.cheise_proj.domain.usecase.files.UploadReceiptTask
import com.cheise_proj.presentation.notification.IParentNotification
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadReceiptWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val uploadReceiptTask: UploadReceiptTask,
    private val notification: IParentNotification
) :
    RxWorker(appContext, workerParams) {
    override fun createWork(): Single<Result> {
        val filePath = inputData.getString("file_path")
        val destination = inputData.getInt("destination", -1)
        val file = File(filePath!!)
        val uploadFile: MultipartBody.Part = MultipartBody.Part.Companion.createFormData(
            "file", file.name, RequestBody.create(
                "image/jpeg".toMediaTypeOrNull(), file
            )
        )

        return uploadReceiptTask.buildUseCase(UploadReceiptTask.Params(uploadFile)).lastOrError()
            .map {
                println("http status receipt $it")
                Result.success()
            }
            .doOnSuccess {
                notification.initNotification(
                    applicationContext,
                    "Receipt upload complete",
                    "File uploaded successfully",
                    destination
                )
            }
            .doOnError {
                notification.initNotification(
                    applicationContext,
                    "Receipt upload failed",
                    "An error occurred while uploading file, try again\n error: ${it.localizedMessage}",
                    destination
                )
            }
            .onErrorReturnItem(Result.failure())
    }

    companion object {
        fun start(context: Context, filePath: String?, destination: Int): LiveData<WorkInfo> {
            val data = Data.Builder()
            data.putString("file_path", filePath)
            data.putInt("destination", destination)
            val request = OneTimeWorkRequest
                .Builder(UploadReceiptWorker::class.java)
                .setInputData(data.build())
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
            return workManager.getWorkInfoByIdLiveData(request.id)

        }
    }

}