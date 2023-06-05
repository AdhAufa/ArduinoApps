package com.example.arduinoapps.model

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("data") var data : T,
    @SerializedName("msg") var msg : String,
    @SerializedName("status") var status : Int
)

data class WrappedListResponse<T>(
    @SerializedName("data") var data : List<T>,
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int
)

