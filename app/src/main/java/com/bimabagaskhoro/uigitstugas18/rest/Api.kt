package com.bimabagaskhoro.uigitstugas18.rest

import com.bimabagaskhoro.uigitstugas18.model.ResponseGambar
import com.bimabagaskhoro.uigitstugas18.model.ResponseNotification
import com.bimabagaskhoro.uigitstugas18.model.buah.ResponseData
import com.bimabagaskhoro.uigitstugas18.model.person.ResponsePerson
import com.bimabagaskhoro.uigitstugas18.model.buah.ResponseStatus
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseLogins
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseStatusLogin
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
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

    //person

    @GET("apiperson.php?function=get_person")
    fun getPerson(): Call<ResponsePerson>

    @FormUrlEncoded
    @POST("apiperson.php?function=insert_person")
    fun insertPerson(
        @Field("id") id: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("tittle") tittle: String,
        @Field("gambar") gambar: String,
        @Query("function") function: String
    ): Call<ResponseStatusPerson>

    @FormUrlEncoded
    @POST("apiperson.php?function=update_person")
    fun updatePerson(
        @Field("nama")nama: String,
        @Field("email")email: String,
        @Field("tittle")tittle: String,
        @Field("gambar")gambar: String,
        @Query("id")id: String
    ): Call<ResponseStatusPerson>

    @DELETE("apiperson.php?function=delete_person")
    fun deletePerson(
            @Query("id")id: String,
    ):Call<ResponseStatusPerson>

    @Multipart
    @POST("apiperson.php?function=upload_gambar")
    fun insertGambar(
            @Part body: MultipartBody.Part
    ):Call<ResponseGambar>

    //login

    @FormUrlEncoded
    @POST("apilogin.php?function=insert_user")
    fun regist(
            @Field("id") id: String,
            @Field("nama") nama: String,
            @Field("email") email: String,
            @Field("passwd") tittle: String,
            @Field("avatar") gambar: String,
            @Query("function") function: String
    ): Call<ResponseStatusLogin>

    @Multipart
    @POST("apilogin.php?function=upload_avatar")
    fun uploadImage(
            @Part body: MultipartBody.Part
    ):Call<ResponseGambar>

    @GET("apilogin.php?function=login_user")
    fun login(
            @Query("email") email_user: String,
            @Query("passwd") password: String
    ): Call<ResponseLogins>

    @FormUrlEncoded
    @POST("apilogin.php?function=send_notifikasi")
    fun pushNotif(
        @Field("title")tittle: String,
        @Field("message")message: String
    ): Call<ResponseNotification>

}