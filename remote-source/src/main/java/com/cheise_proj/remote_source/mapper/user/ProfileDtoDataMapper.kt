package com.cheise_proj.remote_source.mapper.user

import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.remote_source.mapper.RemoteMapper
import com.cheise_proj.remote_source.model.dto.user.IProfileDto
import com.cheise_proj.remote_source.model.dto.user.Profile1

internal class ProfileDtoDataMapper :
    RemoteMapper<IProfileDto, ProfileData> {
    override fun dtoToData(t: IProfileDto): ProfileData {
        return ProfileData(
            username = t.username?:"",
            semester = t.semester?:"",
            section = t.section?:"",
            refNo = t.refNo?:"",
            guardian = t.guardian?:"",
            gender = t.gender?:"",
            faculty = t.faculty?:"",
            contact = t.contact?:"",
            adminDate = t.adminDate?:"",
            dob = t.dob?:"",
            imageUrl = t.imageUrl,
            level = t.level?:"",
            name = t.name?:""
        )
    }

    override fun dataToDto(d: ProfileData): IProfileDto {
        return Profile1(
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

}