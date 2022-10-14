package com.example.recibodigitalalexventurini.model

import com.google.gson.annotations.SerializedName

// Example Category:
// "categories": [...]

data class ListCategoriesResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String,
    @SerializedName("categories")
    var categories: List<CategoryResponse>
)

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
)