package com.example.recibodigitalalexventurini.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.CategoryResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils

class CategoryAdapter(private val mList: List<CategoryResponse>) :
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
                Integer.toString(categoryResponse.countReceipts)
            )
        }

        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG = ConstantsUtils.LOGTAG + "CategoryAdapter.ViewHolder"

        val categoryBackground: View = this.itemView.findViewById(R.id.category_background)
        val categoryName: TextView = this.itemView.findViewById(R.id.category_name)
        val categoryReceiptCount: TextView = this.itemView.findViewById(R.id.category_receipt_count)

        fun bind(item: CategoryResponse) {
            itemView.setOnClickListener {
                Log.i(TAG, "setOnClickListener id: ${item.id}")
            }
        }
    }
}