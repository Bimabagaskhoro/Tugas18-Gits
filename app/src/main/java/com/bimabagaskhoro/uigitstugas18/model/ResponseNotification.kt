package com.bimabagaskhoro.uigitstugas18.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseNotification(
	val message: String? = null,
	val status: Int? = null
) : Parcelable
