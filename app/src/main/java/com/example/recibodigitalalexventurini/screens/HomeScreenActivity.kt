package com.example.recibodigitalalexventurini.screens

import android.content.Intent
import com.example.recibodigitalalexventurini.adapter.CategoryAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.*
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "HomeScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.home_screen)

        val user = intent.extras?.get(ConstantsUtils.USER_INFO_EXTRAS) as LoginResponse

        updateUserName(user.name)

        RetrofitClient.setAuth(user.token)
        getCategoriesList()
    }

    fun backButton(view: View) {
        Log.i(TAG, "getAllReceipts()")
        onBackPressed()
    }

    fun getAllReceipts(view: View) {
        Log.i(TAG, "getAllReceipts()")

        val allReceiptsScreen = Intent(this, AllReceiptsActivity::class.java)
        this.startActivity(allReceiptsScreen)
    }

    private fun updateUserName(name: String) {
        Log.i(TAG, "updateUserName() name: $name")
        findViewById<RelativeLayout>(R.id.home_header)
            .findViewById<TextView>(R.id.header_user_name).text =
            String.format(getString(R.string.home_screen_welcome), name)
    }

    private fun getCategoriesList() {
        Log.i(TAG, "getCategoriesList()")
        RetrofitClient.instance.getCategories()
            .enqueue(object : Callback<ListCategoriesResponse> {

                override fun onResponse(
                    call: Call<ListCategoriesResponse>,
                    response: Response<ListCategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.i(
                            TAG,
                            "getCategoriesList() onResponse() code: " + response.body()?.code +
                                    " resultMessage: " + response.body()?.resultMessage
                        )
                        updateCategories(response.body()?.categories)
                    }
                }

                override fun onFailure(call: Call<ListCategoriesResponse>, t: Throwable) {
                    Log.e(TAG, "Failed to take categories t:'${t.message}' call:$call")
                }
            })
    }

    private fun updateCategories(categoriesList: List<CategoryResponse>?) {
        Log.i(TAG, "updateCategories()")
        if (categoriesList != null) {
            val recyclerview = findViewById<RecyclerView>(R.id.category_recyclerview)
            val adapter = CategoryAdapter(categoriesList)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        } else {
            Log.i(TAG, "updateCategories() categoriesList is null!")
        }
    }
}