package com.cheise_proj.presentation.model.vo

import com.cheise_proj.common_module.PARENT_ROLE
import com.cheise_proj.presentation.R

object Labels {
    fun getLabels(role: String): MutableList<Pair<String, Int>> {
        return when (role) {
            PARENT_ROLE -> getParent()
            else -> getTeacher()
        }
    }

    private fun getParent(): MutableList<Pair<String, Int>> {
        return mutableListOf(
            Pair("StudentNo", R.drawable.ic_list_black_24dp),
            Pair("Student Name", R.drawable.ic_person_black_24dp),
            Pair("Date of Birth", R.drawable.ic_event_black_24dp),
            Pair("Gender", R.drawable.ic_wc_black_24dp),
            Pair("Admission Date", R.drawable.ic_event_black_24dp),
            Pair("Academic Year", R.drawable.ic_person_black_24dp),
            Pair("Term", R.drawable.ic_access_time_black_24dp),
            Pair("Class Name", R.drawable.ic_school_black_24dp),
            Pair("Parent Name", R.drawable.ic_person_black_24dp),
            Pair("Contact", R.drawable.ic_call_black_24dp),
            Pair("Site Name", R.drawable.ic_business_black_24dp),
            Pair("Username", R.drawable.ic_account_circle_black_24dp)
        )
    }

    private fun getTeacher(): MutableList<Pair<String, Int>> {
        return mutableListOf(
            Pair("ref", R.drawable.ic_list_black_24dp),
            Pair("Name", R.drawable.ic_person_black_24dp),
            Pair("Gender", R.drawable.ic_wc_black_24dp),
            Pair("Admission Date", R.drawable.ic_event_black_24dp),
            Pair("Level", R.drawable.ic_business_black_24dp),
            Pair("Contact", R.drawable.ic_call_black_24dp),
            Pair("Faculty", R.drawable.ic_school_black_24dp),
            Pair("Username", R.drawable.ic_account_circle_black_24dp)
        )
    }


}