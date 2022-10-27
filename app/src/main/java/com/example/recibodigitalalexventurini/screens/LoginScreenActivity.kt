package com.example.recibodigitalalexventurini.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.ForgotPassword
import com.example.recibodigitalalexventurini.model.LoginResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreenActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "LoginScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.login_screen)

        findViewById<Button>(R.id.login)
            .setOnClickListener {
                executeLogin()
            }
        findViewById<Button>(R.id.new_account)
            .setOnClickListener {
                executeNewAccount()
            }
        findViewById<TextView>(R.id.forgot_password)
            .setOnClickListener {
                executeForgotPassword()
            }
    }

    private fun executeLogin() {
        Log.i(TAG, "executeLogin()")

        //val email = "alex3@email.com"
        //val password = "123"
        val email = findViewById<EditText>(R.id.user_email).text.toString()
        val password = findViewById<EditText>(R.id.user_password).text.toString()

        Log.i(TAG, "executeLogin() email: $email password: $password")
        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { updateLoginSuccess(it) }
                    } else {
                        val message = getString(R.string.code_message_incorrect_email_or_password)
                        updateLoginMessageError(true, message)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    val message = getString(R.string.code_message_access_api_error)
                    updateLoginMessageError(true, message)
                }

            })
    }

    private fun executeNewAccount() {
        Log.i(TAG, "executeNewAccount()")
        startActivity(Intent(this, CreateUserActivity::class.java))
    }

    private fun executeForgotPassword() {
        Log.i(TAG, "executeForgotPassword()")
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    fun updateLoginMessageError(visibility: Boolean, errorMessage: String) {
        Log.i(TAG, "updateLoginMessageError()")

        val tfLoginError = findViewById<TextView>(R.id.login_error)
        tfLoginError.visibility = if (visibility) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        tfLoginError.setText(errorMessage)
    }

    fun updateLoginSuccess(loginResponse: LoginResponse) {
        Log.i(TAG, "updateLoginSuccess()")
        updateLoginMessageError(false, ConstantsUtils.EMPTY_STRING)

        val homeScreen = Intent(this, HomeScreenActivity::class.java)
        homeScreen.putExtra(ConstantsUtils.USER_EXTRA, loginResponse)
        this.startActivity(homeScreen)
    }
}