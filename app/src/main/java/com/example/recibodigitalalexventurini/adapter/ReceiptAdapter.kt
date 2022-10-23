package com.example.recibodigitalalexventurini.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.screens.ReceiptDetailsActivity
import com.example.recibodigitalalexventurini.utils.ConstantsUtils
import com.example.recibodigitalalexventurini.utils.Utils
import kotlinx.coroutines.*
import java.net.URL
import java.util.*

class ReceiptAdapter(
    private val mList: List<ReceiptResponse>,
    private val mActivity: Activity
) :
    RecyclerView.Adapter<ReceiptAdapter.ViewHolder>(), Filterable {
    private val TAG = ConstantsUtils.LOGTAG + "ReceiptAdapter"
    var receiptsFilterList: List<ReceiptResponse>

    init {
        receiptsFilterList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.receipt_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receiptResponse = receiptsFilterList[position]

        CoroutineScope(Dispatchers.IO).launch {
            val merchantIcon = Utils.getImageFromUrl(URL(receiptResponse.merchantIcon))

            withContext(Dispatchers.Main) {
                holder.receiptMerchantIcon.setImageBitmap(merchantIcon)
                holder.receiptMerchantName.text = receiptResponse.merchantName
                holder.receiptValue.text = receiptResponse.value
                holder.receiptDate.text = receiptResponse.date
            }
        }

        holder.bind(receiptResponse, mActivity)
    }

    override fun getItemCount(): Int {
        return receiptsFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    receiptsFilterList = mList
                } else {
                    val resultList = ArrayList<ReceiptResponse>()
                    for (row in mList) {
                        if (row.merchantName.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    receiptsFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = receiptsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                receiptsFilterList = results?.values as List<ReceiptResponse>
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG = ConstantsUtils.LOGTAG + "ReceiptAdapter.ViewHolder"

        //      val receiptBackground: TextView = this.itemView.findViewById(R.id.receipt_background)
        val receiptMerchantIcon: ImageView =
            this.itemView.findViewById(R.id.receipt_merchant_icon)
        val receiptMerchantName: TextView =
            this.itemView.findViewById(R.id.receipt_merchant_name)
        val receiptValue: TextView = this.itemView.findViewById(R.id.receipt_value)
        val receiptDate: TextView = this.itemView.findViewById(R.id.receipt_date)

        fun bind(item: ReceiptResponse, activity: Activity) {
            itemView.setOnClickListener {
                Log.i(TAG, "setOnClickListener id: ${item.id}")
                val receiptDetailsScreen = Intent(activity, ReceiptDetailsActivity::class.java)
                receiptDetailsScreen.putExtra(ConstantsUtils.RECEIPT_DETAILS_EXTRA, item)
                activity.startActivity(receiptDetailsScreen)
            }
        }
    }
}