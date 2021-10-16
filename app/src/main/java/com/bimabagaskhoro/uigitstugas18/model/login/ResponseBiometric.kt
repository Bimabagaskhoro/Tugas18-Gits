package com.bimabagaskhoro.uigitstugas18.model.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseBiometric(
	val data: List<DataItemBiometric?>? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItemBiometric(
	val nama: String? = null,
	val passwd: String? = null,
	val idDevice: String? = null,
	val id: String? = null,
	val avatar: String? = null,
	val email: String? = null
) : Parcelable
