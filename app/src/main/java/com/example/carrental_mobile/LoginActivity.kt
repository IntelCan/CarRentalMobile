package com.example.carrental_mobile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.carrental_mobile.infrastructure.rest.security.api.AuthenticationApi
import com.example.carrental_mobile.infrastructure.rest.security.LoginCredentials
import com.example.carrental_mobile.infrastructure.rest.security.Token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {

    val TOKEN_KEY: String = "com.example.carrental_mobile.token"

    private val authenticationApi by lazy {
        AuthenticationApi.create()
    }

    private var myCompositeDisposable: CompositeDisposable? = null

    private var buttonLogin: Button? = null
    private var token: String? = ""
    private var loginCredentials: LoginCredentials? = null
    private var progressBar: ProgressBar? = null
    var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"
        myCompositeDisposable = CompositeDisposable()

        prefs = this.getSharedPreferences(TOKEN_KEY, 0)

        progressBar = ProgressBar(this@LoginActivity)
        progressBar?.isIndeterminate = false
        token = prefs!!.getString(TOKEN_KEY, "")
        if (token?.length!! > 10) {
            progressBar?.visibility = View.VISIBLE
        }

        buttonLogin = findViewById<View>(R.id.login_button) as Button
        buttonLogin?.setOnClickListener {
            when {
                isOnline() -> {
                    val login = (findViewById<View>(R.id.login) as EditText).text.toString()
                    val password = (findViewById<View>(R.id.password) as EditText).text.toString()
                    loginCredentials = LoginCredentials(login, password)
                    sendLoginRequesr(loginCredentials!!)
                }
                else -> Toast.makeText(window.context, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun sendLoginRequesr(credentials: LoginCredentials) {
        myCompositeDisposable?.add(authenticationApi.login(credentials)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))
    }

    @SuppressLint("CommitPrefEdits")
    private fun handleResponse(result: Token?) {
        prefs?.edit()?.putString(TOKEN_KEY, result?.token)?.apply()
        redirectToMainActivity()
    }

    private fun handleError(error: Throwable) {
        Log.d("rxError", error.localizedMessage)
        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }


    fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, 1)
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
        print("On destroy")
    }

}
