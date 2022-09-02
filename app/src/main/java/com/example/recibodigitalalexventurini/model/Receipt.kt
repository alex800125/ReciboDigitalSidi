package com.example.recibodigitalalexventurini.model

import android.graphics.Bitmap

class Receipt {

    // Example receipt infos:
    //    "id": "63113df734626f6711c13040",
    //    "idUser": "631139cd34626f6711c13038",
    //    "value": 72421,
    //    "status": 1,
    //    "paymentMethod": 2,
    //    "cardInfoBrand": "Visa",
    //    "merchantName": "teste 12",
    //    "date": 1661449971,
    //    "authentication": "5fa98cbe-4a0c-4b0c-a79d-56982ad11b6e",
    //    "merchantIcon": "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/1048.jpg",
    //    "merchantImage": "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/170.jpg",
    //    "message": "mensagem qualquer",
    //    "categories": []

    data class ReceiptInfo(
        val id: String,
        val idUser: String,
        val value: String,
        val status: String,
        val paymentMethod: String,
        val cardInfoBrand: String,
        val merchantName: String,
        val date: String,
        val authentication: String,
        val merchantIcon: String,
        val merchantImage: String,
        val message: String,
        val categories: String
    )

    var id: String? = null
    var idUser: String? = null
    var value: Double? = null
    val status: Int? = null
    val paymentMethod: Int? = null
    val cardInfoBrand: String? = null
    val merchantName: String? = null
    val date: String? = null
    val authentication: String? = null
    val merchantIcon: Bitmap? = null
    val merchantImage: Bitmap? = null
    val message: String? = null
    val categories: Array<Category>? = null
}