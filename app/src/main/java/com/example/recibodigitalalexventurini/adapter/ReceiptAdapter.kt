package com.example.recibodigitalalexventurini.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.ReceiptResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils

class ReceiptAdapter(private val mList: List<ReceiptResponse>) :
    RecyclerView.Adapter<ReceiptAdapter.ViewHolder>() {
    private val TAG = ConstantsUtils.LOGTAG + "ReceiptAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.receipt_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder()")
        val receiptResponse = mList[position]

        // TODO atualizar pra pegar imagem do link fornecido
        holder.receiptMerchantImage.setImageResource(R.drawable.icon_image_default)
        holder.receiptMerchantName.text = receiptResponse.merchantName
        holder.receiptValue.text = receiptResponse.value
        holder.receiptDate.text = receiptResponse.date

        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG = ConstantsUtils.LOGTAG + "ReceiptAdapter.ViewHolder"

        val receiptMerchantImage: ImageView =
            this.itemView.findViewById(R.id.receipt_merchant_image)
        val receiptMerchantName: TextView = this.itemView.findViewById(R.id.receipt_merchant_name)
        val receiptValue: TextView = this.itemView.findViewById(R.id.receipt_value)
        val receiptDate: TextView = this.itemView.findViewById(R.id.receipt_date)

        fun bind(item: ReceiptResponse) {
            itemView.setOnClickListener {
                Log.i(TAG, "setOnClickListener id: ${item.id}")
            }
        }
    }
}