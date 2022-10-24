package com.example.recibodigitalalexventurini.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Example Receipts:
// "receipts": [...]

data class ListReceiptsResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String,
    @SerializedName("receipts")
    var receipts: List<ReceiptResponse>
) : Serializable

// Example Receipt:
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

data class ReceiptResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("idUser")
    var idUser: String,
    @SerializedName("value")
    var value: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("paymentMethod")
    var paymentMethod: String,
    @SerializedName("cardInfoBrand")
    var cardInfoBrand: String,
    @SerializedName("merchantName")
    var merchantName: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("authentication")
    var authentication: String,
    @SerializedName("merchantIcon")
    var merchantIcon: String,
    @SerializedName("merchantImage")
    var merchantImage: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("categories")
    var categories: List<String>
) : Serializable