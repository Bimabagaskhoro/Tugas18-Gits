package com.bimabagaskhoro.uigitstugas18.rest

import com.bimabagaskhoro.uigitstugas18.model.DataItem
import com.bimabagaskhoro.uigitstugas18.model.ResponseData
import com.bimabagaskhoro.uigitstugas18.model.ResponseStatus
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("apibuah.php?function=get_buah")
    fun getBuah(): Call<ResponseData>

    @FormUrlEncoded
    @POST("apibuah.php?function=insert_buah")
    fun insertBuah(
            @Field("id")id: String,
            @Field("nama")nama: String,
            @Field("harga")harga: String,
            @Query("function")function: String
    ): Call<ResponseStatus>

    @FormUrlEncoded
    @POST("apibuah.php?function=update_buah")
    fun updateBuah(
            @Field("nama")nama: String,
            @Field("harga")harga: String,
            @Query("id")id: String
    ): Call<ResponseStatus>

    @DELETE("apibuah.php?function=delete_buah")
    fun deleteBuah(
            @Query("id")id: String,
    ):Call<ResponseStatus>
}