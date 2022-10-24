package com.example.recibodigitalalexventurini.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Example Category:
// "categories": [...]

data class ListCategoriesResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String,
    @SerializedName("categories")
    var categories: List<CategoryResponse>
) : Serializable

// Example category:
//    "id": "631148c834626f6711c1304b",
//    "category": "Mercado",
//    "color": "#c462d5"
//    "countReceipts": 0

data class CategoryResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("color")
    var color: String,
    @SerializedName("countReceipts")
    var countReceipts: Int
) : Serializable

data class NewCategoryResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String
)

data class AddCategoryForReceiptResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String
)