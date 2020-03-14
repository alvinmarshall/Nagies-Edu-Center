package com.cheise_proj.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.common_module.PARENT_ROLE
import com.cheise_proj.domain.usecase.users.GetProfileTask
import com.cheise_proj.presentation.mapper.user.ProfileEntityMapper
import com.cheise_proj.presentation.model.user.Profile
import com.cheise_proj.presentation.model.vo.Labels
import com.cheise_proj.presentation.model.vo.ProfileLabel
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileTask: GetProfileTask,
    private val profileEntityMapper: ProfileEntityMapper
) : BaseViewModel() {

    fun getProfile(
        role: String,
        identifier: String
    ): LiveData<Resource<List<Pair<ProfileLabel, String?>>>> {
        return getProfileTask.buildUseCase(getProfileTask.ProfileParams(role, identifier))
            .map { profileEntityMapper.entityToPresentation(it) }
            .map {
                return@map setProfileData(it, role)
            }
            .map { Resource.onSuccess(it) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function { Observable.just(Resource.onError(it.localizedMessage)) }
            )
            .toFlowable(BackpressureStrategy.LATEST).toLiveData()
    }

    private fun setProfileData(data: Profile, role: String): List<Pair<ProfileLabel, String?>> {
        val myList = arrayListOf<Pair<ProfileLabel, String?>>()
        val profileData = getProfileData(role,data)
        val label = ArrayList<String>()
        val drawable = ArrayList<Int>()
        for (profile in Labels.getLabels(role)) {
            label.add(profile.first)
            drawable.add(profile.second)
        }
        for (i in profileData.indices) {
            myList.add(Pair(ProfileLabel(label[i], drawable[i]), profileData[i]))
        }

        return myList
    }

    private fun getProfileData(role: String, data: Profile): List<String> {
        if (role == PARENT_ROLE) {
            return arrayListOf(
                data.refNo,
                data.name,
                data.dob,
                data.gender,
                data.adminDate,
                data.section,
                data.semester,
                data.level,
                data.guardian,
                data.contact,
                data.faculty,
                data.username
            )

        } else {
            return arrayListOf(
                data.refNo,
                data.name,
                data.gender,
                data.adminDate,
                data.level,
                data.contact,
                data.faculty,
                data.username
            )
        }
    }
}