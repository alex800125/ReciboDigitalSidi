package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.adapter.ReceiptAdapter
import com.example.recibodigitalalexventurini.model.ListReceiptsResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils

class AllReceiptsActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "AllReceiptsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.all_receipts_screen)

        val receipts = intent.extras?.get(ConstantsUtils.RECEIPTS_EXTRA) as ListReceiptsResponse
        val adapter = ReceiptAdapter(receipts.receipts, this)

        updateReceipt(adapter)
        updateSearch(adapter)
    }

    fun backButton(view: View) {
        Log.i(TAG, "backButton()")
        onBackPressed()
    }

    private fun updateReceipt(adapter: ReceiptAdapter) {
        Log.i(TAG, "updateReceipt()")
        val recyclerview = findViewById<RecyclerView>(R.id.all_receipts_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    private fun updateSearch(adapter: ReceiptAdapter) {
        Log.i(TAG, "updateSearch()")
        val receiptsSearch = findViewById<SearchView>(R.id.all_receipts_search)
        receiptsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                if (adapter.itemCount == 0) {
                    findViewById<TextView>(R.id.all_receipts_no_item).visibility = View.VISIBLE
                } else {
                    findViewById<TextView>(R.id.all_receipts_no_item).visibility = View.INVISIBLE
                }
                return false
            }
        })
    }
}