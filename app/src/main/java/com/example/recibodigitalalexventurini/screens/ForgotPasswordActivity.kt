package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.ForgotPassword
import com.example.recibodigitalalexventurini.model.ForgotPasswordResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "ForgotPasswordActivity"

    private var mPhoneNumber: EditText? = null
    private var mEmail: EditText? = null
    private var mCpf: EditText? = null

    private var mPhoneNumberError: TextView? = null
    private var mEmailError: TextView? = null
    private var mCpfError: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        init()

        findViewById<Button>(R.id.forgot_password_reset)
            .setOnClickListener {
                validateItems(
                    ForgotPassword(
                        mPhoneNumber?.text.toString(),
                        mEmail?.text.toString(),
                        mCpf?.text.toString()
                    )
                )
            }

    }

    fun backButton(view: View) {
        Log.i(TAG, "backButton()")
        onBackPressed()
    }

    private fun init() {
        Log.i(TAG, "init()")
        mPhoneNumber = findViewById(R.id.forgot_password_phone_number_edittext)
        mEmail = findViewById(R.id.forgot_password_email_edittext)
        mCpf = findViewById(R.id.forgot_password_cpf_edittext)

        mPhoneNumberError = findViewById(R.id.forgot_password_phone_number_text_error)
        mEmailError = findViewById(R.id.forgot_password_email_text_error)
        mCpfError = findViewById(R.id.forgot_password_cpf_text_error)
    }

    private fun validateItems(forgotPassword: ForgotPassword) {
        Log.i(TAG, "validadeItems()")
        var error = false
        mPhoneNumberError!!.visibility = View.INVISIBLE
        mEmailError!!.visibility = View.INVISIBLE
        mCpfError!!.visibility = View.INVISIBLE

        if (forgotPassword.phoneNumber.isNullOrEmpty()) {
            error = true
            mPhoneNumberError!!.visibility = View.VISIBLE
        }
        if (forgotPassword.email.isNullOrEmpty()) {
            error = true
            mEmailError!!.visibility = View.VISIBLE
        }
        if (forgotPassword.cpf.isNullOrEmpty()) {
            error = true
            mCpfError!!.visibility = View.VISIBLE
        }

        if (!error)
            executePostResetPassword(forgotPassword)
    }

    private fun executePostResetPassword(forgotPassword: ForgotPassword) {
        Log.i(TAG, "executePostResetPassword()")

        Log.i(
            TAG,
            "validadeItems forgotPassword.phoneNumber: " + forgotPassword.phoneNumber.toString() +
                    "\nforgotPassword.email: " + forgotPassword.email.toString() +
                    "\nforgotPassword.cpf: " + forgotPassword.cpf.toString() +
                    "\nURL_USER: " + ConstantsUtils.URL_USER_RESET_PASSWORD
        )

        RetrofitClient.instance.postResetPassword(
            forgotPassword.email!!,
            forgotPassword.phoneNumber!!,
            forgotPassword.cpf!!
        )
            .enqueue(object : Callback<ForgotPasswordResponse> {
                override fun onResponse(
                    call: Call<ForgotPasswordResponse>,
                    response: Response<ForgotPasswordResponse>
                ) {
                    if (response.isSuccessful) {
                        showReturnFromApi(response.message())
                        onBackPressed()
                    } else {
                        showReturnFromApi("Code: " + response.code() + " Message: " + response.message())
                    }
                }

                override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                    showReturnFromApi("ERROR " + t.cause + " message: " + t.message!!)
                }
            })
    }

    fun showReturnFromApi(message: String) {
        Log.i(TAG, "showReturnFromApi() $message")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}