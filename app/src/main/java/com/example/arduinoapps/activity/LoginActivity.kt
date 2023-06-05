package com.example.arduinoapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.arduinoapps.contracts.LoginActivityContrac
import com.example.arduinoapps.databinding.ActivityLoginBinding
import com.example.arduinoapps.databinding.ActivityMainBinding
import com.example.arduinoapps.presenters.ActivityLoginPresenter
import com.example.arduinoapps.webservices.Constants

class LoginActivity : AppCompatActivity(), LoginActivityContrac.View {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var presenter : LoginActivityContrac.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ActivityLoginPresenter(this)
        login()
        createAccount()
    }

    override fun onStart() {
        super.onStart()
        isLogin()
    }

    private fun isLogin(){
        val token = Constants.getToken(this)

        if (token.isNotEmpty() && token != "UNDEFINED"){
            startActivity(Intent(this, MainActivity::class.java).also { finish() })
        }
    }
    private fun createAccount(){
        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun login(){
        binding.btnLogin.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (userName.isNotEmpty() && password.isNotEmpty()){
                presenter.login(userName, password,this)
            }else{
                showToast("Please input all form")
            }

        }
    }
    override fun showToast(message: String) {
        Toast.makeText(this, message ,Toast.LENGTH_SHORT).show()
    }

    override fun successLogin() {
        startActivity(Intent(this, MainActivity::class.java).also { finish() })
    }

    override fun showLoading() {
        binding.loadingLogin.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loadingLogin.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

}