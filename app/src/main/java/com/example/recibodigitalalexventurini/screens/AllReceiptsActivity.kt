package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.adapter.ReceiptAdapter
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.ListReceiptsResponse
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllReceiptsActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "AllReceiptsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.all_receipts_screen)

        getReceiptList()
    }

    fun backButton(view: View) {
        Log.i(TAG, "getAllReceipts()")
        onBackPressed()
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

    private fun updateReceipt(receiptList: List<ReceiptResponse>?) {
        Log.i(TAG, "updateReceipt()")

        if (receiptList != null) {
            val recyclerview = findViewById<RecyclerView>(R.id.all_receipts_recyclerview)
            val adapter = ReceiptAdapter(receiptList)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        } else {
            Log.i(TAG, "updateReceipt() receiptList is null!")
        }
    }
}