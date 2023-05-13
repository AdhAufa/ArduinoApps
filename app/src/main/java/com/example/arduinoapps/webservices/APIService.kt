package com.example.arduinoapps.webservices

import com.example.arduinoapps.model.PredictResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {


    @POST("/hypoxia")
    fun predict(
    @Body requestBody : RequestBody
    ): Call<PredictResponse>

}