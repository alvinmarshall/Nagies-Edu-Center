package com.cheise_proj.presentation.job

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.cheise_proj.domain.usecase.files.UploadVideoTask
import com.cheise_proj.presentation.notification.ITeacherNotification
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadVideoWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val uploadVideoTask: UploadVideoTask,
    private val notification: ITeacherNotification
) :
    RxWorker(appContext, workerParams) {
    override fun createWork(): Single<Result> {
        val filePath = inputData.getString("file_path")
        val destination = inputData.getInt("destination", -1)
        val file = File(filePath!!)
        val uploadFile: MultipartBody.Part = MultipartBody.Part.Companion.createFormData(
            "file", file.name, RequestBody.create(
                "video/*".toMediaTypeOrNull(), file
            )
        )

        return uploadVideoTask.buildUseCase(uploadVideoTask.Params(uploadFile)).lastOrError()
            .map {
                println("http status receipt $it")
                Result.success()
            }
            .doOnSuccess {
                notification.initNotification(
                    applicationContext,
                    "Video upload complete",
                    "File uploaded successfully",
                    destination
                )
            }
            .doOnError {
                notification.initNotification(
                    applicationContext,
                    "Video upload failed",
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
                .Builder(UploadVideoWorker::class.java)
                .setInputData(data.build())
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
            return workManager.getWorkInfoByIdLiveData(request.id)

        }
    }

}