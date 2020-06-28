package com.cheise_proj.presentation.mapper.user

import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.model.user.Profile

class ProfileEntityMapper :
    PresentationMapper<Profile, ProfileEntity> {
    override fun presentationToEntity(p: Profile): ProfileEntity {
        return ProfileEntity(
            username = p.username,
            semester = p.semester,
            section = p.section,
            refNo = p.refNo,
            guardian = p.guardian,
            gender = p.gender,
            faculty = p.faculty,
            contact = p.contact,
            adminDate = p.adminDate,
            dob = p.dob,
            imageUrl = p.imageUrl,
            level = p.level,
            name = p.name
        )
    }

    override fun entityToPresentation(e: ProfileEntity): Profile {
        return Profile(
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