package com.example.recibodigitalalexventurini.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.api.RetrofitClient
import com.example.recibodigitalalexventurini.model.CategoryResponse
import com.example.recibodigitalalexventurini.model.ListReceiptsResponse
import com.example.recibodigitalalexventurini.screens.AllReceiptsActivity
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryAdapter(
    private val mList: List<CategoryResponse>,
    private val mActivity: Activity
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val TAG = ConstantsUtils.LOGTAG + "CategoryAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryResponse = mList[position]

        holder.categoryBackground.setBackgroundColor(Color.parseColor(categoryResponse.color))
        holder.categoryName.text = categoryResponse.category

        // TODO tentar resolver esse problema no futuro
        // Improvise because we couldn't access Resources String:
        // Resources.getSystem().getString(R.string.home_screen_receipts)
        val receipt = "%s receipt"
        val receipts = "%s receipts"

        holder.categoryReceiptCount.text = if (categoryResponse.countReceipts == 0) {
            String.format(
                receipts,
                categoryResponse.countReceipts.toString()
            )
        } else {
            String.format(
                receipt,
                categoryResponse.countReceipts.toString()
            )
        }

        holder.bind(mList[position], mActivity)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG = ConstantsUtils.LOGTAG + "CategoryAdapter.ViewHolder"

        val categoryBackground: View = this.itemView.findViewById(R.id.category_background)
        val categoryName: TextView = this.itemView.findViewById(R.id.category_name)
        val categoryReceiptCount: TextView = this.itemView.findViewById(R.id.category_receipt_count)

        fun bind(
            item: CategoryResponse,
            mActivity: Activity
        ) {
            itemView.setOnClickListener {
                Log.i(TAG, "setOnClickListener() id: ${item.id}")
                getReceiptList(item.id, mActivity)
            }
        }

        private fun selectReceiptAvailable(
            id: String,
            listReceipt: ListReceiptsResponse,
            activity: Activity
        ) {
            Log.i(TAG, "selectReceiptAvailable()")
            listReceipt.receipts.removeIf { !it.categories.contains(id) }

            val allReceiptsScreen = Intent(activity, AllReceiptsActivity::class.java)
            allReceiptsScreen.putExtra(ConstantsUtils.RECEIPTS_EXTRA, listReceipt)
            activity.startActivity(allReceiptsScreen)
        }

        private fun getReceiptList(id: String, activity: Activity) {
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
                            selectReceiptAvailable(id, response.body()!!, activity)
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
    }
}
