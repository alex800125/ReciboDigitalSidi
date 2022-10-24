package com.example.recibodigitalalexventurini.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.AddCategoryForReceiptResponse
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

    var mReceipt: ReceiptResponse? = null

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

        // TODO precisa terminar de implementar a sequencia de funções abaixo
        // getCategoriesList > pega lista de categorias existentes.
        // updateAddCategories > com base na lista de categorias, cria um item do tipo
        //      add_or_remove_category_card_view.xml para cada item da lista.
        // updateApiDatabase > Após o usuário marcar as categorias que quiser desse recibo, chama
        //      essa função para atualizar na API.
        // putCategoriesReceipt > Para cada categoria, realiza uma chamada dessa função para atualizar.
        getCategoriesList()
    }

    private fun updateReceipt() {
        Log.i(TAG, "updateReceipt()")
        CoroutineScope(Dispatchers.IO).launch {
            val merchantImage = Utils.getImageFromUrl(URL(mReceipt!!.merchantImage))

            withContext(Dispatchers.Main) {
                findViewById<ImageView>(R.id.receipt_detail_merchant_image)
                    .setImageBitmap(merchantImage)
                findViewById<TextView>(R.id.receipt_detail_merchant_name).text =
                    mReceipt!!.merchantName
                findViewById<TextView>(R.id.receipt_detail_value).text =
                    String.format(getString(R.string.receipt_details_value), mReceipt!!.value)
                findViewById<TextView>(R.id.receipt_detail_date).text =
                    mReceipt!!.date
                findViewById<TextView>(R.id.receipt_detail_payment).text =
                    String.format(
                        getString(R.string.receipt_details_payment),
                        (
                                if (mReceipt!!.paymentMethod == "1")
                                    getString(R.string.receipt_details_payment_credit_card)
                                else
                                    getString(R.string.receipt_details_payment_debit_card)
                                ), mReceipt!!.cardInfoBrand
                    )
                findViewById<TextView>(R.id.receipt_detail_authentication_code).text =
                    String.format(
                        getString(R.string.receipt_details_authentication_code),
                        mReceipt!!.authentication
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
        val categoryAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val categoryEdittext = EditText(this)
        val dataItems = EditText(this)

        for (item in categories) {

        }

        categoryAlertDialog.setTitle(getString(R.string.home_screen_create_category_title))
        categoryAlertDialog.setMessage(getString(R.string.home_screen_create_category_message))
        categoryAlertDialog.setView(categoryEdittext)

        categoryAlertDialog.setPositiveButton(getString(R.string.home_screen_create_category_positive_button))
        { dialog, whichButton ->
            Log.i(TAG, "executeCreateCategory() create edittext: " + categoryEdittext.text)
            //updateApiDatabase()
        }

        categoryAlertDialog.setNegativeButton(
            getString(R.string.home_screen_create_category_negative_button)
        ) { dialog, whichButton ->
            Log.i(TAG, "executeCreateCategory() cancel")
        }

        categoryAlertDialog.show()
    }

    private fun updateApiDatabase() {
        Log.i(TAG, "updateApiDatabase()")
        // criar um for e para cada item da lista, chamar a função 'putCategoriesReceipt()'
    }

    private fun putCategoriesReceipt(receiptId: String, categoryId: String, status: Boolean) {
        Log.i(
            TAG,
            "putCategoriesReceipt() receiptId: " + receiptId +
                    " categoryId: " + categoryId + " status: " + status
        )

        RetrofitClient.instance.putCategoryReceipt(receiptId, categoryId, status)
            .enqueue(object : Callback<AddCategoryForReceiptResponse> {

                override fun onResponse(
                    call: Call<AddCategoryForReceiptResponse>,
                    response: Response<AddCategoryForReceiptResponse>
                ) {
                    Log.i(
                        TAG,
                        "putCategoriesReceipt() code: $response.code().toString() " +
                                "message: $response.message"
                    )
                }

                override fun onFailure(call: Call<AddCategoryForReceiptResponse>, t: Throwable) {
                    Log.e(
                        TAG,
                        "putCategoriesReceipt() Failed to take receipts t:'${t.message}' call:$call"
                    )
                }
            })
    }
}