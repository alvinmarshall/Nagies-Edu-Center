package com.cheise_proj.presentation.job

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.cheise_proj.domain.usecase.files.UploadAssignmentTask
import com.cheise_proj.presentation.notification.ITeacherNotification
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadAssignmentWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val uploadAssignmentTask: UploadAssignmentTask,
    private val notification: ITeacherNotification
) :
    RxWorker(appContext, workerParams) {
    override fun createWork(): Single<Result> {
        val filePath = inputData.getString("file_path")
        val file = File(filePath!!)
        val destination = inputData.getInt("destination", -1)
        println("destination $destination")
        val uploadFile: MultipartBody.Part = MultipartBody.Part.Companion.createFormData(
            "file", file.name, file
                .asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        return uploadAssignmentTask.buildUseCase(uploadAssignmentTask.Params(uploadFile))
            .lastOrError()
            .map {
                println("http status receipt $it")
                Result.success()
            }
            .doOnSuccess {
                notification.initNotification(
                    applicationContext,
                    "Assignment upload complete",
                    "file uploaded successful",
                    destination
                )
            }
            .doOnError {
                println("worker err ${it.message}")
                notification.initNotification(
                    applicationContext,
                    "Assignment upload failed",
                    "There was a problem uploading the file, try again\n error: ${it.localizedMessage}",
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
                .Builder(UploadAssignmentWorker::class.java)
                .setInputData(data.build())
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
            return workManager.getWorkInfoByIdLiveData(request.id)
        }
    }
}