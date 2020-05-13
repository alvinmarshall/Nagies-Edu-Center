package com.cheise_proj.data.mapper.user

import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.domain.entity.user.ProfileEntity
import javax.inject.Inject

internal class ProfileDataEntityMapper @Inject constructor() : DataMapper<ProfileData, ProfileEntity> {
    override fun dataToEntity(d: ProfileData): ProfileEntity {
        return ProfileEntity(
            username = d.username,
            semester = d.semester,
            section = d.section,
            refNo = d.refNo,
            guardian = d.guardian,
            gender = d.gender,
            faculty = d.faculty,
            contact = d.contact,
            adminDate = d.adminDate,
            dob = d.dob,
            imageUrl = d.imageUrl,
            level = d.level,
            name = d.name
        )
    }

    override fun entityToData(e: ProfileEntity): ProfileData {
        return ProfileData(
            username = e.username,
            semester = e.semester,
            section = e.section,
            refNo = e.refNo,
            guardian = e.guardian,
            gender = e.gender,
            faculty = e.faculty,
            contact = e.contact,
            adminDate = e.adminDate,
            dob = e.dob,
            imageUrl = e.imageUrl,
            level = e.level,
            name = e.name
        )
    }
}