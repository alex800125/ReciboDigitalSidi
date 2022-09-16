package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.User
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

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user)

        init()
        val termsService = findViewById<SwitchMaterial>(R.id.create_user_terms_service_switch)

        findViewById<Button>(R.id.create_user_register)
            .setOnClickListener {
                validadeItems(
                    User(
                        mName?.text.toString(),
                        mPhoneNumber?.text.toString(),
                        mEmail?.text.toString(),
                        mCpf?.text.toString(),
                        (if (mPassword?.text.toString()
                                .equals(mPasswordConfirmation?.text.toString())
                        ) mPassword?.text.toString() else ""),
                        termsService.isEnabled
                    )
                )
            }

        findViewById<ImageView>(R.id.create_user_back_button)
            .setOnClickListener {
                onBackPressed()
            }
    }

    private fun init() {
        mName = findViewById(R.id.create_user_name_edittext)
        mPhoneNumber = findViewById(R.id.create_user_phone_number_edittext)
        mEmail = findViewById(R.id.create_user_email_edittext)
        mCpf = findViewById(R.id.create_user_cpf_edittext)
        mPassword = findViewById(R.id.create_user_password_edittext)
        mPasswordConfirmation =
            findViewById(R.id.create_user_confirm_password_edittext)
    }

    private fun validadeItems(user: User) {
        Log.i(TAG, "validadeItems user.name: " + user.name.toString())
        // TODO implementar validação e enviar pra API
    }
}