package com.example.arduinoapps.presenters

import android.util.Log
import com.example.arduinoapps.contracts.HistoryFragmentContract
import com.example.arduinoapps.model.History
import com.example.arduinoapps.model.WrappedListResponse
import com.example.arduinoapps.model.WrappedResponse
import com.example.arduinoapps.webservices.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHistoryPresenter(v : HistoryFragmentContract.View?) : HistoryFragmentContract.Presenter {
    private var view : HistoryFragmentContract.View? = v
    private var apiService = APIClient.APIService()

    override fun getHistory(token: String) {
        val request = apiService.getHistory("Bearer $token")
        request.enqueue(object : Callback<WrappedListResponse<History>> {
            override fun onResponse(
                call: Call<WrappedListResponse<History>>,
                response: Response<WrappedListResponse<History>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        if (body.data.isEmpty()){
                            view?.showEmpty()
                        }else{
                            view?.hideEmpty()
                            view?.attachHistoryToRecycler(body.data)
                            println("History : ${body.data}")
                        }
                    }else{
                        view?.showToast("Data is empty")
                    }
                }else{
                    view?.showToast("Check your connection")
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<History>>, t: Throwable) {
                view?.showToast("Cant connect to server")
                println(t.message)
            }
        })
    }

    override fun deleteWishlist(token: String, id: Int) {
        val request = apiService.deleteHistory(id, "Bearer $token")
        request.enqueue(object : Callback<WrappedResponse<History>>{
            override fun onResponse(
                call: Call<WrappedResponse<History>>,
                response: Response<WrappedResponse<History>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        view?.showToast(body.msg)
                        view?.onItemDelete(body.data)
                    }
                }else{
                    view?.showToast("Check your connection")
                }
            }
            override fun onFailure(call: Call<WrappedResponse<History>>, t: Throwable) {
                view?.showToast("Cant connect to server")
            }
        })
    }

    override fun destroy() {
        view = null
    }
}