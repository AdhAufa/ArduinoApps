package com.example.arduinoapps.webservices

import com.example.arduinoapps.model.PredictResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("/hypoxia")
    fun predict(
    @Field("oxygen") oxygen : Double,
    @Field("heart") heart : Double
    ): Call<PredictResponse>

}