package com.hypoxia.arduinoapps.presenters

import android.content.Context
import android.util.Log
import com.hypoxia.arduinoapps.contracts.LoginActivityContrac
import com.hypoxia.arduinoapps.model.User
import com.hypoxia.arduinoapps.model.WrappedResponse
import com.hypoxia.arduinoapps.webservices.APIClient
import com.hypoxia.arduinoapps.webservices.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLoginPresenter(v : LoginActivityContrac.View?) : LoginActivityContrac.Presenter {

    private var view : LoginActivityContrac.View? = v
    private var apiService = APIClient.APIService()

    override fun login(username: String, password: String, context: Context) {
        val request = apiService.login(username, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast("Succes Login")
                        view?.successLogin()
                        view?.hideLoading()
                        println("Data ${body.data}")
                        Constants.setToken(context,body.data.token!!)
                        Constants.setName(context,body.data.username!!)
                    }else{
                        view?.showToast("Data is Empty")
                        view?.hideLoading() }
                }else{
                    view?.showToast("Username and Password Doesn't match")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Cant Connect with server")
                print(t.message)
                Log.d(t.message, "error message")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }
}