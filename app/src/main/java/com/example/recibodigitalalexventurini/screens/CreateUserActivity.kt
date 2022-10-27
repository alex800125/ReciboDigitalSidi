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
import com.example.recibodigitalalexventurini.model.CreateUser
import com.example.recibodigitalalexventurini.model.UserCreateResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUserActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "CreateUserActivity"

    private var mName: EditText? = null
    private var mPhoneNumber: EditText? = null
    private var mEmail: EditText? = null
    private var mCpf: EditText? = null
    private var mPassword: EditText? = null
    private var mPasswordConfirmation: EditText? = null
    private var mTerms: SwitchMaterial? = null

    private var mNameError: TextView? = null
    private var mPhoneNumberError: TextView? = null
    private var mEmailError: TextView? = null
    private var mCpfError: TextView? = null
    private var mPasswordError: TextView? = null
    private var mPasswordConfirmationError: TextView? = null
    private var mTermsError: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user)

        init()

        findViewById<Button>(R.id.create_user_register)
            .setOnClickListener {
                validateItems(
                    CreateUser(
                        mName?.text.toString(),
                        mPhoneNumber?.text.toString(),
                        mEmail?.text.toString(),
                        mCpf?.text.toString(),
                        (if (mPassword?.text.toString()
                                .equals(mPasswordConfirmation?.text.toString())
                        ) mPassword?.text.toString() else ""),
                        (mTerms?.isChecked == true)
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
        mName = findViewById(R.id.create_user_name_edittext)
        mPhoneNumber = findViewById(R.id.create_user_phone_number_edittext)
        mEmail = findViewById(R.id.create_user_email_edittext)
        mCpf = findViewById(R.id.create_user_cpf_edittext)
        mPassword = findViewById(R.id.create_user_password_edittext)
        mPasswordConfirmation =
            findViewById(R.id.create_user_confirm_password_edittext)
        mTerms = findViewById(R.id.create_user_terms_service_switch)

        mNameError = findViewById(R.id.create_user_name_text_error)
        mPhoneNumberError = findViewById(R.id.create_user_phone_number_text_error)
        mEmailError = findViewById(R.id.create_user_email_text_error)
        mCpfError = findViewById(R.id.create_user_cpf_text_error)
        mPasswordError = findViewById(R.id.create_user_password_text_error)
        mPasswordConfirmationError = findViewById(R.id.create_user_confirm_password_text_error)
        mTermsError = findViewById(R.id.create_user_switch_text_error)
    }

    private fun validateItems(createUser: CreateUser) {
        Log.i(TAG, "validadeItems()")
        var error = false
        mNameError!!.visibility = View.INVISIBLE
        mPhoneNumberError!!.visibility = View.INVISIBLE
        mEmailError!!.visibility = View.INVISIBLE
        mCpfError!!.visibility = View.INVISIBLE
        mPasswordError!!.visibility = View.INVISIBLE
        mPasswordConfirmationError!!.visibility = View.INVISIBLE
        mTermsError!!.visibility = View.INVISIBLE

        if (createUser.name.isNullOrEmpty()) {
            error = true
            mNameError!!.visibility = View.VISIBLE
        }
        if (createUser.phoneNumber.isNullOrEmpty()) {
            error = true
            mPhoneNumberError!!.visibility = View.VISIBLE
        }
        if (createUser.email.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(
                createUser.email.toString()
            ).matches()
        ) {
            error = true
            mEmailError!!.visibility = View.VISIBLE
        }
        if (createUser.cpf.isNullOrEmpty()) {
            error = true
            mCpfError!!.visibility = View.VISIBLE
        }
        if (createUser.pass.isNullOrEmpty()) {
            error = true
            mPasswordError!!.visibility = View.VISIBLE
            mPasswordConfirmationError!!.visibility = View.VISIBLE
        }
        if (createUser.acceptTerms == false) {
            error = true
            mTermsError!!.visibility = View.VISIBLE
        }

        if (!error)
            executePostNewUser(createUser)
    }

    private fun executePostNewUser(user: CreateUser) {
        Log.i(TAG, "executePostNewUser()")

        Log.i(
            TAG, "validadeItems user.name: " + user.name.toString() +
                    "\nuser.phoneNumber: " + user.phoneNumber.toString() +
                    "\nuser.email: " + user.email.toString() +
                    "\nuser.cpf: " + user.cpf.toString() +
                    "\nuser.pass: " + user.pass.toString() +
                    "\nuser.acceptTerms: " + user.acceptTerms +
                    "\nURL_USER: " + ConstantsUtils.URL_USER
        )

        RetrofitClient.instance.postNewUser(
            user.name!!,
            user.email!!,
            user.pass!!,
            user.cpf!!,
            user.phoneNumber!!,
            user.acceptTerms!!
        )
            .enqueue(object : Callback<UserCreateResponse> {
                override fun onResponse(
                    call: Call<UserCreateResponse>,
                    response: Response<UserCreateResponse>
                ) {
                    if (response.isSuccessful) {
                        showReturnFromApi(response.message())
                        onBackPressed()
                    } else {
                        showReturnFromApi("Code: " + response.code() + " Message: " + response.message())
                    }
                }

                override fun onFailure(call: Call<UserCreateResponse>, t: Throwable) {
                    showReturnFromApi("ERROR " + t.cause + " message: " + t.message!!)
                }
            })
    }

    fun showReturnFromApi(message: String) {
        Log.i(TAG, "executePostNewUser() $message")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}