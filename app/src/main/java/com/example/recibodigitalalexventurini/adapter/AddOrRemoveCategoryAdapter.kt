package com.example.recibodigitalalexventurini.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.AddCategoryForReceiptResponse
import com.example.recibodigitalalexventurini.model.CategoryResponse
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddOrRemoveCategoryAdapter(
    private val mList: List<CategoryResponse>,
    private val mReceipt: ReceiptResponse
) :
    RecyclerView.Adapter<AddOrRemoveCategoryAdapter.ViewHolder>() {
    private val TAG = ConstantsUtils.LOGTAG + "AddOrRemoveCategory"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_or_remove_category_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryResponse = mList[position]

        holder.addOrRemoveCategoryName.text = categoryResponse.category
        holder.addOrRemoveCategorySwitchMaterial.isChecked =
            mReceipt.categories.contains(categoryResponse.id)

        holder.addOrRemoveCategorySwitchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            putCategoriesReceipt(mReceipt.id, categoryResponse.id, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addOrRemoveCategoryName: TextView =
            this.itemView.findViewById(R.id.add_or_remove_category_name)
        val addOrRemoveCategorySwitchMaterial: SwitchMaterial =
            this.itemView.findViewById(R.id.add_or_remove_category_active)
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
                    (response.isSuccessful)
                    Log.i(
                        TAG,
                        "putCategoriesReceipt() response.isSuccessful: " + response.isSuccessful + " code: $response"
                    )
                    if (status) {
                        mReceipt.categories.add(categoryId)
                    } else {
                        mReceipt.categories.remove(categoryId)
                    }
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