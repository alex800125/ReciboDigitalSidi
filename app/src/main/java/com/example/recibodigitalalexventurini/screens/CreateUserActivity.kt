package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.CreateUser
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import com.google.android.material.switchmaterial.SwitchMaterial

class CreateUserActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "CreateUserActivity"

    private var mName: EditText? = null
    private var mPhoneNumber: EditText? = null
    private var mEmail: EditText? = null
    private var mCpf: EditText? = null
    private var mPassword: EditText? = null
    private var mPasswordConfirmation: EditText? = null
    private var mTerms: SwitchMaterial? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user)

        init()

        findViewById<Button>(R.id.create_user_register)
            .setOnClickListener {
                validadeItems(
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

        findViewById<ImageView>(R.id.create_user_back_button)
            .setOnClickListener {
                onBackPressed()
            }
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
    }

    private fun validadeItems(createUser: CreateUser) {
        Log.i(TAG, "validadeItems()")
        Log.i(
            TAG, "validadeItems user.name: " + createUser.name.toString() +
                    "user.phoneNumber: " + createUser.phoneNumber.toString() +
                    "user.email: " + createUser.email.toString() +
                    "user.cpf: " + createUser.cpf.toString() +
                    "user.pass: " + createUser.pass.toString() +
                    "user.acceptTerms: " + createUser.acceptTerms
        )
        // TODO implementar validação e enviar pra API
    }
}