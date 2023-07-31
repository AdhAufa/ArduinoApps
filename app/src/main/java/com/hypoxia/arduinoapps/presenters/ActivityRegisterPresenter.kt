package com.hypoxia.arduinoapps.presenters

import com.hypoxia.arduinoapps.contracts.RegisterActivityContract
import com.hypoxia.arduinoapps.model.User
import com.hypoxia.arduinoapps.model.WrappedResponse
import com.hypoxia.arduinoapps.webservices.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityRegisterPresenter(v : RegisterActivityContract.RegisterActivityView?)
    : RegisterActivityContract.RegisterActivityPresenter{
    private var view : RegisterActivityContract.RegisterActivityView? = v
    private var apiService = APIClient.APIService()
    override fun register(name : String,username : String,gender : String, noHp : String, email: String, password : String) {
        val request = apiService.register(name, username, gender, noHp, email, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null ){
                        view?.showToast("Success to Register")
                        view?.successRegister()
                        view?.hideLoading()
                        println("Success Register" + body)
                    }else{
                        view?.showToast("Data is empty")
                        view?.hideLoading() }
                }else{
                    view?.showToast("Email or Username already exist")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Can't connect to server")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }

}