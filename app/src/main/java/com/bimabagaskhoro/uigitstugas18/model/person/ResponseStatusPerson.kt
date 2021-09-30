package com.bimabagaskhoro.uigitstugas18.model.person

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseStatusPerson(
	val message: String? = null,
	val status: Int? = null
) : Parcelable
