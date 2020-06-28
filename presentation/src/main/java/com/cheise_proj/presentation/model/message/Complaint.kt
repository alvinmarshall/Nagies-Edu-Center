package com.cheise_proj.presentation.model.message

import android.os.Parcel
import android.os.Parcelable

data class Complaint(
    val refNo: String,
    val studentName: String,
    val level: String,
    val contact: String,
    val receiver: String,
    val id: Int,
    val sender: String,
    val content: String,
    val date: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(refNo)
        parcel.writeString(studentName)
        parcel.writeString(level)
        parcel.writeString(contact)
        parcel.writeString(receiver)
        parcel.writeInt(id)
        parcel.writeString(sender)
        parcel.writeString(content)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Complaint> {
        override fun createFromParcel(parcel: Parcel): Complaint {
            return Complaint(parcel)
        }

        override fun newArray(size: Int): Array<Complaint?> {
            return arrayOfNulls(size)
        }
    }
}