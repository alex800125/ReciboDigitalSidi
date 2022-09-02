package com.example.recibodigitalalexventurini.screens

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

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val user = User()
        user.email = "alex2@email.com"
        user.pass = "123"
        ApiServiceUtils().getRequestUserLogin(this, ConstantsUtils.USER_LOGIN, user)
    }

    private fun executeNewAccount() {
        // TODO implement
        Toast.makeText(this, "New Account executado", Toast.LENGTH_SHORT).show()
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

    fun updateLoginSuccess(message: String) {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.i(TAG, message)
    }
}