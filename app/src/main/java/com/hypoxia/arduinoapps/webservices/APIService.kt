package com.hypoxia.arduinoapps.webservices

import com.hypoxia.arduinoapps.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {


    @POST("hypoxia")
    fun predict(
    @Body requestBody : RequestBody
    ): Call<PredictResponse>

    //Sign in
    @FormUrlEncoded
    @POST("signin")
    fun login (
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    //Sign up
    @FormUrlEncoded
    @POST("signup")
    fun register (
        @Field("nama_user") name : String,
        @Field("username") username : String,
        @Field("gender") gender : String,
        @Field("no_hp") no_hp : String,
        @Field("email") email : String,
        @Field("password") password : String,
    ):Call<WrappedResponse<User>>

    //get history
    @GET("history")
    fun getHistory(
        @Header("Authorization") api_token: String
    ):Call<WrappedListResponse<History>>
    @POST("history")
    fun addHistory(
        @Header("Authorization") api_token: String,
        @Body requestBody : RequestBody
    ):Call<WrappedResponse<History>>

    @DELETE("history/{id}")
    fun deleteHistory(
        @Path("id") id : Int,
        @Header("Authorization") api_token: String
    ):Call<WrappedResponse<History>>

    @GET("user")
    fun getUser(
        @Header("Authorization") api_token: String
    ):Call<WrappedResponse<User>>

}