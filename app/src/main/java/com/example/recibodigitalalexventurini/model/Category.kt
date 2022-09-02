package com.example.recibodigitalalexventurini.model

class Category {

    // Example receipt infos:
    //    "id": "631148c834626f6711c1304b",
    //    "category": "Mercado",
    //    "color": "#c462d5"

    data class CategoryInfo(
        val id: String,
        val category: String,
        val color: String
    )

    private val id: Int? = null
    private val idUser: String? = null
    private val value: Double? = null
}