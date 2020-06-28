package com.cheise_proj.presentation.job

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.cheise_proj.domain.usecase.files.UploadReportTask
import com.cheise_proj.presentation.notification.ITeacherNotification
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadReportWorker @Inject constructor(
    appContext: Context, workerParams: WorkerParameters,
    private val uploadReportTask: UploadReportTask,
    private val notification: ITeacherNotification
) :
    RxWorker(appContext, workerParams) {
    override fun createWork(): Single<Result> {
        val filePath = inputData.getString("file_path")
        val refNo = inputData.getString("refNo")
        val fullName = inputData.getString("fullName")
        val destination = inputData.getInt("destination", -1)
        val file = File(filePath!!)
        val uploadFile: MultipartBody.Part = MultipartBody.Part.Companion.createFormData(
            "file", file.name, file
                .asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        val refNoPart: MultipartBody.Part = MultipartBody.Part.Companion.createFormData(
            "studentNo", refNo ?: ""
        )
        val fullNamePart: MultipartBody.Part = MultipartBody.Part.Companion.createFormData(
            "studentName", fullName ?: ""
        )
        return uploadReportTask.buildUseCase(
            uploadReportTask.Params(
                file = uploadFile, refNo = refNoPart, fullName = fullNamePart
            )

        ).lastOrError()
            .map {
                println("http status receipt $it")
                Result.success()
            }
            .doOnSuccess {
                notification.initNotification(
                    applicationContext,
                    "Report upload complete",
                    "file uploaded successful",
                    destination
                )
            }
            .doOnError {
                println("worker err ${it.message}")
                notification.initNotification(
                    applicationContext,
                    "Report upload failed",
                    "There was a problem uploading the file, try again\n error: ${it.localizedMessage}",
                    destination
                )
            }
            .onErrorReturnItem(Result.failure())

    }

    companion object {
        fun start(
            context: Context,
            filePath: String?,
            refNo: String,
            fullName: String,
            destination: Int
        ): LiveData<WorkInfo> {
            val data = Data.Builder()
            data.putString("file_path", filePath)
            data.putString("refNo", refNo)
            data.putString("fullName", fullName)
            data.putInt("destination", destination)
            val request = OneTimeWorkRequest
                .Builder(UploadReportWorker::class.java)
                .setInputData(data.build())
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
            return workManager.getWorkInfoByIdLiveData(request.id)
        }
    }
}