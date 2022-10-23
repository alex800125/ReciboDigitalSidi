package com.example.recibodigitalalexventurini.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import com.example.recibodigitalalexventurini.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ReceiptDetailsActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "ReceiptDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.receipt_details_screen)

        val receipt = intent.extras?.get(ConstantsUtils.RECEIPT_DETAILS_EXTRA) as ReceiptResponse
        updateReceipt(receipt)
    }

    fun backButton(view: View) {
        Log.i(TAG, "getAllReceipts()")
        onBackPressed()
    }

    fun executeFavorite(view: View) {
        Log.i(TAG, "executeFavorite()")
        // TODO implement
        Toast.makeText(this, "execute Favorite", Toast.LENGTH_SHORT).show()
    }

    fun executeAddCategory(view: View) {
        Log.i(TAG, "executeAddCategory()")
        // TODO implement
        Toast.makeText(this, "execute AddCategory", Toast.LENGTH_SHORT).show()
    }

    private fun updateReceipt(receipt: ReceiptResponse) {
        Log.i(TAG, "updateReceipt()")
        CoroutineScope(Dispatchers.IO).launch {
            val merchantImage = Utils.getImageFromUrl(URL(receipt.merchantImage))

            withContext(Dispatchers.Main) {
                findViewById<ImageView>(R.id.receipt_detail_merchant_image)
                    .setImageBitmap(merchantImage)
                findViewById<TextView>(R.id.receipt_detail_merchant_name).text =
                    receipt.merchantName
                findViewById<TextView>(R.id.receipt_detail_value).text =
                    String.format(getString(R.string.receipt_details_value), receipt.value)
                findViewById<TextView>(R.id.receipt_detail_date).text =
                    receipt.date
                findViewById<TextView>(R.id.receipt_detail_payment).text =
                    String.format(
                        getString(R.string.receipt_details_payment),
                        (
                                if (receipt.paymentMethod == "1")
                                    getString(R.string.receipt_details_payment_credit_card)
                                else
                                    getString(R.string.receipt_details_payment_debit_card)
                                ), receipt.cardInfoBrand
                    )
                findViewById<TextView>(R.id.receipt_detail_authentication_code).text =
                    String.format(
                        getString(R.string.receipt_details_authentication_code),
                        receipt.authentication
                    )
            }
        }
    }
}