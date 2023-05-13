package com.example.arduinoapps.presenters

import com.example.arduinoapps.contracts.DetectionFragmentContract
import com.example.arduinoapps.model.PredictResponse
import com.example.arduinoapps.webservices.APIClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentDetectionPresenter(v : DetectionFragmentContract.DetectionFragmentView?) : DetectionFragmentContract.DetectionFragmentPresenter  {

    private var view : DetectionFragmentContract.DetectionFragmentView? = v
    private var apiService = APIClient.APIService()

    override fun detect(requestBody: RequestBody) {
        val request = apiService.predict(requestBody)
        request.enqueue(object : Callback<PredictResponse>{
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.successDetect(body)
                        println("Data deteksi ${body}")
                    }else{
                        view?.showToast("Data is empty")
                    }
                }else{
                    view?.showToast("Check your connection")
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                view?.showToast("Cant connect to server")
                println("Error message ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}