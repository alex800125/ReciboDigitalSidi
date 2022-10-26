package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.adapter.AddOrRemoveCategoryAdapter
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.CategoryResponse
import com.example.recibodigitalalexventurini.model.ListCategoriesResponse
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import com.example.recibodigitalalexventurini.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class ReceiptDetailsActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "ReceiptDetailsActivity"

    lateinit var mReceipt: ReceiptResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.receipt_details_screen)

        mReceipt = intent.extras?.get(ConstantsUtils.RECEIPT_DETAILS_EXTRA) as ReceiptResponse
        updateReceipt()
    }

    fun backButton(view: View) {
        Log.i(TAG, "backButton()")
        onBackPressed()
    }

    fun executeFavorite(view: View) {
        Log.i(TAG, "executeFavorite()")
        // TODO implement
        Toast.makeText(this, "execute Favorite", Toast.LENGTH_SHORT).show()
    }

    fun executeAddCategory(view: View) {
        Log.i(TAG, "executeAddCategory()")
        getCategoriesList()
    }

    private fun updateReceipt() {
        Log.i(TAG, "updateReceipt()")
        CoroutineScope(Dispatchers.IO).launch {
            val merchantImage = Utils.getImageFromUrl(URL(mReceipt.merchantImage))

            withContext(Dispatchers.Main) {
                findViewById<ImageView>(R.id.receipt_detail_merchant_image)
                    .setImageBitmap(merchantImage)
                findViewById<TextView>(R.id.receipt_detail_merchant_name).text =
                    mReceipt.merchantName
                findViewById<TextView>(R.id.receipt_detail_value).text =
                    String.format(getString(R.string.receipt_details_value), mReceipt.value)
                findViewById<TextView>(R.id.receipt_detail_date).text =
                    mReceipt.date
                findViewById<TextView>(R.id.receipt_detail_payment).text =
                    String.format(
                        getString(R.string.receipt_details_payment),
                        (
                                if (mReceipt.paymentMethod == "1")
                                    getString(R.string.receipt_details_payment_credit_card)
                                else
                                    getString(R.string.receipt_details_payment_debit_card)
                                ), mReceipt.cardInfoBrand
                    )
                findViewById<TextView>(R.id.receipt_detail_authentication_code).text =
                    String.format(
                        getString(R.string.receipt_details_authentication_code),
                        mReceipt.authentication
                    )
            }
        }
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
                        updateAddCategories(response.body()!!.categories)
                    }
                }

                override fun onFailure(call: Call<ListCategoriesResponse>, t: Throwable) {
                    Log.e(
                        TAG,
                        "getCategoriesList() Failed to take categories t:'${t.message}' call:$call"
                    )
                }
            })
    }

    private fun updateAddCategories(categories: List<CategoryResponse>) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.add_or_remove_category, null) as View
        val rv =
            dialogView.findViewById<View>(R.id.add_or_remove_category_recyclerview) as RecyclerView
        val adapter = AddOrRemoveCategoryAdapter(categories, mReceipt)
        rv.layoutManager = LinearLayoutManager(this);
        rv.adapter = adapter

        builder.setView(dialogView)

        val categoryAlertDialog = builder.create()

        categoryAlertDialog.setTitle(getString(R.string.add_or_remove_category_title))
        categoryAlertDialog.setMessage(getString(R.string.add_or_remove_category_description))

        categoryAlertDialog.show()
    }
}