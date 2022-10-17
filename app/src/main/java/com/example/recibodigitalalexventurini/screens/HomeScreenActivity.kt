package com.example.recibodigitalalexventurini.screens

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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
        getReceiptList()
        getCategoriesList()
    }

    private fun updateUserName(name: String) {
        Log.i(TAG, "updateUserName() name: $name")
        findViewById<RelativeLayout>(R.id.home_header)
            .findViewById<TextView>(R.id.header_user_name).text =
            String.format(getString(R.string.home_screen_welcome), name)
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
                        Log.i(
                            TAG,
                            "getReceiptList() onResponse() code: " + response.body()?.code +
                                    " resultMessage: " + response.body()?.resultMessage
                        )
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
        if (categoriesList != null) {
            for (category in categoriesList) {
                populateTable(category)
            }
        } else {
            Log.i(TAG, "updateCategories() categoriesList is null!")
        }
    }

    private fun populateTable(category: CategoryResponse) {
        Log.i(
            TAG, "populateTable() id: " + category.id + " category: " + category.category +
                    " color: " + category.color + " countReceipts: " + category.countReceipts
        )

        val tableLayout = findViewById<TableLayout>(R.id.categories_table_layout)
        val tableRowLayout = View.inflate(this, R.layout.table_row_layout, null) as TableRow
        val cardView = tableRowLayout.findViewById<CardView>(R.id.category_card_view)

        cardView.findViewById<View>(R.id.category_background)
            .setBackgroundColor(Color.parseColor(category.color))

        cardView.findViewById<TextView>(R.id.category_name).text = category.category

        cardView.findViewById<TextView>(R.id.category_receipt_count).text =
            if (category.countReceipts == 0) {
                String.format(getString(R.string.home_screen_receipt), category.countReceipts)
            } else {
                String.format(getString(R.string.home_screen_receipts), category.countReceipts)
            }

        tableLayout.addView(tableRowLayout)
    }
}