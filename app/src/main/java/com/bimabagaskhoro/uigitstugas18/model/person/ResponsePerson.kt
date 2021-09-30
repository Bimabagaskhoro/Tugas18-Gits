package com.bimabagaskhoro.uigitstugas18.model.person

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponsePerson(

		@field:SerializedName("data")
	val data: List<DataItemPerson?>? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItemPerson(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tittle")
	val tittle: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
) : Parcelable
