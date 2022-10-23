package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.adapter.ReceiptAdapter
import com.example.recibodigitalalexventurini.model.ListReceiptsResponse
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils

class AllReceiptsActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "AllReceiptsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.all_receipts_screen)

        val receipts = intent.extras?.get(ConstantsUtils.RECEIPTS_EXTRA) as ListReceiptsResponse
        updateReceipt(receipts.receipts)
    }

    fun backButton(view: View) {
        Log.i(TAG, "getAllReceipts()")
        onBackPressed()
    }

    private fun updateReceipt(receiptList: List<ReceiptResponse>?) {
        Log.i(TAG, "updateReceipt()")

        if (receiptList != null) {
            val recyclerview = findViewById<RecyclerView>(R.id.all_receipts_recyclerview)
            val adapter = ReceiptAdapter(receiptList, this)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        } else {
            Log.i(TAG, "updateReceipt() receiptList is null!")
        }
    }
}