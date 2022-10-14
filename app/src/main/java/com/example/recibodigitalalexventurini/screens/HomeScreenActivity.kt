package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

        if (user.token != "") {
            RetrofitClient.setAuth(user.token)
            getReceiptList()
            getCategoriesList()
        } else {
            Log.e(TAG, "Token is null!")
        }
    }

    private fun getReceiptList() {
        Log.i(TAG, "getReceiptList()")
        RetrofitClient.instance.getReceipts()
            .enqueue(object : Callback<ListReceiptsResponse> {

                override fun onResponse(
                    call: Call<ListReceiptsResponse>,
                    response: Response<ListReceiptsResponse>
                ) {
                    if (response.isSuccessful) {
                        updateReceipt(response.body()?.receipts)
                    }
                }

                override fun onFailure(call: Call<ListReceiptsResponse>, t: Throwable) {
                    Log.e(TAG, "Failed to take receipts t:'${t.message}' call:$call")
                }
            })
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
                            "onResponse() code: " + response.body()?.code +
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

    private fun updateReceipt(receiptList: List<ReceiptResponse>?) {
        Log.i(TAG, "updateReceipt()")

        // TODO Receives the receipt list and missing the usage about this
        if (receiptList != null) {
            for (receipt in receiptList) {
                Log.i(
                    TAG, "updateReceipt() id: " + receipt.id +
                            " idUser: " + receipt.idUser + " value: " + receipt.value
                )
            }
        } else {
            Log.i(TAG, "updateReceipt() receiptList is null!")
        }
    }

    private fun updateCategories(categoriesList: List<CategoryResponse>?) {
        Log.i(TAG, "updateCategories()")

        // TODO Receives the Category list and missing the usage about this
        if (categoriesList != null) {
            for (category in categoriesList) {
                Log.i(
                    TAG, "updateCategories() id: " + category.id +
                            " category: " + category.category + " color: " + category.color
                )
            }
        } else {
            Log.i(TAG, "updateCategories() categoriesList is null!")
        }
    }
}