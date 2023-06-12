package com.example.arduinoapps.contracts

import android.content.Context
import com.example.arduinoapps.model.PredictResponse
import okhttp3.RequestBody

interface DetectionFragmentContract {
    interface DetectionFragmentView{
        fun successDetect(data : PredictResponse)
        fun showToast(message : String)
    }

    interface DetectionFragmentPresenter {
        fun detect(requestBody : RequestBody)
        fun saveHistory(token : String, requestBody : RequestBody)
        fun onDestroy()
    }
}