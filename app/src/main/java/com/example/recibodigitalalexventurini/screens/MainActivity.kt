package com.example.recibodigitalalexventurini.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.recibodigitalalexventurini.R

class MainActivity : AppCompatActivity() {
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
        // TODO implement
        Toast.makeText(this, "Login executado", Toast.LENGTH_SHORT).show()
    }

    private fun executeNewAccount() {
        // TODO implement
        Toast.makeText(this, "New Account executado", Toast.LENGTH_SHORT).show()
    }

    private fun executeForgotPassword() {
        // TODO implement
        Toast.makeText(this, "Forgot Password executado", Toast.LENGTH_SHORT).show()
    }
}