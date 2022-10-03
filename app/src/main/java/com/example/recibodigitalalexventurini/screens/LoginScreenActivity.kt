package com.example.recibodigitalalexventurini.screens

import android.app.Activity
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
import com.example.recibodigitalalexventurini.model.User
import com.example.recibodigitalalexventurini.utils.ApiServiceUtils
import com.example.recibodigitalalexventurini.utils.ConstantsUtils

class LoginScreenActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "LoginScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.login_screen)

        var userEmailOrPhoneEditText = findViewById<EditText>(R.id.user_email)

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
        val user = User("alex3@email.com", "123")
        ApiServiceUtils().getRequestUserLogin(this, ConstantsUtils.URL_USER_LOGIN, user)
    }

    private fun executeNewAccount() {
        Log.i(TAG, "executeNewAccount()")
        startActivity(Intent(this, CreateUserActivity::class.java))
    }

    private fun executeForgotPassword() {
        // TODO implement
        Toast.makeText(this, "Forgot Password executado", Toast.LENGTH_SHORT).show()
    }

    fun updateLoginMessageError(visibility: Boolean) {
        // TODO verify the crash occurs here when the password is wrong
        findViewById<TextView>(R.id.wrong_user).visibility = if (visibility) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    /**
     * This function was not used because needs more studies about.
     */
    fun updateLoginSuccess(activity: Activity?, message: String) {
        Log.i(TAG, message)
        val homeScreen = Intent(activity, HomeScreenActivity::class.java)
        activity?.startActivity(homeScreen)
    }
}