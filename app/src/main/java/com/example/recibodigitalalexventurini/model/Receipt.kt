package com.example.recibodigitalalexventurini.model

import android.graphics.Bitmap

class Receipt {

    // Example receipt infos:
    //    "id": "469b130d-ca63-4d3d-85c1-d8929fc920fd",
    //    "date": 1642458486,
    //    "value": 27480,
    //    "status": 0,
    //    "authentication": "0760f0e4-b70f-4608-b84f-761e0e51d411",
    //    "paymentMethod": 1,
    //    "cardInfo": {
    //        "brand": "Visa",
    //        "last4digits": "9985"
    //    },
    //    "merchantName": "Farmacia do Messias",
    //    "merchantId": "5520988882",
    //    "merchantIcon": "farmacia_logo.png",
    //    "merchantImage": "farmacia.jpg",
    //    "description": ""

    private val id: Int? = null
    private val date: String? = null
    private val value: Double? = null
    private val status: Int? = null
    private val authentication: String? = null
    private val paymentMethod: Int? = null
    private val cardInfoBrand: String? = null
    private val cardInfoLast4digits: Int? = null
    private val merchantName: String? = null
    private val merchantIcon: Bitmap? = null
    private val merchantImage: Bitmap? = null
    private val description: String? = null
}