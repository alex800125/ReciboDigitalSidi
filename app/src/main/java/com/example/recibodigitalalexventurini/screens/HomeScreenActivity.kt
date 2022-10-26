package com.example.recibodigitalalexventurini.screens

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.adapter.CategoryAdapter
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.*
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


class HomeScreenActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "HomeScreenActivity"

    lateinit var mListReceipts: ListReceiptsResponse
    lateinit var mListCategories: ListCategoriesResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.home_screen)

        val user = intent.extras?.get(ConstantsUtils.USER_EXTRA) as LoginResponse

        updateUser(user.name, user.avatar)

        RetrofitClient.setAuth(user.token)
        getReceiptList()
    }

    override fun onResume() {
        Log.i(TAG, "onResume")
        super.onResume()
        getCategoriesList()
    }

    private fun updateMainCategoryButton() {
        val allReceiptsButton = findViewById<CardView>(R.id.all_receipts)
        val allFavoriteReceiptsButton = findViewById<CardView>(R.id.favorite_receipts)

        allReceiptsButton.findViewById<View>(R.id.category_background)
            .setBackgroundColor(resources.getColor(R.color.category_card_view_background))
        allReceiptsButton.findViewById<TextView>(R.id.category_name).text =
            resources.getString(R.string.home_screen_all_receipts)
        allReceiptsButton.findViewById<TextView>(R.id.category_receipt_count).text =
            String.format(getString(R.string.home_screen_receipts), mListReceipts.receipts.size)

        allFavoriteReceiptsButton.findViewById<View>(R.id.category_background)
            .setBackgroundColor(resources.getColor(R.color.category_card_view_background))
        allFavoriteReceiptsButton.findViewById<TextView>(R.id.category_name).text =
            resources.getString(R.string.home_screen_favorite_receipts)

        // TODO atualizar favorite item
        allFavoriteReceiptsButton.findViewById<TextView>(R.id.category_receipt_count).text =
            String.format(getString(R.string.home_screen_receipts), mListReceipts.receipts.size)

        allFavoriteReceiptsButton.setOnClickListener {
            Log.i(TAG, "allFavoriteReceiptsButton.onClick()")
        }

        allReceiptsButton.setOnClickListener {
            Log.i(TAG, "allReceiptsButton.onClick()")

            val allReceiptsScreen = Intent(this, AllReceiptsActivity::class.java)
            allReceiptsScreen.putExtra(ConstantsUtils.RECEIPTS_EXTRA, mListReceipts)
            this.startActivity(allReceiptsScreen)
        }
    }

    fun backButton(view: View) {
        Log.i(TAG, "backButton()")
        onBackPressed()
    }

    private fun updateUser(name: String, urlAvatar: String) {
        Log.i(TAG, "updateUser()")

        CoroutineScope(Dispatchers.IO).launch {
            val avatarImage = Utils.getImageFromUrl(URL(urlAvatar))

            withContext(Dispatchers.Main) {
                findViewById<RelativeLayout>(R.id.home_header)
                    .findViewById<ImageView>(R.id.header_avatar).setImageBitmap(avatarImage)
                findViewById<RelativeLayout>(R.id.home_header)
                    .findViewById<TextView>(R.id.header_user_name).text =
                    String.format(getString(R.string.home_screen_welcome), name)
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
                        mListCategories = response.body()!!
                        updateCategories()
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

    private fun updateCategories() {
        Log.i(TAG, "updateCategories()")
        val recyclerview = findViewById<RecyclerView>(R.id.category_recyclerview)
        val adapter = CategoryAdapter(mListCategories.categories)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
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
                        mListReceipts = response.body()!!
                        updateMainCategoryButton()
                    }
                }

                override fun onFailure(call: Call<ListReceiptsResponse>, t: Throwable) {
                    Log.e(
                        TAG,
                        "getReceiptList() Failed to take receipts t:'${t.message}' call:$call"
                    )
                }
            })
    }

    fun executeCreateCategory(view: View) {
        Log.i(TAG, "executeCreateCategory()")

        val categoryAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val categoryEdittext = EditText(this)
        categoryAlertDialog.setTitle(getString(R.string.home_screen_create_category_title))
        categoryAlertDialog.setMessage(getString(R.string.home_screen_create_category_message))
        categoryAlertDialog.setView(categoryEdittext)

        categoryAlertDialog.setPositiveButton(getString(R.string.home_screen_create_category_positive_button))
        { dialog, whichButton ->
            Log.i(TAG, "executeCreateCategory() create edittext: " + categoryEdittext.text)
            postNewCategory(categoryEdittext.text.toString())
        }

        categoryAlertDialog.setNegativeButton(
            getString(R.string.home_screen_create_category_negative_button)
        ) { dialog, whichButton ->
            Log.i(TAG, "executeCreateCategory() cancel")
        }

        categoryAlertDialog.show()
    }

    private fun postNewCategory(categoryName: String) {
        Log.i(TAG, "postNewCategory() categoryName: $categoryName")
        RetrofitClient.instance.postNewCategory(categoryName)
            .enqueue(object : Callback<NewCategoryResponse> {

                override fun onResponse(
                    call: Call<NewCategoryResponse>,
                    response: Response<NewCategoryResponse>
                ) {
                    showPostNewCategoryResponse(response.code().toString(), response.message())
                }

                override fun onFailure(call: Call<NewCategoryResponse>, t: Throwable) {
                    Log.e(
                        TAG,
                        "postNewCategory() Failed to take receipts t:'${t.message}' call:$call"
                    )
                }
            })
    }

    private fun showPostNewCategoryResponse(code: String, message: String) {
        Log.i(TAG, "showPostNewCategoryResponse() code: $code, message: $message")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        getCategoriesList()
    }
}