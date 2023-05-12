package com.example.arduinoapps.contracts

import android.content.Context
import com.example.arduinoapps.model.PredictResponse

interface DetectionFragmentContract {
    interface DetectionFragmentView{
        fun successDetect(data : PredictResponse)
        fun showToast(message : String)
    }

    interface DetectionFragmentPresenter {
        fun detect(oxygen : Double,heart : Double)
        fun onDestroy()
    }
}