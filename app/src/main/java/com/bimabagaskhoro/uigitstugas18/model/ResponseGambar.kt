package com.bimabagaskhoro.uigitstugas18.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseGambar(
        @field:SerializedName("message")
        val message: String? = null,

        @field:SerializedName("message")
        val status: Int? = null,

        @field:SerializedName("file path")
        val file_path: String? = null
): Parcelable
