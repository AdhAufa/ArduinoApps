package com.hypoxia.arduinoapps.presenters

import com.hypoxia.arduinoapps.contracts.ProfileFragmentContract
import com.hypoxia.arduinoapps.model.User
import com.hypoxia.arduinoapps.model.WrappedResponse
import com.hypoxia.arduinoapps.webservices.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentProfilePresenter(v : ProfileFragmentContract.View?): ProfileFragmentContract.Presenter {

    private var view : ProfileFragmentContract.View? = v
    private val apiService = APIClient.APIService()

    override fun getUser(token : String) {
        val request = apiService.getUser("Bearer $token")
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showUserToView(body.data)
                        println("Profile ${body.data}")
                    }else{
                        view?.showToast("Data is empty")
                    }
                }else{
                    view?.showToast("Check your connection")
                }
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                view?.showToast("Something went wrong")
            }
        })

    }

    override fun destroy() {
        view = null
    }
}